package edu.scala.commands

import edu.scala.files.{DirEntry, File}
import edu.scala.filesystem.State

class Touch(name: String) extends CreateEntry(name) {

  override def doCreateSpecificEntry(state: State, entryName: String): DirEntry =
    File.empty(state.wd.path, entryName)

}
