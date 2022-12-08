fun generateTreePairSet(matrix: List<List<Int>>): Set<Pair<Int, Int>> {
  val minLeft = 1
  val minTop = 1
  val minRight = matrix[0].size - 2
  val minBottom = matrix.size - 2

  val treePairSet = mutableSetOf<Pair<Int, Int>>()

  // top left -> bottom right
  val minTopY = matrix[0].toCollection(mutableListOf())
  for (i in minTop..minBottom) {
    var minX = matrix[i][0]

    for (j in minLeft..minRight) {
      val treeHeight = matrix[i][j]

      if (minX < treeHeight) {
        treePairSet.add(Pair(i, j))
        minX = treeHeight
      }

      if (minTopY[j] < treeHeight) {
        treePairSet.add(Pair(i, j))
        minTopY[j] = treeHeight
      }
    }
  }

  // bottom right -> top left
  val minBottomY = matrix[matrix.size - 1].toCollection(mutableListOf())
  for (i in minBottom downTo minTop) {
    var minX = matrix[i][matrix[0].size - 1]

    for (j in minRight downTo minLeft) {
      val treeHeight = matrix[i][j]

      if (minX < treeHeight) {
        treePairSet.add(Pair(i, j))
        minX = treeHeight
      }

      if (minBottomY[j] < treeHeight) {
        treePairSet.add(Pair(i, j))
        minBottomY[j] = treeHeight
      }
    }
  }

  return treePairSet
}

fun scenicScoreForTree(matrix: List<List<Int>>, treeCoords: Pair<Int, Int>): Int {
  val (y, x) = treeCoords
  val treeHeight = matrix[y][x]

  var topDownTotal = 0
  for(i in y+1 until matrix.size) {
    topDownTotal++
    if (matrix[i][x] >= treeHeight) {
      break
    }
  }

  var leftRightTotal = 0
  for(i in x+1 until matrix.size) {
    leftRightTotal++
    if (matrix[y][i] >= treeHeight) {
      break
    }
  }

  var bottomUpTotal = 0
  for(i in y-1 downTo 0) {
    bottomUpTotal++
    if (matrix[i][x] >= treeHeight) {
      break
    }
  }

  var rightLeftTotal = 0
  for(i in x-1 downTo 0) {
    rightLeftTotal++
    if (matrix[y][i] >= treeHeight) {
      break
    }
  }

  return topDownTotal * leftRightTotal * bottomUpTotal * rightLeftTotal
}

fun main() {
  fun part1(input: List<String>): Int {
    val matrix: List<List<Int>> = input.map { it.map { inner -> inner.toString().toInt() } }
    val perimeterLength = 2 * (matrix.size - 1 + matrix[0].size - 1)
    val treePairSet = generateTreePairSet(matrix)

    return perimeterLength + treePairSet.size
  }

  fun part2(input: List<String>): Int {
    val matrix: List<List<Int>> = input.map { it.map { inner -> inner.toString().toInt() } }
    val treePairSet = generateTreePairSet(matrix)

    return treePairSet.maxOfOrNull { scenicScoreForTree(matrix, it) } ?: 0
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

  val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
