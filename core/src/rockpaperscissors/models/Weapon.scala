package rockpaperscissors.models

import rockpaperscissors.models.Result.{Draw, Lose, Win}

/**
  * Represents a weapon. In a fight a generic weapon has this behavior
  * based on its value:
  *
  * 1 It will tie with a weapon that has the same value
  * 2 If it's an odd value will win against higher odd values and lower even values
  *   (Conversely will lose against lower odd values and higher even values)
  * 3 If it's an even value will win against higher even values and lower odd values
  *   (Conversely will lose against lower even values and higher odd values)
  */
case class Weapon(value: Int, name: Option[String] = None) {
  def fight(other: Weapon): Result = {
    if (value == other.value) {
      Draw
    } else {
      val sameParity = (value - other.value) % 2 == 0
      if (sameParity) {
        if (value < other.value) Win else Lose
      } else {
        if (value > other.value) Win else Lose
      }
    }
  }

  override val toString = name.getOrElse(value.toString)
}

/**
  * A collection of weapons and weapon collections.
  * To be balanced a collection must contain an odd number of weapons
  * and should not contain duplicated values. Also, there must be an alternation
  * of even and odd numbers ([1,2,3,6,7] is balanced, [1,2,3,4,6] is not)
  */
object WeaponCollections {

  val Rock = Weapon(1, Some("rock"))
  val Paper = Weapon(2, Some("paper"))
  val Scissors = Weapon(3, Some("scissors"))

  val classicWeapons = Set(
    Rock, Paper, Scissors
  )

  val Spock = Weapon(4, Some("spock"))
  val Lizard = Weapon(5, Some("lizard"))

  val extendedWeapons = classicWeapons ++ Set(
    Spock, Lizard
  )

  val Well = Weapon(4, Some("well"))

  val unbalancedWeapons = classicWeapons + Well

}
