package ch.main.db.model.task

import slick.dbio.Effect
import slick.jdbc.SQLiteProfile
import slick.jdbc.SQLiteProfile.api.*
import slick.lifted.TableQuery.Extract
import slick.sql.{FixedSqlAction, FixedSqlStreamingAction, SqlAction}

// (1) Pure Functions in use
object TaskAction {
  def getAll = {
    tasksSchema.result
  }

  def getById(id: Int): SqlAction[Extract[TaskTable], NoStream, Effect.Read] = {
    tasksSchema.filter(_.id === id).result.head
  }

  def getAllSubTasksById: FixedSqlStreamingAction[Seq[Extract[TaskTable]], Extract[TaskTable], Effect.Read] = {
    tasksSchema.filter(_.parentTaskId.nonEmpty).result
  }

  def insert(task: Task): FixedSqlAction[Int, NoStream, Effect.Write] = {
    tasksSchema += task
  }

  // (6) Umsetzung von Pipelines -> .filter().map().update()
  def update(task: Task): FixedSqlAction[Int, NoStream, Effect.Write] = {
    tasksSchema.filter(_.id === task.id.get).update(task)
  }

  def delete(id: Int): FixedSqlAction[Int, NoStream, Effect.Write] = {
    tasksSchema.filter(_.id === id).delete
  }
}
