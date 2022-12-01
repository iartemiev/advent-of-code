fun main() {
  fun reduceInput(input: List<String>): List<Int> {
    val calTotals: MutableList<Int> = mutableListOf(0)

    for (line in input) {
      if (line.isEmpty()) {
        calTotals.add(0)
      } else {
        val cal = Integer.parseInt(line)
        calTotals[calTotals.size - 1] += cal
      }
    }

    calTotals.sortDescending()
    return calTotals
  }

  fun part1(input: List<String>): Int {
    return reduceInput(input)[0]
  }

  fun part2(input: List<String>): Int {
    return reduceInput(input).take(3).sum()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day01_test")
  check(part1(testInput) == 24000)

  val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
