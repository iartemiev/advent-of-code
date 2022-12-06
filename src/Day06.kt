fun findUnique(line: String, sequenceLength: Int): Int {
  var startIdx = 0
  var found = false

  while (!found) {
    val countMap = line.subSequence(startIdx, startIdx + sequenceLength).groupingBy { it }.eachCount()
    if (countMap.values.size == sequenceLength) {
      found = true
    } else {
      startIdx++
    }
  }

  return startIdx + sequenceLength
}

fun main() {
  fun part1(input: List<String>): Int {
    val (line) = input
    return findUnique(line, 4)
  }

  fun part2(input: List<String>): Int {
    val (line) = input
    return findUnique(line, 14)
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day06_test")
  check(part1(testInput) == 11)
  check(part2(testInput) == 26)

  val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
