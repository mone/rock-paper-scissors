package generators

import org.scalacheck.Gen
import rockpaperscissors.models.Weapon

/**
  * Generators to test random weapons
  */
object WeaponGenerators {

  /**
    * Generates random weapons
    */
  def weaponGen(low: Int = -100, high: Int = 100): Gen[Weapon]  = for {
    weaponNum <- Gen.chooseNum(low, high)
  } yield Weapon(weaponNum)

  /**
    * Generates random weapons that are higher than the specified one
    */
  def higherWeaponGen(low: Weapon): Gen[Weapon] = weaponGen(low.value + 1, low.value + 100)
  /**
    * Generates random weapons that are lower than the specified one
    */
  def lowerWeaponGen(high: Weapon): Gen[Weapon] = weaponGen(high.value - 100, high.value - 1)

  /**
    * Generates random even weapons
    */
  def evenWeaponGen(coreGen: Gen[Weapon] = weaponGen()): Gen[Weapon] =
    coreGen suchThat (n => Math.abs(n.value % 2) == 0)
  /**
    * Generates random odd weapons
    */
  def oddWeaponGen(coreGen: Gen[Weapon] = weaponGen()): Gen[Weapon] =
    coreGen suchThat (n => Math.abs(n.value % 2) == 1)

}