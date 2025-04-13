package ch.main.db.model.category

import slick.jdbc.SQLiteProfile.api.*

class CategoryTable(tag: Tag) extends Table[Category](tag, Option.empty, "categories") {
  def id = column[Int]("id", O.PrimaryKey)
  def name = column[String]("name", O.Unique)
  override def * = (id.?, name).mapTo[Category]
}
