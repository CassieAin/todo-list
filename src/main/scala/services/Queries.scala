package services

import models.{Task, TaskTable, User, UserTable}
import slick.jdbc.PostgresProfile.api._

object Queries {

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
    Task(Some(8),  1,"Configure db on Heroku", false),
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
  val selectUsers = UserTable.table.result
  val selectTasks = TaskTable.table.result
  val createUsers = users.foreach(userRepository.create(_))
  val createTasks = tasks.foreach(taskRepository.create(_))

  def fillDatabase(): Unit = {
    users.foreach(userRepository.create(_))
    tasks.foreach(taskRepository.create(_))
  }

  def insertUsers(): Unit = {
    users.foreach(userRepository.create(_))

   /*
    userRepository.create(User(Some(1),"data","data","data user"))
    userRepository.create(User(Some(2),"root","root","root user"))
    */
  }

  def insertTasks(): Unit = {

    tasks.foreach(taskRepository.create(_))
    /*
    taskRepository.create(Task(Some(1),  1,"Analyze logs with Spark", false))
    taskRepository.create(Task(Some(2),  1,"Clean and process data", false))
    taskRepository.create(Task(Some(3),  1,"Make simple analytics on cleaned data", false))
    taskRepository.create(Task(Some(4),  1,"Design db for to-do list", true))
    taskRepository.create(Task(Some(5),  1,"Create models and relations", true))
    taskRepository.create(Task(Some(6),  1,"Fill db with data", false))
    taskRepository.create(Task(Some(7),  1,"Perform simple queries", false))
    taskRepository.create(Task(Some(8),  1,"Configure db on Heroku", false))
    taskRepository.create(Task(Some(9),  1,"Introduce fixes", false))
    taskRepository.create(Task(Some(10), 1,"Create user interface", false))
    taskRepository.create(Task(Some(11), 2,"Have a walk", true))
    taskRepository.create(Task(Some(12), 2,"Finish course work in Unity", false))
    taskRepository.create(Task(Some(13), 2,"Finish lab works in IS", false))
    taskRepository.create(Task(Some(14), 2,"Finish lab works in PM", true))
    taskRepository.create(Task(Some(15), 2,"Finish first project for courses", false))
    taskRepository.create(Task(Some(16), 2,"Finish watching Stranger Things season 2", false))
    taskRepository.create(Task(Some(17), 2,"Finish course work in FS", false))
    taskRepository.create(Task(Some(18), 2,"Pass all the texts for English", true))
    taskRepository.create(Task(Some(19), 2,"Write all requirements for PM coursework", true))
    taskRepository.create(Task(Some(20), 2,"Watch all missed talks from the conference", false))
    */
  }

}
