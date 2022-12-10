import java.lang.IllegalArgumentException

data class Point(val x: Int, val y: Int) {
  operator fun minus(point2: Point): Point {
    return Point(x - point2.x, y - point2.y)
  }
}

class Bridge(private val tailLength: Int = 1) {
  val knotPositions: MutableList<Point> = MutableList(tailLength + 1) { Point(0, 0) }
  val tailPointsVisited = mutableSetOf(Point(0, 0))

  fun move(direction: Char, steps: Int) {
    for (i in 1..steps) {
      moveHead(direction)

      for (j in 1.. tailLength) {
        moveTail(j)
      }
    }
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

    if (prevKnot == currentKnot) return
    val (diffX, diffY) = (prevKnot - currentKnot)

    if (diffX !in -1..1) {
      val newX = currentKnot.x + (diffX/2)
      var newY = currentKnot.y

      if (diffY != 0) {
        newY += diffY
      }

      knotPositions[pos] = Point(newX, newY)

    } else if (diffY !in -1..1) {
      val newY = currentKnot.y + (diffY/2)
      var newX = currentKnot.x

      if(diffX != 0) {
        newX += diffX
      }

      knotPositions[pos] = Point(newX, newY)
    }

    if (pos == tailLength) {
      tailPointsVisited.add(knotPositions[pos])
    }
  }
}

fun ropeBridge(bridge: Bridge, input: List<String>) {
  for (line in input) {
    val (direction, steps) = line.split(" ").map { if (it.matches("\\d+".toRegex())) it.toInt() else it.single() }
    printGrid(bridge)
    bridge.move(direction as Char, steps as Int)
  }
}

fun printGrid(bridge: Bridge) {
// 26 w x 21 h
//  S -> x=11; y= 5
  fun normalize(p: Point): Point {
    return Point(p.x + 11, p.y + 5)
  }

  val row = MutableList(26){ "." }
  val grid = MutableList(21) { row }

//  for ((idx, k) in bridge.knotPositions.withIndex().reversed()) {
//    val normalized = normalize(k)
//    val printVal = if (idx == 0) "H" else idx
//
//    grid[normalized.y][normalized.x] = printVal.toString()
//  }

  for (r in grid) {
    println(r)
  }

  println()
}

fun main() {
  fun part1(input: List<String>): Int {
    val bridge = Bridge()

    ropeBridge(bridge, input)

    return bridge.tailPointsVisited.size
  }

  fun part2(input: List<String>): Int {
    val bridge = Bridge(9)

    ropeBridge(bridge, input)

//    println("max: ${bridge.tailPointsVisited.maxBy { it.y }}")

    return bridge.tailPointsVisited.size
  }

  // test if implementation meets criteria from the description, like:
//  val testInput = readInput("Day09_test")
//    check(part1(testInput) == 13)
//    check(part2(testInput) == 1)

  //  2717
  val testInput2 = readInput("Day09_test2")
    check(part2(testInput2) == 36)

//  val input = readInput("Day09")
//    println(part1(input))
//    println(part2(input))
}
