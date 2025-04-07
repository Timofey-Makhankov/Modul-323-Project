package ch.main.db.model.task

import ch.main.db.connection

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object TaskService {
  def getAllTasks(): Unit = {
    // TODO Implement
  }
  
  def deleteTask(id: Int): Unit = {
    val db = connection
    try {
      Await.result(db.run(TaskAction.delete(id)), Duration.Inf)
    } finally { db.close }
  }
}
