package ch.main

import slick.jdbc.SQLiteProfile.api.Database
import ch.main.db.runStartup
import ch.main.textInterface.startTextUserInterface

@main def main(): Unit = {
  val db: Database = Database.forConfig("sqlite")
  try {
    runStartup(db, doGenerateMockData)
    startTextUserInterface(db)
  }
  finally db.close
}