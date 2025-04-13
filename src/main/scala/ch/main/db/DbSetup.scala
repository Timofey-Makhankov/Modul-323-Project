package ch.main.db

import ch.main.db.model.category.{Category, categoriesSchema}
import ch.main.db.model.task.{Task, tasksSchema}
import slick.jdbc.SQLiteProfile.api.*

import java.sql.Timestamp
import java.time.LocalDateTime
import scala.concurrent.Await
import scala.concurrent.duration.Duration

def createTables = DBIO.seq((
  categoriesSchema.schema ++ tasksSchema.schema
  ).createIfNotExists)

// (1) Pure Function in use
def generateMockData = DBIO.seq(
  categoriesSchema ++= Seq(
    Category(name = "Home"),
    Category(name = "work"),
    Category(name = "School")
  ),
  tasksSchema ++= Seq(
    Task(title = "Clean house", categoryId = Some(1)),
    Task(title = "Finish Homework", categoryId = Some(3), deadline = Some(Timestamp.valueOf(LocalDateTime.of(2025, 3, 15, 9, 30, 0)))),
    Task(title = "Finish Task", categoryId = Some(2), description = Some("Look at jira for the task description")),
    Task(title = "Plan for Malta Trip")
  ),
  tasksSchema ++= Seq(
    Task(title = "Clean dishes", categoryId = Some(1), parentTaskId = Some(1)),
    Task(title = "vacuum floor", categoryId = Some(1), parentTaskId = Some(1))
  )
)

def runStartup(db: Database, genMocks: Boolean): Unit = {
  Await.result(db.run(createTables), Duration.Inf)
  if (genMocks) {
    Await.result(db.run(generateMockData), Duration.Inf)
  }
}