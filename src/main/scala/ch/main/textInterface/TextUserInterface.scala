package ch.main.textInterface

import scala.io.StdIn.readLine
import slick.jdbc.SQLiteProfile.api.Database

def startTextUserInterface(db: Database): Unit = {
  var isRunning = true
  while (isRunning) {
    val input = readLine("> ").toLowerCase() // (4) Pattern Matching in use
    input match {
      case "h" | "help" => Command.printHelpMessage()
      case "list" => Command.listTasks(db)
      //case s"list sort $key $order" => Command.listTasks(db, key, order)
      case "list categories" => Command.listCategories(db)
      case s"show $id" => Command.showTaskDetails(db, id)
      case s"add category $name" => Command.addCategory(db, name)
      case s"add $title" => Command.addNewTask(db, title)
      case s"add $title, $description, $completed, $deadline" => Command.addFullTask(db, title, description, completed, deadline)
      case s"update $id title $value" => Command.updateTaskTitle(db, id, value)
      case s"update $id description $value" => Command.updateTaskDescription(db, id, value)
      case s"update $id category $value" => Command.updateTaskCategory(db, id, value)
      case s"update $id deadline $value" => Command.updateTaskDeadline(db, id, value)
      case s"update $id subtask $value" => Command.updateTaskSubtask(db, id, value)
      case s"set $id completed" => Command.setTaskComplete(db, id)
      case s"set $id incompleted" => Command.setTaskIncomplete(db, id)
      case s"delete category $id" => Command.deleteCategory(db, id)
      case s"delete $id" => Command.deleteTask(db, id)
      case "q" | "exit" | "quit" => isRunning = false
      case _ => Command.sendErrorMessage("Invalid Input, Please try again")
    }
  }
}
