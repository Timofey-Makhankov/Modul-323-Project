import scala.concurrent.ExecutionContext.Implicits.global
import slick.jdbc.H2Profile.api.*

import java.time.LocalDate
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration

@main
def main(): Unit = {
  val db = Database.forConfig("sqlite")
  try {
    val movieTable = TableQuery[MovieTable]
    // Creating the Schema
    Await.result(db.run(movieTable.schema.create), Duration.Inf)
    val insertQuery = DBIO.seq(
      movieTable += Movie(0, "Test", LocalDate.now(), 34),
      movieTable += Movie(0, "Test 2", LocalDate.now(), 64)
    )
    // Inserting some Data
    Await.result(db.run(insertQuery), Duration.Inf)
    // Querying the Data
    println(Await.result(db.run(movieTable.result), Duration.Inf))
    } finally db.close
}

final case class Movie(
                      id: Int,
                      name: String,
                      releaseDate: LocalDate,
                      lengthInMin: Int
                      )

class MovieTable(tag: Tag) extends Table[Movie](tag, None, "Movie") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def releaseDate = column[LocalDate]("release_date")
  def lengthInMin = column[Int]("length_in_min")
  override def * = (id, name, releaseDate, lengthInMin) <> ((Movie.apply _).tupled, Movie.unapply)
}