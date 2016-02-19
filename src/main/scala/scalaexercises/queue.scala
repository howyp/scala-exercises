package scalaexercises

/*
 An immutable, FIFO (first-in, first-out) Queue with O(1) complexity for all operations (amortised for tail)
 */
sealed trait Queue[T] {
  def isEmpty: Boolean
  def insert(t: T): Queue[T]
  def head: Option[T]
  def tail: Queue[T]
}

object Queue {
  // It would be preferable to implement Empty as a case object to allow pattern matching,
  // but then T would need to be defined as +T to allow Empty to extend Queue[Nothing]
  private class Empty[T] extends Queue[T] {
    val isEmpty = true
    def insert(t: T) = NonEmpty[T](front = List(t), back = List.empty)
    def head = None
    def tail = throw new RuntimeException("tail of an empty queue")
  }
  private case class NonEmpty[T](back: List[T], front: List[T]) extends Queue[T] {
    require(!front.isEmpty)
    val isEmpty = false
    def insert(t: T) = NonEmpty[T](back = t :: back, front = front)
    def head = Some(front.head)
    def tail = (back, front.tail) match {
      case (Nil,          Nil           ) => empty
      case (nonEmptyBack, Nil           ) => NonEmpty(back = List.empty,   front = back.reverse)
      case (nonEmptyBack, remainingFront) => NonEmpty(back = nonEmptyBack, front = remainingFront)
    }
  }
  def empty[T]: Queue[T] = new Empty[T]
}