package ch.main.db.model.task

import slick.jdbc.SQLiteProfile.api.TableQuery

val tasksSchema = TableQuery[TaskTable]
