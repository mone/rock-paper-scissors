package rockpaperscissors.models

import WeaponCollections._
import rockpaperscissors.BaseSpec
import rockpaperscissors.models.Result._
import generators.WeaponGenerators._

import org.scalatest.prop.GeneratorDrivenPropertyChecks

class WeaponSpec extends BaseSpec with GeneratorDrivenPropertyChecks {

  // let's test the classic weapons
  behavior of "WeaponCollections"

  "rock" should "win against scissors and lose against paper" in {
    Rock fight Scissors should be (Win)
    Rock fight Paper should be (Lose)

  }

  "scissors" should "win against paper and lose against rock" in {
    Scissors fight Paper should be (Win)
    Scissors fight Rock should be (Lose)
  }

  "paper" should "win against rock and lose against scissors" in {
    Paper fight Rock should be (Win)
    Paper fight Scissors should be (Lose)
  }

  "rock paper and scissors" should "draw against themselves" in {
    Scissors fight Scissors should be (Draw)
    Rock fight Rock should be (Draw)
    Paper fight Paper should be (Draw)
  }


  // let's use generators to test the generic weapon
  behavior of "Weapon"

  "odd weapon" should "win against higher odd weapon (and viceversa)" in {
    forAll(oddWeaponGen()) { oddLowWeapon =>
      forAll(oddWeaponGen(higherWeaponGen(oddLowWeapon))) { oddHighWeapon =>
        oddLowWeapon fight oddHighWeapon should be (Win)
        oddHighWeapon fight oddLowWeapon should be (Lose)
      }
    }
  }

  "odd weapon" should "win against lower even weapon (and viceversa)" in {
    forAll(oddWeaponGen()) { oddHighWeapon =>
      forAll(evenWeaponGen(lowerWeaponGen(oddHighWeapon))) { evenLowWeapon =>
        oddHighWeapon fight evenLowWeapon should be (Win)
        evenLowWeapon fight oddHighWeapon should be (Lose)
      }
    }
  }

  "even weapon" should "win against higher even weapon (and viceversa)" in { // i.e. odd vs odd
    forAll(evenWeaponGen()) { evenLowWeapon =>
      forAll(evenWeaponGen(higherWeaponGen(evenLowWeapon))) { evenHighWeapon =>
        evenLowWeapon fight evenHighWeapon should be (Win)
        evenHighWeapon fight evenLowWeapon should be (Lose)
      }
    }
  }

  "even weapon" should "win against lower odd weapon (and viceversa)" in {
    forAll(evenWeaponGen()) { evenHighWeapon =>
      forAll(oddWeaponGen(lowerWeaponGen(evenHighWeapon))) { oddLowWeapon =>
        evenHighWeapon fight oddLowWeapon should be (Win)
        oddLowWeapon fight evenHighWeapon should be (Lose)
      }
    }
  }

  "any weapon" should "draw against itself" in {
    forAll(weaponGen()) { weapon =>
      weapon fight weapon should be (Draw)
    }
  }

}
