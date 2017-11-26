package services


import models.{TaskRepository, UserRepository}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration._

object Main {

  val db = Database.forConfig("scalaxdb")
  val userRepository = new UserRepository(db)
  val taskRepository = new TaskRepository(db)

  def main(args: Array[String]): Unit = {
    //Await.result(db.run(DBIO.seq(DAO.dropTaskTable, DAO.dropUserTable)), Duration.Inf)
    //Await.result(db.run(DBIO.seq(DAO.createUserTable, DAO.createTaskTable)), Duration.Inf)
    //Await.result(DAO.fillUserTable, Duration.Inf)
    //Await.result(DAO.fillTaskTable, Duration.Inf)
    //DAO.fillDatabase()
    //DAO.insertUsers()
/*
    println("All table records:\n" + Await.result(db.run(DAO.selectTasks), 10.seconds).toList.mkString("\n"))
*/
   // DAO.finishTask(Some(6), 1)
   // DAO.deleteTask(Some(20))
    //DAO.addTask("do the task all night", 2)
    println("Table records by name:\n" + Await.result(DAO.selectTasksByUser(2), Duration.Inf).toList.mkString("\n"))
    println("All table records:\n" + Await.result(db.run(DAO.selectTasks), 10.seconds).toList.mkString("\n"))
    //println("Table records by name:\n" + Await.result(DAO.getTaskById(Some(2)), 10.seconds).toList.mkString("\n"))
  }

  def displayInfo[R](action: DBIOAction[R, NoStream, Nothing], info: String) = {
    println(info + Await.result(db.run(action), 10.seconds))
  }

  def executeAction[T](action: DBIO[T]) =
    Await.result(db.run(action), 2 seconds)
}
