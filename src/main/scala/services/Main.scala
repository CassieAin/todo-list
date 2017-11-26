package services


import models.{TaskRepository, UserRepository}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.io.StdIn._

object Main {

  val db = Database.forConfig("scalaxdb")
  val userRepository = new UserRepository(db)
  val taskRepository = new TaskRepository(db)

  def main(args: Array[String]): Unit = {
   // println("All table records:\n" + Await.result(db.run(DAO.selectTasks), 10.seconds).toString())
    //println("All table records:\n" + Await.result(db.run(DAO.selectTasks), 10.seconds).toList.mkString("\n"))

    /*
        println("All table records:\n" + Await.result(db.run(DAO.selectTasks), 10.seconds).toList.mkString("\n"))
        DAO.finishTask(Some(6), 1)
        DAO.deleteTask(Some(20))
        DAO.addTask("do the task all night", 2)
        println("All table records:\n" + Await.result(db.run(DAO.selectTasks), 10.seconds).toList.mkString("\n"))
        println("User's login:\n" + Await.result(DAO.selectTasksByUser(inputLogin), Duration.Inf).toList.mkString("\n"))
        println("Table records by name:\n" + Await.result(DAO.getTaskById(Some(2)), 10.seconds).toList.mkString("\n"))

         val id = Await.result(DAO.getUserId("data"), Duration.Inf).toList.mkString.toLong
        println("Table records by name:\n" + Await.result(DAO.selectTasksByUser(id), 10.seconds).toList.mkString("\n"))
*/

    println("Main menu:" + " \n1 - Login" + "\n2 - Exit")
    println("\nChoose the operation you want to perform:")
    val inputMainMenu = readInt()
    buildMainMenu(inputMainMenu)
  }

  def buildMainMenu(inputNumber: Int) =  inputNumber match {
      case 1 => enterSystem()
      case 2 => System.exit(0)
      case _ => println("Your input was wrong. Try again"); System.exit(0)
  }

  def enterSystem(): Unit ={
    println("Input you login, please:")
    val inputLogin = readLine()
    println("Input you password, please:")
    val inputPassword = readLine()

    val checkLogin = Await.result(DAO.checkUserLogin(inputLogin, inputPassword), Duration.Inf).toString
    val userId = DAO.selectUserId(inputLogin)

    def changeOutputs(checkLogin: String):Unit = checkLogin match {
      case "true" => println("You have successfully entered"); displayMenu(); buildMenu(userId)
      case "false" => println("Your input for login or password is wrong"); System.exit(1)
      case _ => println("Your input is wrong"); System.exit(1)
    }
    changeOutputs(checkLogin)
  }

  def buildMenu(userId: Long): Unit ={

    //val inputNum = readInt()
    def chooseOption(number: Int):Unit = number match {
      case 1 => displayFinishedTasks(userId)
      case 2 => displayUnfinishedTasks(userId)
      case 3 => addTask()
      case 4 => deleteTask()
      case 5 => markTaskAsFinished(userId)
      case _ => println("Your input is wrong"); System.exit(1)
    }

    val inputNum = displayMenu()
    chooseOption(inputNum)

  }

  def displayMenu():Int ={
    println("TODO List:" + "\n1 - Display finished tasks" + "\n2 - Display unfinished tasks"
      + "\n3 - Add task" + "\n4 - Delete task" + "\n5 - Mark task as finished")
    println("\nChoose the operation you want to perform:")
    val inputNum = readInt()
    inputNum
  }

  def displayAllTasks(id: Long) = {
    println()
    println("User's tasks:\n" + Await.result(DAO.selectTasksByUser(id), Duration.Inf).toList.toString)
    displayMenu()
  }

  def displayFinishedTasks(id: Long) = {
    println()
    println("User's finished tasks:\n" + Await.result(DAO.selectFinishedTasks(id), Duration.Inf).toList.toString)
    displayMenu()
  }

  def displayUnfinishedTasks(id: Long) = {
    println()
    println("User's unfinished tasks:\n" + Await.result(DAO.selectUnfinishedTasks(id), Duration.Inf).toList.toString)
    displayMenu()
  }

  def addTask() = {

  }

  def deleteTask() = {
    println()
    println("Choose the task you want to delete, please:")
    val taskId = readLong()
    Await.result(DAO.deleteTask(Some(taskId)), Duration.Inf)
    displayMenu()
  }

  def markTaskAsFinished(id: Long) = {
    println()
    println("Choose the task you want to mark as finished, please:")
    val taskId = readLong()
    Await.result(DAO.finishTask(Some(taskId), id), Duration.Inf)
    displayMenu()
  }

  def displayInfo[R](action: DBIOAction[R, NoStream, Nothing], info: String) = {
    println(info + Await.result(db.run(action), 10.seconds))
  }

  def executeAction[T](action: DBIO[T]) =
    Await.result(db.run(action), 2 seconds)


  def createSchemasAndFillWithData() = {
    Await.result(db.run(DBIO.seq(DAO.dropTaskTable, DAO.dropUserTable)), Duration.Inf)
    Await.result(db.run(DBIO.seq(DAO.createUserTable, DAO.createTaskTable)), Duration.Inf)
    Await.result(DAO.fillUserTable, Duration.Inf)
    Await.result(DAO.fillTaskTable, Duration.Inf)
  }
}
