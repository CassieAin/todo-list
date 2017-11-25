package models
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

case class User (
  idUser: Option[Long],
  login: String,
  password: String,
  fullname: String
)

class UserTable(tag: Tag) extends Table[User](tag, "users"){
  val idUser = column[Long]("id_user", O.PrimaryKey)
  val login = column[String]("login")
  val password = column[String]("password")
  val fullname = column[String]("fullname")

  def * = (idUser.?, login, password, fullname) <> (User.apply _ tupled, User.unapply)
}

object UserTable{
  lazy val table = TableQuery[UserTable]
}

class UserRepository(db: Database) {
  val userTableQuery = TableQuery[UserTable]

  def create(user: User): Future[User] =
    db.run(userTableQuery returning userTableQuery += user)

  def update(user: User): Future[Int] =
    db.run(userTableQuery.filter(_.idUser === user.idUser).update(user))

  def delete(user: User): Future[Int] =
    db.run(userTableQuery.filter(_.idUser === user.idUser).delete)

  def getById(user: User): Future[Option[User]] =
    db.run(userTableQuery.filter(_.idUser === user.idUser).result.headOption)
}

