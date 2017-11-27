package services


import models.{TaskRepository, UserRepository}
import slick.jdbc.PostgresProfile.api._
import view._

import scala.concurrent.Await
import scala.concurrent.duration._

object Main {

  val db = Database.forConfig("scalaxdb")
  val userRepository = new UserRepository(db)
  val taskRepository = new TaskRepository(db)

  def main(args: Array[String]): Unit = {
    UserInterface2.displayMainMenu()
  }

  def createSchemasAndFillWithData() = {
    //Await.result(db.run(DBIO.seq(DAO.dropTaskTable, DAO.dropUserTable)), Duration.Inf)
    Await.result(db.run(DBIO.seq(FillWithData.createUserTable, FillWithData.createTaskTable)), Duration.Inf)
    Await.result(FillWithData.fillUserTable, Duration.Inf)
    Await.result(FillWithData.fillTaskTable, Duration.Inf)
  }
}
