package scalaexercises

import org.scalameter.api._
import org.scalameter.picklers.Implicits._

import scalaexercises.CountCharacters.toWords

object CountCharactersBenchmark extends Bench.LocalTime {
  override lazy val executor = SeparateJvmsExecutor(
    w = new Executor.Warmer.Default,
    agg = Aggregator.min[Double],
    m = new Measurer.Default
  )
  override lazy val reporter = ChartReporter[Double](ChartFactory.XYLine())

  performance of "CountCharacters" in {
    measure method "toWords" in {
      using(Gen.enumeration("number")(
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
      )) in { toWords }
    }

  }
}