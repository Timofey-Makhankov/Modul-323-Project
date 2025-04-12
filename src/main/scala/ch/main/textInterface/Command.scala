package ch.main.textInterface

import ch.main.db.model.task.{Task, TaskAction, TaskService}
import slick.jdbc.SQLiteProfile.api.*
import java.time.LocalDateTime
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._


import java.time.LocalDateTime

object Command {
  def listTasks(): Unit = {
    val allTasks = TaskService.getAllTasks
    allTasks.foreach(task => {
      println(s"ID: ${task.id}, " +
        s"Title: ${task.title}, Description: ${task.description}, " +
        s"Category: ${task.categoryId.getOrElse("No Category")}, " +
        s"Completed: ${task.completed}, Last Updated: ${task.lastUpdated}, " +
        s"Deadline: ${task.deadline.getOrElse("No deadline defined")}")
    })
  }

  def addTask(id: String, title: String, description: Option[String], categoryId: Option[String],parentTaskId: Option[String], deadline: Option[String]): Unit = {
    val db = Database.forConfig("sqlite")
    val newTask = Task(
      id = None,
      title = title,
      categoryId = None,
      description = None,
      deadline = None,
      parentTaskId = None
    )
    val insertAction = TaskAction.insert(newTask)
    val resultFuture = db.run(insertAction)
      printf("adding %s\n", title)
  }

  def updateTask(id: String, title: String, description: Option[String], categoryId: Option[String],parentTaskId: Option[String], deadline: Option[String]): Unit = {
    val updatedTask = Task(
      id = None,
      title = title,
      categoryId = None,
      description = None,
      deadline = None,
      parentTaskId = None
    )
    val updateAction = TaskAction.update(updatedTask)
    printf("updating %s\n", title)
  } //update $id

  def deleteTask(id: String): Unit = {
    val index = id.toIntOption
    index match
      case None => sendErrorMessage("The provided Index is not a valid Index number")
      case Some(value) => {
        TaskService.deleteTask(value)
        println("Successfully deleted a task")
      }
  }

  def sendErrorMessage(message: String): Unit = {
    println(message)
  }

  def searchTasksByTitle(title: String): Unit = {
    val db = Database.forConfig("sqlite")
    val searchTask = Task(
      id = None,
      title = title,
      categoryId = None,
      description = None,
      deadline = None,
      parentTaskId = None
    )
    val searchResult = TaskService.searchTasksByTitle(title)
    println(s"Id: ${searchTask.id}, Title: ${searchTask.title}, Description: ${searchTask.description}, " +
      s"Category: ${searchTask.categoryId.getOrElse("No Category")}, " +
      s"Completed: ${searchTask.completed}, Last Updated: ${searchTask.lastUpdated}, " +
      s"Deadline: ${searchTask.deadline.getOrElse("No deadline defined")}")
  }

  def readTask(id: String): Unit = {
    val db = Database.forConfig("sqlite")
    val readTask = TaskService.readTask(id)
    println(s"Title: ${readTask.title}, Description: ${readTask.description}, " +
      s"Category: ${readTask.categoryId.getOrElse("No Category")}, " +
      s"Completed: ${readTask.completed}, Last Updated: ${readTask.lastUpdated}, " +
      s"Deadline: ${readTask.deadline.getOrElse("No deadline defined")}")
  }
}
