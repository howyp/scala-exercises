package scalaexercises

import org.scalacheck.Gen
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{Matchers, WordSpec}

import scalaexercises.CountCharacters._

class CountCharactersSpec extends WordSpec with Matchers with GeneratorDrivenPropertyChecks {
  "countCharsInWords" should {
    "convert 9 into 4"    in { countCharsInWords(9) should be (4)}
    "convert 99 into 10"  in { countCharsInWords(99) should be (10)}
    "convert 999 into 21" in { countCharsInWords(999) should be (21)}
  }
  "toWords" should {
    "provide a solution for all numbers between 0 and 10^9" in {
      forAll(Gen.chooseNum(0, Int.MaxValue)) { n => toWords(n) should not be empty }
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
      toWords(41) should be ("forty one")
      toWords(51) should be ("fifty one")
      toWords(61) should be ("sixty one")
      toWords(71) should be ("seventy one")
      toWords(81) should be ("eighty one")
      toWords(91) should be ("ninety one")
    }
    "convert hundreds to words" in {
      toWords(100) should be ("one hundred")
      toWords(101) should be ("one hundred one")
      toWords(131) should be ("one hundred thirty one")
      toWords(248) should be ("two hundred forty eight")
      toWords(523) should be ("five hundred twenty three")
    }
    "convert thousands to words" in {
      toWords(1000) should be ("one thousand")
      toWords(1001) should be ("one thousand one")
      toWords(1056) should be ("one thousand fifty six")
      toWords(1239) should be ("one thousand two hundred thirty nine")
      toWords(8205) should be ("eight thousand two hundred five")
    }
    "convert tens of thousands to words" in {
      toWords(10000) should be ("ten thousand")
      toWords(10001) should be ("ten thousand one")
      toWords(10021) should be ("ten thousand twenty one")
      toWords(10321) should be ("ten thousand three hundred twenty one")
      toWords(15678) should be ("fifteen thousand six hundred seventy eight")
      toWords(23678) should be ("twenty three thousand six hundred seventy eight")
    }
    "convert hundreds of thousands to words" in {
      toWords(100000) should be ("one hundred thousand")
      toWords(100001) should be ("one hundred thousand one")
      toWords(394863) should be ("three hundred ninety four thousand eight hundred sixty three")
    }
    "convert millions to words" in {
      toWords(1000000) should be ("one million")
      toWords(1000001) should be ("one million one")
      toWords(1111111) should be ("one million one hundred eleven thousand one hundred eleven")
      toWords(23000001) should be ("twenty three million one")
      toWords(23981234) should be ("twenty three million nine hundred eighty one thousand two hundred thirty four")
      toWords(280805574) should be ("two hundred eighty million eight hundred five thousand five hundred seventy four")
    }
    "convert billions to words" in {
      toWords(1000000000) should be ("one billion")
      //2,147,483,647
      toWords(Int.MaxValue) should be ("two billion one hundred forty seven million four hundred eighty three thousand six hundred forty seven")
    }
  }
  "countCharsInWordsOptimised" should {
    "produce the same value as countCharsInWords" in {
      forAll(Gen.chooseNum(0, Int.MaxValue)) { n => countCharsInWordsOptimised(n) should be (countCharsInWords(n)) }
    }
  }
}
