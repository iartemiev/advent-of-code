const val UpperNormalize = 38
const val LowerNormalize = 96

fun main() {
  fun findCommonChar(input1: String, input2: String): Char {
    val charSet: Set<Char> = input1.toCharArray().toSet()
    return input2.find { charSet.contains(it) } ?: throw IllegalStateException("Invalid State")
  }

  fun findCommonChar(input1: String, input2: String, input3: String): Char {
    val charSet: Set<Char> = input1.toCharArray().toSet()
    val commonSet = input2.toCharArray().filter { charSet.contains(it) }.toSet()
    return input3.find { commonSet.contains(it) } ?: throw IllegalStateException("Invalid State")
  }

  fun charToPriority(char: Char): Int {
    val normalize: Int = if (char.isUpperCase()) UpperNormalize else LowerNormalize
    return char.code - normalize
  }

  fun part1(input: List<String>): Int {
    return input.sumOf { line ->
      val subStr1 = line.substring(0, line.length/2)
      val subStr2 = line.substring(line.length/2)
      val commonChar = findCommonChar(subStr1, subStr2)

      charToPriority(commonChar)
    }
  }

  fun part2(input: List<String>): Int {
    return input.chunked(3).sumOf { (subStr1, subStr2, subStr3) ->
      val commonChar = findCommonChar(subStr1, subStr2, subStr3)

      charToPriority(commonChar)
    }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day03_test")
  check(part1(testInput) == 157)
  check(part2(testInput) == 70)

  val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
