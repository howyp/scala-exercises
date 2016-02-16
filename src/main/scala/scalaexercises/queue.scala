package scalaexercises

/*
 An immutable, FIFO (first-in, first-out) Queue with O(1) complexity for all operations (amortised for tail)
 */
sealed trait Queue[T] {
  // O(1)
  def isEmpty: Boolean

  // O(1)
  def insert(t: T): Queue[T]

  // O(1)
  def head: Option[T]

  // O(1) amortised
  def tail: Queue[T]
}

object Queue {
  // It would be preferable to implement Empty as a case object to allow pattern matching,
  // but then T would need to be defined as +T
  class Empty[T] extends Queue[T] {
    val isEmpty = true
    def insert(t: T) = NonEmpty[T](List(t), List.empty)
    def head = None
    def tail = throw new RuntimeException("tail of an empty queue")
  }
  case class NonEmpty[T](front: List[T], back: List[T]) extends Queue[T] {
    val isEmpty = false
    def insert(t: T) = ???
    def head = front.headOption
    def tail = new Empty[T]
  }
  def empty[T]: Queue[T] = new Empty[T]
}