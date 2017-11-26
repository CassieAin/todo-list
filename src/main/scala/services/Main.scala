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
    //Await.result(db.run(DBIO.seq(Queries.dropTaskTable, Queries.dropUserTable)), Duration.Inf)
   //Await.result(db.run(DBIO.seq(Queries.createUserTable, Queries.createTaskTable)), Duration.Inf)
    //Queries.fillDatabase()
    //Queries.insertUsers()
   // Await.result(Queries.insertTasks(), Duration.Inf)

    println("All table records:\n" + Await.result(db.run(Queries.selectTasks), 10.seconds).toList.mkString("\n"))

  }

  def executeAction[T](action: DBIO[T]) =
    Await.result(db.run(action), 2 seconds)

  /*

  def main(args: Array[String]): Unit = {
    val entireDBScript =
      createTableAction andThen
      insertAllAction andThen
      insertOneAction andThen
      insertFavouriteAction andThen
      updateAlbumsAction(2015) andThen
      insertAlbum("Keyboard Cat", "More Keyboard Cat's Greatest Hits", 2008)  andThen
      deleteAlbumsAction("Justin Bieber") andThen
      AlbumTable.result

    exec(entireDBScript.transactionally).foreach(println)
  }

   */
}
