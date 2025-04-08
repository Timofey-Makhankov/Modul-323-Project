package ch.main.db.model.task

import ch.main.db.{connection, createTables}
import slick.lifted.TableQuery.Extract

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration

object TaskService {
  def getAllTasks: Seq[Task] = {
    val db = connection
    try {
      val result: Seq[Task] = Await.result(db.run(TaskAction.getAll), Duration.Inf)
      result
    } finally db.close
  }
}
