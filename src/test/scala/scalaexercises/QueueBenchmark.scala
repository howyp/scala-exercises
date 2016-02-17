package scalaexercises

import org.scalameter.api._
import org.scalameter.picklers.Implicits._

object QueueBenchmark extends Bench.LocalTime {
  val queues = for {
    initialSize <- Gen.range("initialSize")(10, 1000000, 100000)
  } yield {
    (0 to initialSize).foldLeft(Queue.empty[Int]) { (q, i) =>
      q.insert(i)
    }
  }

  def preTailed(q: Gen[Queue[Int]]): Gen[Queue[Int]] = q.map(_.tail)


  override lazy val executor = SeparateJvmsExecutor(
    w = new Executor.Warmer.Default,
    agg = Aggregator.min[Double],
    m = new Measurer.Default
  )
  override lazy val reporter = ChartReporter[Double](ChartFactory.XYLine())

  performance of "Queue" in {
    measure method "insert ten items into a queue" in {
      using(queues) in { q =>
        q.insert(-1)
          .insert(-2)
          .insert(-3)
          .insert(-4)
          .insert(-5)
          .insert(-6)
          .insert(-7)
          .insert(-8)
          .insert(-9)
          .insert(-10)
      }
    }
    measure method "take the tail of a queue ten times" in {
      using(queues) in { q =>
        q.tail
          .tail
          .tail
          .tail
          .tail
          .tail
          .tail
          .tail
          .tail
          .tail
      }
    }
    measure method "take the tail of a queue that has already been tailed once ten times" in {
      using(preTailed(queues)) in { q =>
        q.tail
          .tail
          .tail
          .tail
          .tail
          .tail
          .tail
          .tail
          .tail
          .tail
      }
    }
  }
}