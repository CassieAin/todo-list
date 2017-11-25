package models
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

case class Task (
  idTask: Option[Long],
  ownerId: Long,
  name: String,
  finished: Boolean
)

class TaskTable(tag: Tag) extends Table[Task](tag, "tasks"){
  val idTask = column[Long]("id_task", O.PrimaryKey)
  val ownerId = column[Long]("owner")
  val name = column[String]("name")
  val finished = column[Boolean]("finished")

  val ownerFk = foreignKey("owner_id_fk", ownerId, TableQuery[UserTable])(_.idUser)

  def * = (idTask.?, ownerId, name, finished) <> (Task.apply _ tupled, Task.unapply)
}

object TaskTable{
  lazy val table = TableQuery[TaskTable]
}

class TaskRepository(db: Database) {
  val taskTableQuery = TableQuery[TaskTable]

  def create(task: Task): Future[Task] =
    db.run(taskTableQuery returning taskTableQuery += task)

  def update(task: Task): Future[Int] =
    db.run(taskTableQuery.filter(_.idTask === task.idTask).update(task))

  def delete(task: Task): Future[Int] =
    db.run(taskTableQuery.filter(_.idTask === task.idTask).delete)

  def getById(task: Task): Future[Option[Task]] =
    db.run(taskTableQuery.filter(_.idTask === task.idTask).result.headOption)
}

