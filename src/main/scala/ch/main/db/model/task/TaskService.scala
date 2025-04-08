package ch.main.db.model.task

import ch.main.db.{connection, createTables}
import slick.lifted.TableQuery.Extract

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object TaskService {
  def getAllTasks: Seq[Task] = {
    val db = connection
    try {
      val result: Seq[Task] = Await.result(db.run(TaskAction.getAll), Duration.Inf)
      result
    } finally db.close
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
