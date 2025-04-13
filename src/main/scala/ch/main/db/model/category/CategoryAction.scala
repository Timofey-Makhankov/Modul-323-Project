package ch.main.db.model.category

import slick.dbio.Effect
import slick.jdbc.SQLiteProfile.api.*
import slick.sql.{FixedSqlAction, SqlAction}

object CategoryAction {
  def getAll = {
    categoriesSchema.result
  }

  def getById(id: Int): SqlAction[Category, NoStream, Effect.Read] = {
    categoriesSchema
      .filter(_.id === id)
      .result.head
  }

  def insert(category: Category): FixedSqlAction[Int, NoStream, Effect.Write] = {
    categoriesSchema += category
  }

  def delete(id: Int): FixedSqlAction[Int, NoStream, Effect.Write] = {
    categoriesSchema.filter(_.id === id).delete
  }
}
