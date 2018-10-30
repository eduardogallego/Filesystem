package edu.scala.filesystem

import java.util.Scanner

import edu.scala.commands.Command
import edu.scala.files.Directory

object Filesystem extends App {

  val root = Directory.ROOT
  val scanner = new Scanner(System.in)
  var state = State(root, root)

  while (true) {
    state.show
    val input = scanner.nextLine()
    state = Command.from(input).apply(state)
  }

}
