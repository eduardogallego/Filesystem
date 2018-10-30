package edu.scala.commands
import edu.scala.files.{DirEntry, Directory}
import edu.scala.filesystem.State

abstract class CreateEntry(name: String) extends Command {

  def checkIllegal(name: String): Boolean = name.contains(".")

  def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry): Directory = {
    /*
      someDirectory
        /a
        /b
        (new) /d

      => new someDir
        /a
        /b
        /d


      /a/b
        /c
        /d
        (new) /e

      =>
      new /a
        new /b (parent /a)
            /c
            /d
            /e
     */
    if (path.isEmpty) currentDirectory.addEntry(newEntry)
    else {
      /*
        /a/b
          /c
          /d
          (new) /e

        currentDirectory = /a
        path = ["b"]
       */
      val oldEntry = currentDirectory.findEntry(path.head).asDirectory
      currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry, path.tail, newEntry))

      /*
          /a/b
            (contents)
            (new enry) /e

         new root = updateStructure(root, ["a", "b"], /e)
          => path.isEmpty ?
          => oldEntry = /a
          root.replaceEntry("a", updateStructure(/a, ["b"], /e)
            => path.isEmpty ?
            => oldEntry = /b
            /a.replaceEntry("b", updateStructure(/b, [], /e)
              => path.isEmpty /b.add(/e)
       */
    }
  }

  def doCreateEntry(state: State, name: String): State = {
    val wd = state.wd

    // 1. all the directories in the full path
    val allDirsInPath = wd.getAllFoldersInPath

    // 2. create new entry in the wd
    val newEntry: DirEntry = doCreateSpecificEntry(state, name)

    // 3. update the whole directory structure starting from the root
    // (the directory structure is IMMUTABLE)
    val newRoot = updateStructure(state.root, allDirsInPath, newEntry)

    // 4. find new working directory INSTANCE given wd's full path in the new directory structure
    val newWd = newRoot.findDescendant(allDirsInPath)

    State(newRoot, newWd)
  }

  override def apply(state: State): State = {
    val wd = state.wd
    if (wd.hasEntry(name))
      state.setMessage("Entry " + name + " already exists!")
    else if (name.contains(Directory.SEPARATOR))
      state.setMessage(name + " must not contain separators!")
    else if (checkIllegal(name))
      state.setMessage(name + ": illegal entry name!")
    else
      doCreateEntry(state, name)
  }

  def doCreateSpecificEntry(state: State, entryName: String): DirEntry
}
