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
    val zero: Matcher = {
      case 0 => "zero"
    }

    val doubleDigits = {
      val tens: Matcher = {
        case 10                      => "ten"
        case 11                      => "eleven"
        case 12                      => "twelve"
        case 13                      => "thirteen"
        case 15                      => "fifteen"
        case 18                      => "eighteen"
        case n @ (14 | 16 | 17 | 19) => singleDigit(n - 10) + "teen"
      }
      def doubleDigit(decade: Int, name: String): Matcher = {
        case `decade`              => name
        case n if n <= decade + 10 => name + " " + toWords(n - decade)
      }
      tens orElse
        doubleDigit(20, "twenty" ) orElse
        doubleDigit(30, "thirty" ) orElse
        doubleDigit(40, "fourty" ) orElse
        doubleDigit(50, "fifty"  ) orElse
        doubleDigit(60, "sixty"  ) orElse
        doubleDigit(70, "seventy") orElse
        doubleDigit(80, "eighty" ) orElse
        doubleDigit(90, "ninety" )
    }

    zero orElse singleDigit orElse doubleDigits apply i
  }

  // countCharsInWords(9) == 4
  // countCharsInWords(99) == 10
  // countCharsInWords(999) == 21
  def countCharsInWords(i: Int): Int = toWords(i).filter(_ != ' ').length

  /*
    Part 2) Implement the countCharsInWordsOptimised function

    This should be a more efficient implementation of countCharsInWords.
    This does not need to re-use the above and may be an entirely different algorithm.
    Try to make this implementation as efficient as you can
  */
  def countCharsInWordsOptimised(i: Int): Int = ???
}