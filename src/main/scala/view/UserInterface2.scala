package view

import scala.io.StdIn.readInt

object UserInterface2 {

  val mainActionMap = Map[Int, () => Boolean](1 -> login, 2 -> exit)
  val innerActionMap = Map[Int, () => Boolean](1 -> displayFinishedTasks, 2 -> displayUnfinishedTasks,
  3 -> addTask, 4 -> deleteTask, 5 -> markTaskAsFinished)

  def login(): Boolean = {
    displayInnerMenu()
    true
  }

  def exit(): Boolean = {
    System.exit(0)
    true
  }

  def displayFinishedTasks(): Boolean = {
    println("selected 1")
    true
  }

  def displayUnfinishedTasks(): Boolean = {
    System.exit(0)
    true
  }

  def addTask(): Boolean = {
    println("selected 1")
    true
  }

  def deleteTask(): Boolean = {
    System.exit(0)
    true
  }

  def markTaskAsFinished(): Boolean = {
    System.exit(0)
    true
  }

  def buildMainMenu(option: Int): Boolean = {
    mainActionMap.get(option) match {
      case Some(f) => f()
      case None =>
        println("Sorry, that command is not recognized")
        false
    }
  }

  def buildInnerMenu(option: Int): Boolean = {
    innerActionMap.get(option) match {
      case Some(f) => f()
      case None =>
        println("Sorry, that command is not recognized")
        false
    }
  }

  def displayMainMenu() = {
    println("Main menu:" + " \n1 - Login" + "\n2 - Exit")
    println("\nChoose the operation you want to perform:")
    val inputMainMenu = readInt()
    buildMainMenu(inputMainMenu)
  }

  def displayInnerMenu() = {
    println("\nTODO List:" + "\n1 - Display finished tasks" + "\n2 - Display unfinished tasks"
      + "\n3 - Add task" + "\n4 - Delete task" + "\n5 - Mark task as finished" + "\n6 - Get back to the main menu")
    println("\nChoose the operation you want to perform:")
    val inputNum = readInt()
    buildInnerMenu(inputNum)
  }

}
