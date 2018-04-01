package rockpaperscissors.cli

import scala.annotation.tailrec
import scala.util.{Success, Try}

/**
  * Selector interface used to read cli commands and to allow
  * player to chose their weapon (could be abstracted a bit more
  * by using a Future as return value: at that point it could
  * be used in other contexts, e.g. to wait selection from a remote
  * client)
  */
abstract class Selector {
  def select[T](validChoices: Map[Int, T]): T
  def select(validChoices: Set[Int]): Int =
    select(validChoices.map(el => el -> el).toMap)
}

/**
  * Simple wrapper around console read calls
  * Main reason to introduce this is to be able to mock it
  * for testing (java.io.Console is final and can't be mocked)
  */
object ConsoleSelectorUtil {
  val console = System.console()

  /**
    * Keeps reading until it gets a valid value.
    * Uses tailrec to avoid stack overflows, just in case
    */
  @tailrec final def readUntilValidSelection[K, T](
    read: () => Try[K],
    validSelections: Map[K, T]
  ): T = {
    read() match {
      case Success(key) if validSelections.contains(key) =>
        validSelections(key)
      case _ =>
        println("Invalid choice, try again")
        readUntilValidSelection(read, validSelections)
    }
  }
}

/**
  * Reads an Int from the console
  */
object ConsoleSelector extends Selector {
  import ConsoleSelectorUtil._

  def select[T](validChoices: Map[Int, T]): T = {
    readUntilValidSelection(
      () => Try {
        console.readLine().toInt
      },
      validChoices
    )
  }
}

/**
  * Like ConsoleSelector, but user input is hidden to protect privacy
  * during 2 player games
  */
object ConsoleHiddenSelector extends Selector {
  import ConsoleSelectorUtil._

  def select[T](validChoices:  Map[Int, T]): T = {
    readUntilValidSelection(
      () => Try {
        console.readPassword().toSeq.mkString("").toInt
      },
      validChoices
    )
  }

}
