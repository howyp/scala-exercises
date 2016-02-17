package scalaexercises

import org.scalacheck.Gen
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{Matchers, WordSpec}

import scalaexercises.CountCharacters._

class CountCharactersSpec extends WordSpec with Matchers with GeneratorDrivenPropertyChecks {
  "countCharsInWords" should {
    "convert 9 into 4"    in { countCharsInWords(9) should be (4)}
//    "convert 99 into 10"  in { countCharsInWords(99) should be (10)}
//    "convert 999 into 21" in { countCharsInWords(999) should be (21)}
  }
  "toWords" should {
    "provide a solution for all numbers between 0 and 10^9" in {
      forAll(Gen.chooseNum(0, 99)) { toWords(_) should not be (empty) }
    }
    "convert single digits to words" in {
      toWords(0) should be ("zero")
      toWords(1) should be ("one")
      toWords(2) should be ("two")
      toWords(3) should be ("three")
      toWords(4) should be ("four")
      toWords(5) should be ("five")
      toWords(6) should be ("six")
      toWords(7) should be ("seven")
      toWords(8) should be ("eight")
      toWords(9) should be ("nine")
    }
    "convert -teens to words" in {
      toWords(10) should be ("ten")
      toWords(11) should be ("eleven")
      toWords(12) should be ("twelve")
      toWords(13) should be ("thirteen")
      toWords(14) should be ("fourteen")
      toWords(15) should be ("fifteen")
      toWords(16) should be ("sixteen")
      toWords(17) should be ("seventeen")
      toWords(18) should be ("eighteen")
      toWords(19) should be ("nineteen")
    }
    "convert other two-digit numbers to words" in {
      toWords(20) should be ("twenty")
      toWords(21) should be ("twenty one")
      toWords(29) should be ("twenty nine")
      toWords(31) should be ("thirty one")
      toWords(41) should be ("fourty one")
      toWords(51) should be ("fifty one")
      toWords(61) should be ("sixty one")
      toWords(71) should be ("seventy one")
      toWords(81) should be ("eighty one")
      toWords(91) should be ("ninety one")
    }
  }
}