scala-exercises
---------------

Scala examples of:

* An algorithm converting positive integer numbers into English words
* An immutable (FIFO) queue structure with at most amortised O(1) complexity for all operations


### CountCharacters

`toWords` contains an algorithm converting numbers between 0 and ~2 billion (specifically `Int.MaxValue`) into English words. See `CountCharactersSpec` for examples of the conversions.

`countCharsInWords` uses a simplistic method to convert the output of `toWords` into a count of the number of characters in a word (ignoring spaces). A more optimised version of this is contained in `countCharsInWordsOptimised`, which uses a similar algorithm to `toWords`, but sums pre-computed counts of characters, rather than creating the strings and counting the characters.

`CountCharactersBenchmark` roughly measures the relative performance of the two versions of character counting, showing the optimised version to take around a third of the time.

### Queue

A first-in, first-out queue. The basis of this is two lists `front` and `back`, which each hold part of the queue. See `QueueSpec` for examples of its usage. This specification also checks that the first-in, first-out property is obeyed by inserting and removing items in a random order on the queue.

`QueueBenchmark` aims to measure the performance of each operation, showing the O(1) nature of all operations. The amortised O(1) complexity of `tail` is roughly demonstrated by showing the behaviour for both a queue which has only had inserts, and one that has already been tailed once. For a never-tailed queue, compute time goes up proportionally to the number of items in the queue. For a once-tailed queue, compute time is uniform regardless of the size.

### Running 
 
 To execute the tests, run:
 
 ```
 sbt clean test
 ```
 
 Test results are shown in the console output. Graphs of the benchmarks are available in `target/benchmarks` after running. A sample of the benchmark results from a 2.8 GHz Intel Core i5 MacBook Pro are in `graphs`.