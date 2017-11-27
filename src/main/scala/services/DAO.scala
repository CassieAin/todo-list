package services

import models.{Task, TaskTable, UserTable}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

object DAO {

  def getUserId(login: String): Future[Long] = {
    val queryToGetUserId = (for {
      user <- UserTable.table if user.login === login
    } yield (user.idUser))
        .result.head
      //.head // Will throw exception if empty result
    db.run(queryToGetUserId)
  }

  def selectUserId(login: String) = login match {
      case "data" => 1
      case "root" => 2
  }

  def checkUserLogin(login: String, password: String) = {
    db.run(UserTable.table.filter(t => (t.login === login) && (t.password === password)).exists.result)
  }

  def checkUserPassword(password: String) = {
    db.run(UserTable.table.filter(_.password === password).exists.result)
  }

  def addTask(name: String, currentUser: Long) = {
    taskRepository.create(Task(Some(FillWithData.tasks.length + 2), currentUser, name, false))
  }

  def deleteTask(id: Option[Long]) = {
    val getTaskByIdLoc = (for {
      task <- TaskTable.table if task.idTask === id
    } yield (task))
    val deleteAction = getTaskByIdLoc.delete
    db.run(deleteAction)
  }

  def finishTask(id: Option[Long], userId: Long) = {
    val getTaskFinishedField = (for {
      task <- TaskTable.table if task.idTask === id
    } yield (task.finished))
    val updateAction = getTaskFinishedField.update(true)
    db.run(updateAction)
  }

  def selectTasksByUser(userId: Long) = {
    val queryTasksByUser = (for {
      task <- TaskTable.table
      user <- UserTable.table if task.ownerId === userId
    } yield (task, user))
      .map(_._1.name)

    db.run(queryTasksByUser.distinct.result)
  }


  def selectFinishedTasks(userId: Long) = {
    val queryFinishedTasks = (for {
      task <- TaskTable.table
      user <- UserTable.table if task.ownerId === userId
    } yield (task, user))
        .filter(_._1.finished  === true)
        .map{case (task, user) => (task.idTask, task.name)}
        .sortBy(_._1)
    db.run(queryFinishedTasks.distinct.result)
  }

  def selectUnfinishedTasks(userId: Long) = {
    val queryUnFinishedTasks = (for {
      task <- TaskTable.table
      user <- UserTable.table if task.ownerId === userId
    } yield (task, user))
      .filter(_._1.finished === false)
      .map{case (task, user) => (task.idTask, task.name)}
      .sortBy(_._1)
    db.run(queryUnFinishedTasks.distinct.result)
  }

  def getTaskByName(inputName: String) = {
    val queryTasksByNameAndUser = (for {
      task <- TaskTable.table
      user <- UserTable.table if task.ownerId === user.idUser
    } yield (task, user))
        .filter(_._1.name === inputName)
        .map{ case (task, user) => (task.name, task.finished) }
        .take(1)
    db.run(queryTasksByNameAndUser.result)
  }

  def getTaskById(id: Option[Long]) = {
    val queryTasksByIdAndUser = (for {
      task <- TaskTable.table
      user <- UserTable.table if task.ownerId === user.idUser
    } yield (task, user))
      .filter(_._1.idTask === id)
      .map{ case (task, user) => (task.name, task.finished) }
      .take(1)
    db.run(queryTasksByIdAndUser.result)
  }
}
