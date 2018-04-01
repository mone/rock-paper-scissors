package rockpaperscissors.cli

import rockpaperscissors.models.{Weapon, WeaponCollections}
import rockpaperscissors.models.Result.{Draw, Lose, Win}

class RockPaperScissorsCli(
  selector: Selector,
  hiddenSelector: Selector
) {

  /**
    * Chooses a weapon for a CPU player
    */
  def generateCPUPlayer(
    availableWeapons: Set[Weapon]
  ): Player = {
    val index = math.round(math.random() * (availableWeapons.size - 1)).toInt
    Player("CPU", availableWeapons.toSeq(index))
  }

  /**
    * Reads a weapon from the console for the human player
    */
  def readPlayer(
    playerNum: Int,
    availableWeapons: Set[Weapon],
    selector: Selector
  ): Player = {
    println()
    println(s"Player $playerNum, choose your weapon (NOTE: input may be hidden)")
    // sort weapons by value before printing to make it look nice :)
    availableWeapons.toSeq.sortBy(_.value).foreach(weapon =>
      println(s"[${weapon.value}] $weapon")
    )

    val weapon = selector.select(
      // make it into a map keyed on the value; duplicate values are overwritten,
      // we don't care as the end result would be the same
      availableWeapons.map(w => w.value -> w).toMap
    )

    Player(s"Player $playerNum", weapon)
  }

  /**
    * Reads the number of players and their moves from the console
    */
  def readPlayersMove(availableWeapons: Set[Weapon]): (Player, Player) = {
    println(
      """Press
        |[1] To play against the CPU
        |[2] To play against a human""".stripMargin)

    selector.select(Set(1, 2)) match {
      case 1 =>
        (readPlayer(1, availableWeapons, selector), generateCPUPlayer(availableWeapons))
      case 2 =>
        (
          // when we have two players we use the hiddenSelector so that player moves are not
          // printed on screen
          readPlayer(1, availableWeapons, hiddenSelector),
          readPlayer(2, availableWeapons, hiddenSelector)
        )
    }
  }

  /**
    * Reads the type of game the user wants to play from the console
    */
  def readGameType(
    games: Set[Set[Weapon]]
  ): Set[Weapon] = {
    println("Specify the game you want to play")

    val gamesWithIndex = games.zipWithIndex.map {
      case (weapons, index) => (index + 1) -> weapons
    }

    gamesWithIndex.foreach {
      case (index, weapons) => {
        println(s"[$index] ${weapons.toSeq.sortBy(_.value).mkString(", ")}")
      }
    }

    selector.select(gamesWithIndex.toMap)

  }

  def play: Unit = {
    val weapons = readGameType(
      Set(
        WeaponCollections.classicWeapons,
        WeaponCollections.extendedWeapons,
        WeaponCollections.unbalancedWeapons
      )
    )
    val (player1, player2) = readPlayersMove(weapons)
    val weapon1 = player1.weapon
    val weapon2 = player2.weapon
    println(s"$weapon1 VS $weapon2")
    weapon1 fight weapon2 match {
      case Win =>
        println(s"${player1.name} wins!")
      case Lose =>
        println(s"${player2.name} wins!")
      case Draw =>
        println(s"It's a draw!")
    }
  }
}

object RockPaperScissorsCli extends App {

  val game = new RockPaperScissorsCli(ConsoleSelector, ConsoleHiddenSelector)

  println("Rock Paper Scissor, ctrl-c to quit")
  while(true) {
    println("************************")
    game.play
    println("************************")
  }

}
