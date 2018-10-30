package edu.scala.commands

import edu.scala.filesystem.State

trait Command {

  def apply(state: State): State

}

object Command {

  val CD = "cd"
  val LS = "ls"
  val MKDIR = "mkdir"
  val PWD = "pwd"
  val TOUCH = "touch"

  def emptyCommand: Command =
    (state) => state.setMessage("")

  def incompleteCommand(name: String): Command =
    (state) => state.setMessage(name + " incomplete command!")

  def from(input: String): Command = {
    val tokens: Array[String] = input.split(" ")
    if (input.isEmpty) emptyCommand
    else if (MKDIR.equals(tokens(0))) {
      if (tokens.length < 2) incompleteCommand(MKDIR)
      else new Mkdir(tokens(1))
    } else if (LS.equals(tokens(0))) new Ls
    else if (PWD.equals(tokens(0))) new Pwd
    else if (TOUCH.equals(tokens(0))) {
      if (tokens.length < 2) incompleteCommand(TOUCH)
      else new Touch(tokens(1))
    } else if (CD.equals(tokens(0))) {
      if (tokens.length < 2) incompleteCommand(CD)
      else new Cd(tokens(1))
    } else new UnknownCommand
  }

}
