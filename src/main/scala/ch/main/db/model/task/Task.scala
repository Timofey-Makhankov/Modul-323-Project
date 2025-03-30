package ch.main.db.model.task

import java.time.LocalDateTime

case class Task(
                 id: Option[Int], 
                 title: String, 
                 description: Option[String], 
                 categoryId: Option[Int], 
                 parentTaskId: Option[Int], 
                 completed: Boolean = false,
                 lastUpdated: LocalDateTime = LocalDateTime.now(), 
                 deadline: Option[LocalDateTime]
               )
