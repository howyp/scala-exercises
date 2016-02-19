package scalaexercises

import org.scalameter.api._
import org.scalameter.picklers.Implicits._

import scalaexercises.CountCharacters._

object CountCharactersBenchmark extends Bench.LocalTime {
  override lazy val executor = SeparateJvmsExecutor(
    w = new Executor.Warmer.Default,
    agg = Aggregator.min[Double],
    m = new Measurer.Default
  )
  override lazy val reporter = ChartReporter[Double](ChartFactory.XYLine())

  val numbers = Gen.enumeration("number")(
    9,
    99,
    999,
    9999,
    99999,
    999999,
    9999999,
    99999999,
    999999999,
    1999999999
  )

  performance of "CountCharacters" in {
    measure method "toWords" in { using(numbers) in { toWords } }
    measure method "countCharsInWords" in { using(numbers) in { countCharsInWords } }
    measure method "countCharsInWordsOptimised" in { using(numbers) in { countCharsInWordsOptimised } }
  }
}