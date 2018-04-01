package rockpaperscissors.cli

import rockpaperscissors.models.Weapon

/**
  * Simple tuple to keep weapon and player name together
  */
case class Player(
  name: String,
  weapon: Weapon
)
