import kotlin.math.abs

data class Point(val x: Int, val y: Int) {
  operator fun minus(point2: Point): Point {
    return Point(abs(x - point2.x), abs(y - point2.y))
  }
}

class Bridge(private val tailLength: Int = 1) {
  private val knotPositions: MutableList<Point> = MutableList(tailLength + 1) { Point(0, 0) }
  private val tailPointsVisited = mutableSetOf(Point(0, 0))

  fun move(direction: Char, steps: Int) {
    for (i in 1..steps) {
      moveHead(direction)

      for (j in 1.. tailLength) {
        moveTail(j)
      }
    }
  }

  fun visitedSize(): Int {
    return tailPointsVisited.size
  }

  private fun moveHead(direction: Char) {
    val headPos = knotPositions[0]
    knotPositions[0] = when (direction) {
      'U' -> Point(headPos.x, headPos.y + 1)
      'D' -> Point(headPos.x, headPos.y - 1)
      'L' -> Point(headPos.x - 1, headPos.y)
      'R' -> Point(headPos.x + 1, headPos.y)
      else -> throw IllegalArgumentException("Invalid direction $direction")
    }
  }

  private fun moveTail(pos: Int) {
    val currentKnot = knotPositions[pos]
    val prevKnot = knotPositions[pos - 1]
    val (diffX, diffY) = prevKnot - currentKnot

    if (diffX < 2 && diffY < 2) return

    // stole the compareTo use from Cathrin
    knotPositions[pos] = Point(
      currentKnot.x + prevKnot.x.compareTo(currentKnot.x),
      currentKnot.y + prevKnot.y.compareTo(currentKnot.y)
    )

    if (pos == tailLength) {
      tailPointsVisited.add(knotPositions[pos])
    }
  }
}

fun ropeBridge(bridge: Bridge, input: List<String>) {
  for (line in input) {
    val (direction, steps) = line.split(" ").map { if (it.matches("\\d+".toRegex())) it.toInt() else it.single() }
    bridge.move(direction as Char, steps as Int)
  }
}

fun main() {
  fun part1(input: List<String>): Int {
    val bridge = Bridge()

    ropeBridge(bridge, input)

    return bridge.visitedSize()
  }

  fun part2(input: List<String>): Int {
    val bridge = Bridge(9)

    ropeBridge(bridge, input)

    return bridge.visitedSize()
  }

  val testInput = readInput("Day09_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 1)

  val testInput2 = readInput("Day09_test2")
    check(part2(testInput2) == 36)

  val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
