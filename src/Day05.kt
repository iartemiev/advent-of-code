import java.util.*
fun makeMoves(moves: List<List<Int>>, stacks: List<MutableList<Char>>): List<MutableList<Char>> {
  for (move in moves) {
    val (quantity, source, destination) = move

    for (i in 1..quantity) {
      val crate = stacks[source-1].last()
      stacks[source-1].removeLast()
      stacks[destination-1].add(crate)
    }
  }
  return stacks
}

fun makeMoves2(moves: List<List<Int>>, stacks: List<MutableList<Char>>): List<MutableList<Char>> {
  for (move in moves) {
    val (quantity, source, destination) = move
    val crates = stacks[source-1].takeLast(quantity)
    stacks[destination-1].addAll(crates)

    for (i in 1..quantity) {
      stacks[source - 1].removeLast()
    }
  }
  return stacks
}
fun parseInput(input: List<String>): Pair<MutableList<List<Int>>, List<MutableList<Char>>> {
  val moves: MutableList<List<Int>> = mutableListOf()
  val rows: MutableList<List<Char>> = mutableListOf()

  fun parseLine(line: String): List<Char> {
    var idx = 1
    val offset = 4
    val list: MutableList<Char> = mutableListOf()

    while (idx < line.length) {
      val char = if (line[idx] != ' ') line[idx] else '0'
      list.add(char)
      idx += offset
    }

    return list
  }

  fun transposeRows(rows: List<List<Char>>): List<MutableList<Char>> {
    val listSize = rows.last().size
    val matrix: List<MutableList<Char>> = List(listSize){mutableListOf()}

    for (row in rows) {
      row.forEachIndexed{index, char ->
        if (char != '0') {
          matrix[index].add(0, char)
        }
      }
    }

    return matrix
  }

  for (line in input) {
    if (line.startsWith("move")) {
        val move = line.split(" ").filter{ it.toDoubleOrNull() != null }.map{ it.toInt() }
        moves.add(move)
    } else if (line.contains('[')) {
      val row = parseLine(line)
      rows.add(row)
    }
  }
  val stacks = transposeRows((rows))

  return Pair(moves, stacks)
}

fun main() {
  fun part1(input: List<String>): String {
    val (moves, stacks) = parseInput(input);
    return makeMoves(moves, stacks).fold("") {acc, stack -> acc + stack.last() }
  }

  fun part2(input: List<String>): String {
    val (moves, stacks) = parseInput(input);
    return makeMoves2(moves, stacks).fold("") {acc, stack -> acc + stack.last() }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day05_test")
  check(part1(testInput) == "CMZ")
  check(part2(testInput) == "MCD")

  val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
