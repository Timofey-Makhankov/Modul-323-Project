package ch.main.db

import ch.main.db.model.category.categoriesSchema
import ch.main.db.model.task.tasksSchema
import slick.jdbc.SQLiteProfile.api.*

val connection = Database.forConfig("sqlite")
val createTables = DBIO.seq((
  categoriesSchema.schema ++ tasksSchema.schema
  ).createIfNotExists)
