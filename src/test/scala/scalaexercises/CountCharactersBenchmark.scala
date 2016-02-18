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
      using(Gen.range("number")(0, 99999, 67)) in { toWords }
    }

  }
}