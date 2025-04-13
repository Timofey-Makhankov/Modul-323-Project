package ch.main.db.model.task

import ch.main.db.model.category.categoriesSchema
import slick.ast.BaseTypedType
import slick.jdbc.JdbcType
import slick.jdbc.SQLiteProfile.api.*

import java.sql.{Date, Timestamp}
import java.time.{LocalDate, LocalDateTime, ZoneId}

// (4) Data set to be immutable
class TaskTable(tag: Tag) extends Table[Task](tag, Option.empty, "tasks"){
  def id = column[Int]("id", O.PrimaryKey)
  def title = column[String]("title")
  def description = column[Option[String]]("description")
  def categoryId = column[Option[Int]]("category_id")
  def parentTaskId = column[Option[Int]]("parent_task_id")
  def completed = column[Boolean]("completed")
  def lastUpdated = column[Timestamp]("last_updated")
  def deadline = column[Option[Timestamp]]("deadline")
  override def * = (id.?, title, description, categoryId, parentTaskId, completed, lastUpdated, deadline).mapTo[Task]
  def category = foreignKey("category_fk", categoryId, categoriesSchema)(_.id.?, onDelete = ForeignKeyAction.NoAction, onUpdate = ForeignKeyAction.NoAction)
  def subtask = foreignKey("parent_task_fk", parentTaskId, tasksSchema)(_.id.?, onDelete = ForeignKeyAction.Cascade, onUpdate = ForeignKeyAction.NoAction)
}
