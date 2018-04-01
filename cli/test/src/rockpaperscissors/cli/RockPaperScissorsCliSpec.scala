package rockpaperscissors.cli

import rockpaperscissors.BaseSpec
import org.mockito.{ArgumentMatchers, Mockito}
import rockpaperscissors.models.Weapon

class RockPaperScissorsCliSpec extends BaseSpec {
  import RockPaperScissorsCliSpec._

  trait Fixtures {
    val selector = mock[Selector]

    val game = new RockPaperScissorsCli(selector, selector)
  }

  behavior of "RockPaperScissorsCli"

  it should "read a game type" in new Fixtures {

    val game1 = Set(Weapon(1))
    val game2 = Set.empty[Weapon]

    Mockito.when(selector.select[Set[Weapon]](ArgumentMatchers.any())).thenReturn(game1)

    game.readGameType(
      Set(
        game1, game2
      )
    ) should be (game1)

    Mockito.verify(selector, Mockito.times(1))
      .select[Set[Weapon]](ArgumentMatchers.any())

  }

  it should "read weapon from console" in new Fixtures {

    Mockito.when(
      selector.select[Weapon](ArgumentMatchers.eq(weaponsMap))
    ).thenReturn(
      weapon1
    )

    val humanPlayer = game.readPlayer(1, weapons, selector)

    humanPlayer.weapon should be (weapon1)

    Mockito.verify(selector, Mockito.times(1))
      .select[Weapon](ArgumentMatchers.eq(weaponsMap))
  }

  it should "return a weapon from the set" in new Fixtures {
    val cpu = game.generateCPUPlayer(weapons)

    weapons should contain (cpu.weapon)
  }

  "when '2 players' is selected" should "generate 2 human players" in new Fixtures {

    // number of players selection
    Mockito.when(selector.select(ArgumentMatchers.eq(Set(1, 2)))).thenReturn(2)
    // player weapons selections
    Mockito.when(selector.select[Weapon](ArgumentMatchers.eq(weaponsMap)))
      .thenReturn(weapon1, weapon2)

    val (p1, p2) = game.readPlayersMove(weapons)

    p1 should be (Player("Player 1", weapon1))
    p2 should be (Player("Player 2", weapon2))

    Mockito.verify(selector, Mockito.times(1))
      .select(ArgumentMatchers.eq(Set(1, 2)))
    Mockito.verify(selector, Mockito.times(2))
      .select[Weapon](ArgumentMatchers.eq(weaponsMap))

  }

  "when '1 player' is selected" should "generate 1 human player" in new Fixtures {

    Mockito.when(selector.select(ArgumentMatchers.eq(Set(1, 2)))).thenReturn(1)
    Mockito.when(selector.select[Weapon](ArgumentMatchers.eq(Map(1 -> weapon1))))
      .thenReturn(weapon1)

    val (p1, p2) = game.readPlayersMove(
      Set(weapon1)
    )

    p1 should be (Player("Player 1", weapon1))
    p2 should be (Player("CPU", weapon1))

    Mockito.verify(selector, Mockito.times(1))
      .select(ArgumentMatchers.eq(Set(1, 2)))
    Mockito.verify(selector, Mockito.times(1))
      .select[Weapon](ArgumentMatchers.eq(Map(1 -> weapon1)))

  }

}

object RockPaperScissorsCliSpec {
  val weapon1 = Weapon(1)
  val weapon2 = Weapon(2)

  val weapons = Set(weapon1, weapon2)
  val weaponsMap = Map(
    1 -> weapon1,
    2 -> weapon2
  )
}