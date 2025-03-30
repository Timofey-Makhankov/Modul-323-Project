package ch.main.textInterface

object Command {
  def addTask(title: String): Unit = {
    printf("adding %s\n", title)
  }
}
