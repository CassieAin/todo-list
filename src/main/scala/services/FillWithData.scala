package services

import models.{Task, TaskTable, User, UserTable}
import slick.jdbc.PostgresProfile.api._

object FillWithData {

  val users = List(
    User(Some(1),"data","data","data user"),
    User(Some(2),"root","root","root user")
  )

  val tasks = List(
    Task(Some(1),  1,"Analyze logs with Spark", false),
    Task(Some(2),  1,"Clean and process data", false),
    Task(Some(3),  1,"Make simple analytics on cleaned data", false),
    Task(Some(4),  1,"Design db for to-do list", true),
    Task(Some(5),  1,"Create models and relations", true),
    Task(Some(6),  1,"Fill db with data", false),
    Task(Some(7),  1,"Perform simple queries", false),
    Task(Some(8),  1,"Configure db on Heroku", true),
    Task(Some(9),  1,"Introduce fixes", false),
    Task(Some(10), 1,"Create user interface", false),
    Task(Some(11), 2,"Have a walk", true),
    Task(Some(12), 2,"Finish course work in Unity", false),
    Task(Some(13), 2,"Finish lab works in IS", false),
    Task(Some(14), 2,"Finish lab works in PM", true),
    Task(Some(15), 2,"Finish first project for courses", false),
    Task(Some(16), 2,"Finish watching Stranger Things season 2", false),
    Task(Some(17), 2,"Finish course work in FS", false),
    Task(Some(18), 2,"Pass all the texts for English", true),
    Task(Some(19), 2,"Write all requirements for PM coursework", true),
    Task(Some(20), 2,"Watch all missed talks from the conference", false)
  )

  val createUserTable = UserTable.table.schema.create
  val createTaskTable = TaskTable.table.schema.create
  val dropUserTable = UserTable.table.schema.drop
  val dropTaskTable = TaskTable.table.schema.drop
  val selectUsers = UserTable.table.result
  val selectTasks = TaskTable.table.result
  val tasksNumber = TaskTable.table.length
  val fillUserTable = userRepository.createInBatch(users)
  val fillTaskTable = taskRepository.createInBatch(tasks)

  def fillDatabase(): Unit = {
    users.map(userRepository.create(_))
    tasks.map(taskRepository.create(_))
  }

}
