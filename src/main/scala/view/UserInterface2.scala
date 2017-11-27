package view

import services.DAO

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.io.StdIn.{readInt, readLine}

object UserInterface2 {
val id = 0
  val mainActionMap = Map[Int, () => Boolean](1 -> login, 2 -> exit)
  val innerActionMap = Map[Int, ((Long) => Boolean)](1 -> displayFinishedTasks, 2 -> displayUnfinishedTasks,
  3 -> addTask, 4 -> deleteTask, 5 -> markTaskAsFinished)
  val loginMap = Map[String,((Long) =>  Boolean)]("true" -> correctLogin, "false" -> wrongLogin)

  def login(): Boolean = {
    println("Input you login, please:")
    val inputLogin = readLine()
    println("Input you password, please:")
    val inputPassword = readLine()

    val checkLogin = Await.result(DAO.checkUserLogin(inputLogin, inputPassword), Duration.Inf).toString
    //val userId = DAO.selectUserId(inputLogin)
    val userId = Await.result(DAO.getUserId(inputLogin), Duration.Inf)
    enterSystem(checkLogin, userId)
    true
  }

  def correctLogin(userId: Long): Boolean = {
    println("You have successfully entered")
    displayInnerMenu(userId)
    true
  }

  def wrongLogin(userId: Long): Boolean = {
    displayInnerMenu(userId)
    true
  }

  def enterSystem(option: String, userId: Long): Unit ={
    loginMap.get(option) match {
      case Some(f) => f()
      case None =>
        println("Sorry, that command is not recognized")
        false
    }
  }

  def exit(): Boolean = {
    System.exit(0)
    true
  }

  def displayFinishedTasks(userId: Long): Boolean = {
    println("User's finished tasks:\n" + Await.result(DAO.selectFinishedTasks(userId), Duration.Inf).toList.toString)
    //displayInnerMenu()
    true
  }

  def displayUnfinishedTasks(userId: Long): Boolean = {
    System.exit(0)
    true
  }

  def addTask(userId: Long): Boolean = {
    System.exit(0)
    true
  }

  def deleteTask(userId: Long): Boolean = {
    System.exit(0)
    true
  }

  def markTaskAsFinished(userId: Long): Boolean = {
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

  def buildInnerMenu(option: Int, userId: Long): Boolean = {
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

  def displayInnerMenu(userId: Long) = {
    println("\nTODO List:" + "\n1 - Display finished tasks" + "\n2 - Display unfinished tasks"
      + "\n3 - Add task" + "\n4 - Delete task" + "\n5 - Mark task as finished" + "\n6 - Get back to the main menu")
    println("\nChoose the operation you want to perform:")
    val inputNum = readInt()
    buildInnerMenu(inputNum, userId)
  }

}
