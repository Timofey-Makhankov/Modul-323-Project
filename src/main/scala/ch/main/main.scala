package ch.main

import ch.main.db.model.task.TaskAction
import ch.main.db.{connection, createTables, generateMockData}
import ch.main.textInterface.Command

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.io.Source.fromFile
import scala.io.StdIn.readLine

@main
def main(): Unit = {
  var running: Boolean = true
  // put into impure function
  val db = connection
  try {
    // Creating the tables, If they don't exist
    Await.result(db.run(createTables), Duration.Inf)
    if (doGenerateMockData()) {
      Await.result(db.run(generateMockData), Duration.Inf)
    }
    while (running) {
      val input = readLine("> ").toLowerCase()
      // (4) Pattern Matching in use
      input match
        case "h" | "help" => {
          // Put it into an impure function
          val helpMessageSource = fromFile("src/main/resources/help_message.txt")
          val helpMessageLines = try helpMessageSource.mkString finally helpMessageSource.close()
          println(helpMessageLines)
        }
        case "list" => Command.listTasks()
        case s"add $id, $title, $description, $categoryId, $parentTaskId, $deadline" => Command.addTask(id, title, Option(description), Option(categoryId), Option(parentTaskId), Option(deadline))
        case s"read $id" => Command.readTask(id)
        case s"update $id, $title, $description, $categoryId, $parentTaskId, $deadline" => Command.updateTask(id, title, Option(description), Option(categoryId), Option(parentTaskId), Option(deadline))
        case s"delete $id" => Command.deleteTask(id)
        case s"search $title" => Command.searchTasksByTitle(title)
        case "q" | "exit" | "quit" => running = false
        case _ => Command.sendErrorMessage("Invalid Input, Please try again")
    }
  } finally db.close
}