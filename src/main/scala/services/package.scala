import models.{TaskRepository, UserRepository}
import slick.jdbc.PostgresProfile.api._

package object services {
  val db = Database.forConfig("scalaxdb")
  val userRepository = new UserRepository(db)
  val taskRepository = new TaskRepository(db)
}
