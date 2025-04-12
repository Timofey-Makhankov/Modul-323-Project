package ch.main.db.model.task

import ch.main.db.{connection, createTables}
import slick.lifted.TableQuery.Extract

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object TaskService {
  val db = connection
  def getAllTasks: Seq[Task] = {
    try {
      val result: Seq[Task] = Await.result(db.run(TaskAction.getAll), Duration.Inf)
      result
    } finally db.close
  }

  def searchTasksByTitle(searchTerm: String): Unit = {
    val searchAction = TaskAction.searchByTitle(searchTerm)
    val resultFuture = db.run(searchAction)

    val tasksList = Await.result(resultFuture, 10.seconds)

    if (tasksList.nonEmpty) {
      tasksList.foreach(task => {
        println(s"Task ID: ${task.id}, Title: ${task.title}, Category: ${task.categoryId.getOrElse("No Category")}")
      })
    } else {
      println(s"No tasks found matching '$searchTerm'.")
    }
  }
  
  def readTask(searchId: String): Unit = {
    val readAction = TaskAction.readTask(searchId)
    val resultFuture = db.run(readAction)

    val tasksList = Await.result(resultFuture, 10.seconds)

    if (tasksList.nonEmpty) {
      tasksList.foreach(task => {
        println(s"Task ID: ${task.id}, Title: ${task.title}, Category: ${task.categoryId.getOrElse("No Category")}")
      })
    } else {
      println(s"No tasks found matching '$searchId'.")
    }
  }

//  def readTaskByTitle(title: String): Unit = {
//    val resultFuture = db.run(TaskAction.tasks.filter(_.title.toLowerCase === title.toLowerCase).result)
//    val taskList = Await.result(resultFuture, 10.seconds)
//
//    if (taskList.nonEmpty) {
//      val task = taskList.head
//
//      println(s"Title: ${task.title}")
//      println(s"Description: ${task.description.getOrElse("No description")}")
//      println(s"Category: ${task.categoryId.getOrElse("No Category")}")
//      println(s"Completed: ${task.completed}")
//      println(s"Last Updated: ${task.lastUpdated}")
//      println(s"Deadline: ${task.deadline.getOrElse("No deadline defined")}")
//      println(s"Parent Task ID: ${task.parentTaskId.getOrElse("No parent task")}")
//    } else {
//      println(s"No task found with title '$title'.")
//    }
//  }

  def deleteTask(id: Int): Unit = {
    val db = connection
    try {
      Await.result(db.run(TaskAction.delete(id)), Duration.Inf)
    } finally {
      db.close
    }
  }

  def updateTask(task: Task): Unit = {
    val db = connection
    try {
      Await.result(db.run(TaskAction.update(task)), Duration.Inf)
    } finally {
      db.close
    }
  }
}
