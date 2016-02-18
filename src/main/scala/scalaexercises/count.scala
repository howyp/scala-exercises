package scalaexercises

object CountCharacters {
  /*
    We want to produce a function that counts the number of chars in an English language spelling of a number.
    The top-level function countCharsInWords is provided for you.

    Part 1) Implement the toWords function

    Returns i as spelled in english (without commas, "and"s etc)
    Assume US notation, ie billion = 10^9
    eg.
     toWords(9) = "nine"
     toWords(99) = "ninety nine"
     toWords(999) = "nine hundred ninety nine"
  */
  def toWords(i: Int): String = {
    type Matcher = PartialFunction[Int, String]

    val zero: Matcher = {
      case 0 => "zero"
    }

    val singleDigit: Matcher = {
      case 1 => "one"
      case 2 => "two"
      case 3 => "three"
      case 4 => "four"
      case 5 => "five"
      case 6 => "six"
      case 7 => "seven"
      case 8 => "eight"
      case 9 => "nine"
    }

    val doubleDigitsAndBelow = {
      val tens: Matcher = {
        case 10                      => "ten"
        case 11                      => "eleven"
        case 12                      => "twelve"
        case 13                      => "thirteen"
        case 15                      => "fifteen"
        case 18                      => "eighteen"
        case n @ (14 | 16 | 17 | 19) => singleDigit(n - 10) + "teen"
      }
      def m(decade: Int, name: String): Matcher = {
        case n if n >= decade && n <= decade + 9 =>
          name + singleDigit.lift(n % 10).map(" " + _).getOrElse("")
      }
      singleDigit orElse
      tens orElse
        m(20, "twenty" ) orElse
        m(30, "thirty" ) orElse
        m(40, "forty"  ) orElse
        m(50, "fifty"  ) orElse
        m(60, "sixty"  ) orElse
        m(70, "seventy") orElse
        m(80, "eighty" ) orElse
        m(90, "ninety" )
    }

    def prependSpaceOnMatch(m: Matcher)(arg: Int) = m.lift(arg).map(" " + _).getOrElse("")

    val hundreds: Matcher = {
      case n if n > 99 && n <= 999 =>
        singleDigit(n / 100) + " hundred" + prependSpaceOnMatch(doubleDigitsAndBelow)(n % 100)
    }
    val tripleDigitsAndBelow = hundreds orElse doubleDigitsAndBelow

    val thousands: Matcher = {
      case n if n > 999 && n <= 999999 =>
        tripleDigitsAndBelow(n / 1000) + " thousand" + prependSpaceOnMatch(tripleDigitsAndBelow)(n % 1000)
    }
    val sextupleDigitsAndBelow = thousands orElse tripleDigitsAndBelow

    val millions: Matcher = {
      case n if n > 999999 && n <= 999999999 =>
        tripleDigitsAndBelow(n / 1000000) + " million" + prependSpaceOnMatch(sextupleDigitsAndBelow)(n % 1000000)
    }
    val nonupletDigitsAndBelow = millions orElse sextupleDigitsAndBelow

    val billions: Matcher = {
      case n if n > 999999999 && n <= Int.MaxValue =>
        tripleDigitsAndBelow(n / 1000000000) + " billion" + prependSpaceOnMatch(nonupletDigitsAndBelow)(n % 1000000000)
    }

    def couldNotHandle(n: Int) = throw new IllegalArgumentException(s"Could not process $n")

    zero orElse billions orElse millions orElse thousands orElse tripleDigitsAndBelow applyOrElse (i, couldNotHandle)
  }

  def countCharsInWords(i: Int): Int = toWords(i).filter(_ != ' ').length

  /*
    Part 2) Implement the countCharsInWordsOptimised function

    This should be a more efficient implementation of countCharsInWords.
    This does not need to re-use the above and may be an entirely different algorithm.
    Try to make this implementation as efficient as you can
  */
  def countCharsInWordsOptimised(i: Int): Int = ???
}