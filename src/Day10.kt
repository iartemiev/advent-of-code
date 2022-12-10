import kotlin.math.floor

enum class Instruction(val cycles: Int) {
  noop(1),
  addx(2),
}

fun drawCRT(): (cycle: Int, sprite: Int) -> Unit {
  val grid = MutableList(6) { MutableList(40) {"."} }

  return fun (cycle: Int, sprite: Int) {
    val row = (cycle - 1).floorDiv(40)
    val pixel = (cycle - 1) % 40

    if (pixel in (sprite-1)..(sprite+1)) {
      grid[row][pixel] = "#"
    }

    if (cycle == 240) {
      val p = grid.joinToString(separator = "\n" ) { it.joinToString("") }
      println(p + "\n")
    }
  }
}

fun runProgram(instructionList: MutableList<List<String>>, drawOutput: Boolean = false): Int {
  var cycle = 1
  var registerX = 1
  var signalSum = 0

  var currentLine = instructionList.removeFirst()
  var currentInstruction = Instruction.valueOf(currentLine[0])
  var currentCycles = currentInstruction.cycles

  val draw = drawCRT()

  while(true) {
    if (drawOutput) {
      draw(cycle, registerX)
    }

    cycle++
    currentCycles--

    if (currentCycles == 0) {
      if (currentInstruction == Instruction.addx) {
        registerX += currentLine[1].toInt()
      }

      if (instructionList.size == 0) break

      currentLine = instructionList.removeFirst()
      currentInstruction = Instruction.valueOf(currentLine[0])
      currentCycles = currentInstruction.cycles
    }

    if ((cycle == 20) || ((cycle - 20) % 40 == 0)) {
      val signal = cycle * registerX
      signalSum += signal
    }
  }

  return signalSum
}

fun main() {
  fun part1(input: List<String>): Int {
    val instructionList = input.map { it.split(" ") } as MutableList<List<String>>
    return runProgram(instructionList)
  }

  fun part2(input: List<String>): Int {
    val instructionList = input.map { it.split(" ") } as MutableList<List<String>>
    return runProgram(instructionList, true)
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    part2(testInput)
//    check(part2(testInput) == 1)


  val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
