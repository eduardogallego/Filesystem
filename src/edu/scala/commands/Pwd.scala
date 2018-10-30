package edu.scala.commands

import edu.scala.filesystem.State

class Pwd extends Command {

  override def apply(state: State): State = {
    state.setMessage(state.wd.path)
  }
}
