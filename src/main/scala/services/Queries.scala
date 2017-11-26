package services

import models.{Task, TaskTable, User, UserTable}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

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

  def fillDatabase(): Unit = {
    users.map(userRepository.create(_))
    tasks.map(taskRepository.create(_))
  }

  def insertUsers(): Unit = {
    Await.result(userRepository.create(User(Some(1),"data","data","data user")), Duration.Inf)
    Await.result(userRepository.create(User(Some(2),"root","root","root user")), Duration.Inf)
  }

  def insertTasks() = {
    db.run(
      DBIO.seq( taskRepository.taskTableQuery += Task(Some(1),  1,"Analyze logs with Spark", false),
        taskRepository.taskTableQuery += Task(Some(2),  1,"Clean and process data", false),
          taskRepository.taskTableQuery += Task(Some(3),  1,"Make simple analytics on cleaned data", false),
          taskRepository.taskTableQuery += Task(Some(4),  1,"Design db for to-do list", true),
          taskRepository.taskTableQuery += Task(Some(5),  1,"Create models and relations", true),
          taskRepository.taskTableQuery += Task(Some(6),  1,"Fill db with data", false),
          taskRepository.taskTableQuery += Task(Some(7),  1,"Perform simple queries", false),
          taskRepository.taskTableQuery += Task(Some(8),  1,"Configure db on Heroku", false),
          taskRepository.taskTableQuery += Task(Some(9),  1,"Introduce fixes", false),
          taskRepository.taskTableQuery += Task(Some(10), 1,"Create user interface", false),
          taskRepository.taskTableQuery += Task(Some(11), 2,"Have a walk", true),
          taskRepository.taskTableQuery += Task(Some(12), 2,"Finish course work in Unity", false),
          taskRepository.taskTableQuery += Task(Some(13), 2,"Finish lab works in IS", false),
          taskRepository.taskTableQuery += Task(Some(14), 2,"Finish lab works in PM", true),
          taskRepository.taskTableQuery += Task(Some(15), 2,"Finish first project for courses", false),
          taskRepository.taskTableQuery += Task(Some(16), 2,"Finish watching Stranger Things season 2", false),
          taskRepository.taskTableQuery += Task(Some(17), 2,"Finish course work in FS", false),
          taskRepository.taskTableQuery += Task(Some(18), 2,"Pass all the texts for English", true),
          taskRepository.taskTableQuery += Task(Some(19), 2,"Write all requirements for PM coursework", true),
          taskRepository.taskTableQuery += Task(Some(20), 2,"Watch all missed talks from the conference", false)
      ).transactionally
    )
  }

  def addTask(name: String) = {
    //taskRepository.create(getTaskByName(name)) //insert through current user
  }

  def deleteTask() = {
    //taskRepository.delete(Task())
  }

  def getTaskByName(name: String) = {
    TaskTable.table.filter(_.name === name).take(1)

  }

  def showUsers = {
    // UserTable.table.result.map(u => println("Id: " + u.map()))
    /*
    db.run(selectUsers).map({
          case(idUser, login, password, fullname) =>
      println("Id: " + t.idUser + "\t" + login + "\t" + password + "\t" + fullname)
    })


    val q1 = for(c <- UserTable.table)
      yield c.idUser + "\t" + c.login.asColumnOf[String] ++
        "\t" + c.password.asColumnOf[String] ++ "\t" + c.fullname.asColumnOf[String]
    db.stream(q1.result).foreach(println)
    */
  }
}
