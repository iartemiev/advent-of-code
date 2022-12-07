fun findUnique(line: String, sequenceLength: Int): Int {
  var startIdx = 0
  var found = false

  while (!found) {
    val countSet = line.subSequence(startIdx, startIdx + sequenceLength).toSet()
    if (countSet.size == sequenceLength) {
      found = true
    } else {
      startIdx++
    }
  }

  return startIdx + sequenceLength
}

//Cathrin's solution - saving for future ref
fun processWindowOfX(input: String, x: Int) : Int{
  return input.windowed(x,1) {
      window -> window.toSet().size == x
  }.indexOf(true) + x
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
