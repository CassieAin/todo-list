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
/*
    println("All table records:\n" + Await.result(db.run(DAO.selectTasks), 10.seconds).toList.mkString("\n"))
    DAO.finishTask(Some(6), 1)
    DAO.deleteTask(Some(20))
    DAO.addTask("do the task all night", 2)
    println("All table records:\n" + Await.result(db.run(DAO.selectTasks), 10.seconds).toList.mkString("\n"))
    println("User's login:\n" + Await.result(DAO.selectTasksByUser(inputLogin), Duration.Inf).toList.mkString("\n"))
    println("Table records by name:\n" + Await.result(DAO.getTaskById(Some(2)), 10.seconds).toList.mkString("\n"))
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

  def enterSystem(): List[String] ={
    println("Input you login, please:")
    val inputLogin = readLine()
    println("Input you password, please:")
    val inputPassword = readLine()

    val checkLogin = Await.result(DAO.checkUserLogin(inputLogin, inputPassword), Duration.Inf).toString

    def changeOutputs(checkLogin: String):Unit = checkLogin match {
      case "true" => println("You have successfully entered")
      case "false" => println("Your input for login or password is wrong"); System.exit(1)
      case _ => println("Your input is wrong"); System.exit(1)
    }
    changeOutputs(checkLogin)
    val userData = List(inputLogin, inputPassword)
    userData
  }

  def buildMenu(): Unit ={

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
