package scalaexercises

import org.scalatest._

class QueueSpec extends FreeSpec with Matchers {
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
  }
}
