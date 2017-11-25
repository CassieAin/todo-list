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
    Await.result(db.run(DBIO.seq(Queries.createUserTable, Queries.createTaskTable)), 2 seconds)
    //db.run((DBIO.seq(Queries.createUserTable, Queries.createTaskTable)))
    executeAction(Queries.selectUsers)
  }

  def executeAction[T](action: DBIO[T]) =
    Await.result(db.run(action), 2 seconds)
}
