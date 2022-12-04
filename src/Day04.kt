class SectionRange(private val start: Int, private val end: Int) {
  fun partiallyOverlapsWith(range2: SectionRange): Boolean {
    return end >= range2.start && start <= range2.end
  }

  fun fullyOverlapsWith(range2: SectionRange): Boolean {
    return (start <= range2.start && end >= range2.end)
        || (range2.start <= start && range2.end >= end)
  }
}

fun rangePairFromLine(line: String): Pair<SectionRange, SectionRange> {
  val (range1, range2) = line.split(",").map{
    val (start, end) = it.split("-")
    SectionRange(start.toInt(), end.toInt())
  }
  return Pair(range1, range2)
}

fun main() {
  fun part1(input: List<String>): Int {
    return input.count {
      val (range1, range2) = rangePairFromLine(it)
      range1.fullyOverlapsWith(range2)
    }
  }

  fun part2(input: List<String>): Int {
    return input.count {
      val (range1, range2) = rangePairFromLine(it)
      range1.partiallyOverlapsWith(range2)
    }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day04_test")
  check(part1(testInput) == 2)
  check(part2(testInput) == 4)

  val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
