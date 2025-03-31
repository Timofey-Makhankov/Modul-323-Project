package ch.main.db

import ch.main.db.model.category.{Category, categoriesSchema}
import ch.main.db.model.task.{Task, tasksSchema}
import slick.jdbc.SQLiteProfile.api.*

import java.time.LocalDateTime

val connection = Database.forConfig("sqlite")
val createTables = DBIO.seq((
  categoriesSchema.schema ++ tasksSchema.schema
  ).createIfNotExists)
val generateMockData = DBIO.seq(
  categoriesSchema ++= Seq(
    Category(name = "Home"),
    Category(name = "work"),
    Category(name = "School")
  ),
  tasksSchema ++= Seq(
    Task(title = "Clean house", categoryId = Some(1)),
    Task(title = "Finish Homework", categoryId = Some(3), deadline = Some(LocalDateTime.of(2025, 3, 15, 9, 30, 0))),
    Task(title = "Finish Task", categoryId = Some(2), description = Some("Look at jira for the task description")),
    Task(title = "Plan for Malta Trip")
  ),
  tasksSchema ++= Seq(
    Task(title = "Clean dishes", categoryId = Some(1), parentTaskId = Some(1)),
    Task(title = "vacuum floor", categoryId = Some(1), parentTaskId = Some(1))
  )
)