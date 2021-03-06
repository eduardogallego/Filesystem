package edu.scala.commands

import edu.scala.files.{DirEntry, Directory}
import edu.scala.filesystem.State

class Mkdir(name: String) extends CreateEntry(name) {

  override def doCreateSpecificEntry(state: State, entryName: String): DirEntry =
    Directory.empty(state.wd.path, entryName)

}
