package ch.main.textInterface

import ch.main.db.model.category.CategoryService
import ch.main.db.model.task.{Task, TaskService}

import scala.io.Source.fromFile
import scala.util.{Failure, Success, Using}
import slick.jdbc.SQLiteProfile.api.Database

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import scala.io.StdIn.readLine

// making a non-pure function into pure function
def readFile(location: String): Option[String] = {
  Using(fromFile(location)) { reader =>
    reader.mkString
  } match
    case Failure(exception) => None
    case Success(value) => Some(value)
}

def generateTemplateData(db: Database, task: Task): Seq[String] = {
  Seq(
    task.id.get.toString,
    task.title,
    task.description.getOrElse(""),
    if (task.completed) "✓" else "X",
    task.deadline match
      case Some(date) => SimpleDateFormat("dd.MM.yyyy").format(date)
      case None => "None",
    task.categoryId match
      case Some(id) => CategoryService.getCategoryById(db, id).name
      case None => "None",
  )
}

def askUserForDescription: Option[String] = {
  var result: Option[String] = None
  var running = true
  while (running) {
    val input = readLine("Do you want to add a Description? N/y >").toLowerCase().strip()
    if (input == "y") {
      val description = readLine("Enter a description >")
      result = Some(description)
      running = false
    } else if (input == "n") {
      running = false
    } else {
      println("Please enter a valid Response")
    }
  }
  result
}
def askUserForCategory(db: Database): Option[Int] = {
  var result: Option[Int] = None
  var running = true
  while (running) {
    val input = readLine("Do you want to add a Category? N/y >").toLowerCase().strip()
    if (input == "y") {
      val categories = CategoryService.getAllCategories(db)
      categories.foreach((c) => printf("%d: %s%n", c.id.get, c.name))
      val id = readLine("choose a category id >")
      result = Some(id.toInt)
      running = false
    } else if (input == "n") {
      running = false
    } else {
      println("Please enter a valid Response")
    }
  }
  result
}
def askUserForDeadline: Option[LocalDateTime] = {
  var result: Option[LocalDateTime] = None
  var running = true
  while (running) {
    val input = readLine("Do you want to add a Deadline? N/y >").toLowerCase().strip()
    if (input == "y") {
      val stringDate = readLine("Enter a deadline (YYYY-mm-dd) >")
      result = Some(LocalDateTime.parse(stringDate))
      running = false
    } else if (input == "n") {
      running = false
    } else {
      println("Please enter a valid Response")
    }
  }
  result
}
def askUserToAddTaskAsSubtask(db: Database): Option[Int] = {
  var result: Option[Int] = None
  var running = true
  while (running) {
    val input = readLine("Do you want to add task as a subtask? N/y >").toLowerCase().strip()
    if (input == "y") {
      val tasks = TaskService.getAllTasks(db).filter(_.parentTaskId.isEmpty)
      tasks.foreach((t) => printf("%d: %s%n", t.id.get, t.title))
      val taskId = readLine("Enter a task id >")
      result = Some(taskId.toInt)
      running = false
    } else if (input == "n") {
      running = false
    } else {
      println("Please enter a valid Response")
    }
  }
  result
}
