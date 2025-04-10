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

  def deleteTask(id: Int): Unit = {
    val db = connection
    try {
      Await.result(db.run(TaskAction.delete(id)), Duration.Inf)
    } finally {
      db.close
    }
  }
}
