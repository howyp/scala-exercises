package scalaexercises

import org.scalacheck.Gen
import org.scalatest._
import org.scalatest.prop.GeneratorDrivenPropertyChecks

class QueueSpec extends FreeSpec with Matchers with GeneratorDrivenPropertyChecks {
  "A Queue" - {
    val emptyQueue = Queue.empty[String]
    val firstValue = "example value"
    val singleItemQueue = emptyQueue.insert(firstValue)
    val secondValue = "another value"
    val twoItemQueue = singleItemQueue.insert(secondValue)
    "an empty queue should" - {
      "be empty" in                   { emptyQueue.isEmpty should be (true) }
      "have no head" in               { emptyQueue.head should be (None) }
      "throw an exception on tail" in { an [Exception] should be thrownBy emptyQueue.tail }
    }
    "after inserting an element, a queue should" - {
      "not be empty" in                        { singleItemQueue.isEmpty should be (false) }
      "have a head of the inserted element" in { singleItemQueue.head should contain (firstValue) }
      "have an empty tail" in                  { singleItemQueue.tail.isEmpty should be (true) }
    }
    "adding further elements to the list should" - {
      "stil be empty" in                           { twoItemQueue.isEmpty should be (false) }
      "still have the old head" in                 { twoItemQueue.head should contain (firstValue) }
      "put the new element in the tail" in         { twoItemQueue.tail.head should contain (secondValue) }
      "end up being empty if tail called twice" in { twoItemQueue.tail.tail.isEmpty should be (true) }
    }
    "elements should always be removed in the order they are inserted" in {
      trait Command
      case object Insert extends Command
      case object Remove extends Command

      case class State(queue: Queue[Int], nextToInsert: Int, nextToRemove: Int)

      forAll(Gen.listOf(Gen.oneOf(Insert, Remove))) { commandList: List[Command] =>
        commandList.foldLeft(State(queue = Queue.empty[Int], nextToInsert = 0, nextToRemove = 0)) {
          case (s, Insert) =>
            s.copy(queue = s.queue.insert(s.nextToInsert), nextToInsert = s.nextToInsert + 1)
          case (s, Remove) if s.nextToInsert == s.nextToRemove =>
            s.queue.head should be(None)
            s
          case (s, Remove) =>
            s.queue.head should contain (s.nextToRemove)
            s.copy(queue = s.queue.tail, nextToRemove = s.nextToRemove + 1)
        }
      }
    }
  }
}