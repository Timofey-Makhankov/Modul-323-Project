package ch.main.db.model.task

import slick.dbio.Effect
import slick.jdbc.SQLiteProfile.api.*
import slick.sql.FixedSqlAction

object TaskAction {
  def getAll = {
    tasksSchema.result
  }

  def insert(task: Task): DBIOAction[Unit, NoStream, Effect.Write] = {
    DBIO.seq(tasksSchema += task)
  }

  // (6) Umsetzung von Pipelines -> .filter().map().update()
  def update(task: Task): FixedSqlAction[Int, NoStream, Effect.Write] = {
    tasksSchema.filter(_.id === task.id.get)
      .map(t => (t.title, t.categoryId, t.description, t.deadline, t.parentTaskId))
      .update((task.title, task.categoryId, task.description, task.deadline, task.parentTaskId))
  }

  def delete(id: Int): FixedSqlAction[Int, NoStream, Effect.Write] = {
    tasksSchema.filter(_.id === id).delete
  }

  def searchByTitle(searchTerm: String): DBIOAction[Seq[Task], NoStream, Effect.Read] = {
    tasksSchema.filter(_.title.toLowerCase like s"%${searchTerm.toLowerCase}%").result
  }

  
}
