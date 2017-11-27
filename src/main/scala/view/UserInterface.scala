package view

import services.DAO

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.io.StdIn.{readInt, readLine, readLong}

object UserInterface {
  def displayMainMenu(): Unit ={
    println("Main menu:" + " \n1 - Login" + "\n2 - Exit")
    println("\nChoose the operation you want to perform:")
    val inputMainMenu = readInt()
    buildMainMenu(inputMainMenu)
  }

  def buildMainMenu(inputNumber: Int) =  inputNumber match {
    case 1 => enterSystem()
    case 2 => System.exit(0)
    case _ => println("Your input was wrong. Try again"); displayMainMenu()
  }

  def enterSystem(): Unit ={
    println("Input you login, please:")
    val inputLogin = readLine()
    println("Input you password, please:")
    val inputPassword = readLine()

    val checkLogin = Await.result(DAO.checkUserLogin(inputLogin, inputPassword), Duration.Inf).toString
    //val userId = DAO.selectUserId(inputLogin)
    val userId = Await.result(DAO.getUserId(inputLogin), Duration.Inf)

    def changeOutputs(checkLogin: String):Unit = checkLogin match {
      case "true" => println("You have successfully entered"); displayInnerMenu(); buildMenu(userId)
      case "false" => println("Your input for login or password is wrong. Please, try again"); displayMainMenu()
      case _ => println("Your input is wrong"); displayMainMenu()
    }
    changeOutputs(checkLogin)
  }

  def buildMenu(userId: Long): Unit ={
    def chooseOption(number: Int):Unit = number match {
      case 1 => displayFinishedTasks(userId)
      case 2 => displayUnfinishedTasks(userId)
      case 3 => addTask(userId)
      case 4 => deleteTask()
      case 5 => markTaskAsFinished(userId)
      case 6 => displayMainMenu()
      case _ => println("Your input is wrong"); displayMainMenu()
    }
    val inputNum = displayInnerMenu()
    chooseOption(inputNum)

  }

  def displayInnerMenu():Int ={
    println("\nTODO List:" + "\n1 - Display finished tasks" + "\n2 - Display unfinished tasks"
      + "\n3 - Add task" + "\n4 - Delete task" + "\n5 - Mark task as finished" + "\n6 - Get back to the main menu")
    println("\nChoose the operation you want to perform:")
    val inputNum = readInt()
    inputNum
  }

  def displayAllTasks(id: Long) = {
    println()
    println("User's tasks:\n" + Await.result(DAO.selectTasksByUser(id), Duration.Inf).toList.toString)
    displayInnerMenu()
  }

  def displayFinishedTasks(id: Long) = {
    println()
    println("User's finished tasks:\n" + Await.result(DAO.selectFinishedTasks(id), Duration.Inf).toList.toString)
    displayInnerMenu()
  }

  def displayUnfinishedTasks(id: Long) = {
    println()
    println("User's unfinished tasks:\n" + Await.result(DAO.selectUnfinishedTasks(id), Duration.Inf).toList.toString)
    displayInnerMenu()
  }

  def addTask(id: Long) = {
    println()
    println("Input the task name you want to create, please:")
    val taskName = readLine()
    Await.result(DAO.addTask(taskName, id), Duration.Inf)
    displayInnerMenu()
  }

  def deleteTask() = {
    println()
    println("Choose the task id you want to delete, please:")
    val taskId = readLong()
    Await.result(DAO.deleteTask(Some(taskId)), Duration.Inf)
    displayInnerMenu()
  }

  def markTaskAsFinished(id: Long) = {
    println()
    println("Choose the task id you want to mark as finished, please:")
    val taskId = readLong()
    Await.result(DAO.finishTask(Some(taskId), id), Duration.Inf)
    displayInnerMenu()
  }

}
