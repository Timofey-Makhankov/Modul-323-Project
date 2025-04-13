package ch.main.db.model.task

import java.sql.Timestamp
import java.time.LocalDateTime

case class Task(
                 id: Option[Int] = None,
                 title: String,
                 description: Option[String] = None,
                 categoryId: Option[Int] = None,
                 parentTaskId: Option[Int] = None,
                 completed: Boolean = false,
                 lastUpdated: Timestamp = Timestamp.valueOf(LocalDateTime.now()),
                 deadline: Option[Timestamp] = None
               )
