package ch.main.db.model.task

import slick.dbio.Effect
import slick.jdbc.SQLiteProfile.api.*
import slick.sql.FixedSqlAction

object TaskAction {
  def getAll: DBIOAction[Unit, NoStream, Effect] = {
    //TODO implement getAll for Tasks
    DBIO.seq()
  }

  def insert(task: Task): DBIOAction[Unit, NoStream, Effect] = {
    //TODO implement insert for Tasks
    DBIO.seq()
  }

  def delete(id: Int): FixedSqlAction[Int, NoStream, Effect.Write] = {
    tasksSchema.filter(_.id === id).delete
  }
}
