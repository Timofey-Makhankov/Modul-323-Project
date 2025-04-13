package ch.main.textInterface

import ch.main.db.model.category.CategoryService
import ch.main.db.model.task.{Task, TaskService}
import slick.jdbc.SQLiteProfile.api.*

import java.sql.Timestamp
import java.time.LocalDateTime

object Command
{
  // Complete
  def printHelpMessage(): Unit = {
    val message = readFile("src/main/resources/help_message.txt")
    message match
      case Some(value) => println(value)
      case None => sendErrorMessage("unable to display message, please read written doc")
  }

  // Complete
  def sendErrorMessage(message: String): Unit = {
    println(message)
  }

  //complete
  def listTasks(db: Database): Unit = {
    val content = readFile("src/main/resources/task_display.txt")
    content match
      case None => sendErrorMessage("unable to get task template, please report the issue to us")
      case Some(template) => {
        val tasks = TaskService.getAllTasks(db)
        tasks.foreach(task => {
          val values: Seq[String] = generateTemplateData(db, task)
          printf(
            template + "%nSubtasks: %s" + "%n",
            values.appended(TaskService.getAllSubtasksByParentId(db).count(_.parentTaskId.get == task.id.get).toString): _*)
          print("\n")
        })
      }
  }
  // done
  def listCategories(db: Database): Unit = {
    val categories = CategoryService.getAllCategories(db)
    for (c <- categories) yield printf("%d: %s%n", c.id.get, c.name)
  }
  // done
  def showTaskDetails(db: Database, id: String): Unit = {
    id.toIntOption match
      case None => sendErrorMessage("Invalid Id, please try again")
      case Some(id) => {
        val content = readFile("src/main/resources/task_display.txt")
        content match
          case None => sendErrorMessage("unable to get task template, please report the issue to us")
          case Some(template) =>{
            val task = TaskService.getTaskById(db, id)
            val subtasks = TaskService.getAllSubtasksByParentId(db).filter(_.parentTaskId.get == task.id.get)
            val values: Seq[String] = generateTemplateData(db, task)
            printf(template + "%n%n", values: _*)
            print("Subtasks :\n")
            subtasks.foreach(t => {
              printf(template + "%n", generateTemplateData(db, t): _*)
            })
          }
      }
  }
  // done
  def addCategory(db: Database, name: String): Unit = {
    CategoryService.addCategory(db, name)
    println("Saved Category")
  }
  // done
  def addNewTask(db: Database, title: String): Unit = {
    val description = askUserForDescription
    val categoryId = askUserForCategory(db)
    val deadline = askUserForDeadline
    val parentId = askUserToAddTaskAsSubtask(db)
    val task = Task(
      title = title,
      description = description,
      categoryId = categoryId,
      deadline = deadline match
      case Some(value) => Some(Timestamp.valueOf(value))
      case None => None
      , parentTaskId = parentId,
    )
    TaskService.AddTask(db, task)
    println("Task Added")
  }
  // done
  def addFullTask(db: Database, title: String, description: String, completed: String, deadline: String): Unit = {
    val categoryId = askUserForCategory(db)
    val parentId = askUserToAddTaskAsSubtask(db)
    val task = Task(
      title = title,
      description = Option(description),
      categoryId = categoryId,
      deadline = Option(Timestamp.valueOf(LocalDateTime.parse(description))),
      parentTaskId = parentId,
      completed = if(completed.toInt == 1) true else false
    )
    TaskService.AddTask(db, task)
    println("Task Added")
  }

  def updateTaskTitle(db: Database, id: String, value: String): Unit = {
    val task = TaskService.getTaskById(db, id.toInt)
    val updated = task.copy(title = value)
    TaskService.updateTask(db, updated)
    println("Task Updated")
  }

  def updateTaskDescription(db: Database, id: String, value: String): Unit = {
    val task = TaskService.getTaskById(db, id.toInt)
    val updated = task.copy(description = Option(value))
    TaskService.updateTask(db, updated)
    println("Task Updated")
  }

  def updateTaskCategory(db: Database, id: String, value: String): Unit = {
    val task = TaskService.getTaskById(db, id.toInt)
    val updated = task.copy(categoryId = Option(value.toInt))
    TaskService.updateTask(db, updated)
    println("Task Updated")
  }

  def updateTaskDeadline(db: Database, id: String, value: String): Unit = {
    val task = TaskService.getTaskById(db, id.toInt)
    val updated = task.copy(deadline = Option(Timestamp.valueOf(LocalDateTime.parse(value))))
    TaskService.updateTask(db, updated)
    println("Task Updated")
  }

  def updateTaskSubtask(db: Database, id: String, value: String): Unit = {
    val task = TaskService.getTaskById(db, id.toInt)
    val updated = task.copy(parentTaskId = Option(value.toInt))
    TaskService.updateTask(db, updated)
    println("Task Updated")
  }

  def setTaskComplete(db: Database, id: String): Unit = {
    val task = TaskService.getTaskById(db, id.toInt)
    val updated = task.copy(completed = true)
    TaskService.updateTask(db, updated)
    println("Task Completed")
  }

  def setTaskIncomplete(db: Database, id: String): Unit = {
    val task = TaskService.getTaskById(db, id.toInt)
    val updated = task.copy(completed = false)
    TaskService.updateTask(db, updated)
    println("Task Incomplete")
  }

  def deleteTask(db: Database, id: String): Unit = {
    println(id)
    TaskService.deleteTask(db, id.toInt)
    println("Task deleted")
  }

  def deleteCategory(db: Database, id: String): Unit = {
    CategoryService.deleteTask(db, id.toInt)
    println("Category deleted")
  }
}
