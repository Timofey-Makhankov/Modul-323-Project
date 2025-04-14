package ch.main.db.model.task

import slick.jdbc.SQLiteProfile.api.Database

import scala.concurrent.Await
import scala.concurrent.duration.*

object TaskService {
  def getAllTasks(db: Database): Seq[Task] = {
    Await.result(db.run(TaskAction.getAll), Duration.Inf)
  }
  
  def getAllSubtasksByParentId(db: Database): Seq[Task] = {
    Await.result(db.run(TaskAction.getAllSubTasksById), Duration.Inf)
  }
  
  def getTaskById(db: Database, id: Int): Task = {
    Await.result(db.run(TaskAction.getById(id)), Duration.Inf)
  }
  
  def AddTask(db: Database, task: Task): Unit = {
    Await.result(db.run(TaskAction.insert(task)), Duration.Inf)
  }
  
  def updateTask(db: Database, task: Task): Unit = {
    Await.result(db.run(TaskAction.update(task)), Duration.Inf)
  }

  def deleteTask(db: Database, id: Int): Unit = {
    Await.result(db.run(TaskAction.delete(id)), Duration.Inf)
  }
}
