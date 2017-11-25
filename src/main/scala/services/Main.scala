package services


import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration._

object Main {

  def main(args: Array[String]): Unit = {

  }

  def exec[T](action: DBIO[T]): T =
    Await.result(db.run(action), 2 seconds)
}
