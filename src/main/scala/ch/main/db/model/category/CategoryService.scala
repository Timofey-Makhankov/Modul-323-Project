package ch.main.db.model.category

import slick.jdbc.SQLiteProfile.api.Database

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object CategoryService {
  def getAllCategories(db: Database): Seq[Category] = {
    Await.result(db.run(CategoryAction.getAll), Duration.Inf)
  }
  
  def getCategoryById(db: Database, id: Int): Category = {
    Await.result(db.run(CategoryAction.getById(id)), Duration.Inf)
  }
  
  def addCategory(db: Database, name: String): Unit = {
    Await.result(db.run(CategoryAction.insert(Category(None, name))), Duration.Inf)
  }
  
  def deleteTask(db: Database, id: Int): Unit = {
    Await.result(db.run(CategoryAction.delete(id)), Duration.Inf)
  }
}
