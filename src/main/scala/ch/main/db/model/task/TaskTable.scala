package ch.main.db.model.task

import ch.main.db.model.category.categoriesSchema
import slick.jdbc.SQLiteProfile.api.*

import java.time.LocalDateTime

class TaskTable(tag: Tag) extends Table[Task](tag, Option.empty, "tasks"){
  def id = column[Int]("id", O.PrimaryKey)
  def title = column[String]("title")
  def description = column[String]("description")
  def categoryId = column[Int]("category_id")
  def parentTaskId =column[Int]("parent_task_id")
  def completed = column[Boolean]("completed")
  def lastUpdated = column[LocalDateTime]("last_updated")
  def deadline = column[LocalDateTime]("deadline")
  def category = foreignKey("category_fk", categoryId, categoriesSchema)(_.id)
  def subtask = foreignKey("parent_task_fk", parentTaskId, tasksSchema)(_.id)
  override def * = (id.?, title, description.?, parentTaskId.?, categoryId.?, completed, lastUpdated, deadline.?).mapTo[Task]
}
