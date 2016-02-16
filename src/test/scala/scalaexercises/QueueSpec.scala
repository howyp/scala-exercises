package scalaexercises

import org.scalatest._

class QueueSpec extends FreeSpec with Matchers {
  "A Queue" - {
    val emptyQueue = Queue.empty[String]
    "an empty queue should" - {
      "be empty" in { emptyQueue.isEmpty should be (true) }
      "have no head" in { emptyQueue.head should be (None) }
      "throw an exception on tail" in { an [Exception] should be thrownBy emptyQueue.tail }
    }
    "after inserting an element, a queue should" - {
      val singleItemQueue = emptyQueue.insert("example value")
      "not be empty" in { singleItemQueue.isEmpty should be (false) }
      "have a head of the inserted element" in { singleItemQueue.head should contain ("example value") }
      "have an empty tail" in { singleItemQueue.tail.isEmpty should be (true) }
    }
  }
}
