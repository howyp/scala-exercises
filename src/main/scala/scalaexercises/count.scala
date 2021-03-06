package scalaexercises

object CountCharacters {
  /*
    Returns i as spelled in english (without commas, "and"s etc)
    Uses US notation, ie billion = 10^9
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

    def prependSpaceOnMatch(m: Matcher)(arg: Int) = m.lift(arg).map(" " + _).getOrElse("")

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
          name + prependSpaceOnMatch(singleDigit)(n % 10)
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

  /*
    Counts the number of chars in an English language spelling of a number.
   */
  def countCharsInWords(i: Int): Int = toWords(i).filter(_ != ' ').length

  /*
    A more efficient implementation of countCharsInWords.
  */
  def countCharsInWordsOptimised(i: Int): Int = {
    type Matcher = PartialFunction[Int, Int]

    val zero: Matcher = {
      case 0 => 4
    }

    val singleDigit: Matcher = {
      case 1 => 3
      case 2 => 3
      case 3 => 5
      case 4 => 4
      case 5 => 4
      case 6 => 3
      case 7 => 5
      case 8 => 5
      case 9 => 4
    }

    val doubleDigitsAndBelow = {
      val tens: Matcher = {
        case 10                      => 3
        case 11                      => 6
        case 12                      => 6
        case 13                      => 8
        case 15                      => 7
        case 18                      => 8
        case n @ (14 | 16 | 17 | 19) => singleDigit(n - 10) + 4
      }
      def m(decade: Int, name: Int): Matcher = {
        case n if n >= decade && n <= decade + 9 =>
          name + singleDigit.applyOrElse(n % 10, (_: Int) => 0)
      }
      singleDigit orElse
        tens orElse
        m(20, 6) orElse
        m(30, 6) orElse
        m(40, 5) orElse
        m(50, 5) orElse
        m(60, 5) orElse
        m(70, 7) orElse
        m(80, 6) orElse
        m(90, 6)
    }


    val hundreds: Matcher = {
      case n if n > 99 && n <= 999 =>
        singleDigit(n / 100) + 7 + doubleDigitsAndBelow.applyOrElse(n % 100, (_: Int) => 0)
    }
    val tripleDigitsAndBelow = hundreds orElse doubleDigitsAndBelow

    val thousands: Matcher = {
      case n if n > 999 && n <= 999999 =>
        tripleDigitsAndBelow(n / 1000) + 8 + tripleDigitsAndBelow.applyOrElse(n % 1000, (_: Int) => 0)
    }
    val sextupleDigitsAndBelow = thousands orElse tripleDigitsAndBelow

    val millions: Matcher = {
      case n if n > 999999 && n <= 999999999 =>
        tripleDigitsAndBelow(n / 1000000) + 7 + sextupleDigitsAndBelow.applyOrElse(n % 1000000, (_: Int) => 0)
    }
    val nonupletDigitsAndBelow = millions orElse sextupleDigitsAndBelow

    val billions: Matcher = {
      case n if n > 999999999 && n <= Int.MaxValue =>
        tripleDigitsAndBelow(n / 1000000000) + 7 + nonupletDigitsAndBelow.applyOrElse(n % 1000000000, (_: Int) => 0)
    }

    def couldNotHandle(n: Int) = throw new IllegalArgumentException(s"Could not process $n")

    zero orElse billions orElse millions orElse thousands orElse tripleDigitsAndBelow applyOrElse (i, couldNotHandle)
  }
}