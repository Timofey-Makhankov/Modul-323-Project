package ch.main.db.model.task

import slick.jdbc.SQLiteProfile.api.*

object TaskAction {
  def getAll: DBIOAction[Unit, NoStream, Effect] = {
    //TODO implement getAll for Tasks
    DBIO.seq()
  }

  def insert(task: Task): DBIOAction[Unit, NoStream, Effect] = {
    //TODO implement insert for Tasks
    DBIO.seq()
  }
}
