# Building the Project

The Project uses Intellij with the Scala plugin to develop the Application. The Application also uses a Local SqLite Database to store the data to.

The Database file is stored in the projects resources folder.

## Database Schema

![Database Schema](./Database%20Schema.png)

The Database uses the default Schema in SQlite (main) and includes two Tables

- Categories - Which includes available Categories to be set for a Task
- Tasks - The Tasks themselves

There is also a references to itself in the tasks table. This allow us to have a child and parent relation on the tasks. Making it possible to have subtasks

## Dependencies

### Slick - Advanced, Comprehensive Database Access library

### SQLite JDCB - SQLite JDCB Driver for Slick

### H2 DB - H2 Driver for Slick (For Testing purposes only. Will be removed later)

### slf4j-nop - Disabled Logging for Slick (may be enabled in the future)

## Build a Jar file (TODO - figure out)