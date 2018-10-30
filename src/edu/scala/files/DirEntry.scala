package edu.scala.files

abstract class DirEntry(val parentPath: String, val name: String) {

  def path: String =
    if (Directory.SEPARATOR.equals(parentPath)) parentPath + name
    else parentPath + Directory.SEPARATOR + name

  def asDirectory: Directory

  def asFile: File

  def getType: String

  def isDirectory: Boolean

  def isFile: Boolean

}
