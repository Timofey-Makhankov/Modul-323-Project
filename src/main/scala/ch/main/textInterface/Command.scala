package ch.main.textInterface

import ch.main.db.model.task.TaskService

object Command {
  def addTask(title: String): Unit = {
    printf("adding %s\n", title)
  }
  def deleteTask(id: String): Unit = {
    val index = id.toIntOption
    index match
      case None => sendErrorMessage("The provided Index is not a valid Index number")
      case Some(value) => {
        TaskService.deleteTask(value)
        println("Successfully deleted a task")
      }
  }

  def sendErrorMessage(message: String): Unit = {
    println(message)
  }
}
