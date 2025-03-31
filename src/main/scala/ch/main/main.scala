package ch.main

import ch.main.db.{connection, createTables, generateMockData}
import ch.main.textInterface.Command

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.io.Source.fromFile
import scala.io.StdIn.readLine

@main
def main(): Unit = {
  var running: Boolean = true
  val db = connection
  try {
    // Creating the tables, If they don't exist
    Await.result(db.run(createTables), Duration.Inf)
    if (doGenerateMockData()) {
      Await.result(db.run(generateMockData), Duration.Inf)
    }
    while (running) {
      val input = readLine("> ").toLowerCase()
      input match
        case "h" | "help" => {
          val helpMessageSource = fromFile("src/main/resources/help_message.txt")
          val helpMessageLines = try helpMessageSource.mkString finally helpMessageSource.close()
          println(helpMessageLines)
        }
        case  "q" | "exit" => running = false
        case  "list" => println("listing tasks, TODO")
        case  s"add $title" => Command.addTask(title)
        case _ => println("Invalid Input, Please try again")
    }
  } finally db.close
}