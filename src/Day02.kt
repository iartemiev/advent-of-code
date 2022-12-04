import java.lang.IllegalArgumentException

// Super verbose solution - wanted to play with enums with anonymous classes

enum class PlayerMove(val move: RockPaperScissors) {
  X(RockPaperScissors.ROCK), Y(RockPaperScissors.PAPER), Z(RockPaperScissors.SCISSORS)
}

enum class OpponentMove(val move: RockPaperScissors) {
  A(RockPaperScissors.ROCK), B(RockPaperScissors.PAPER), C(RockPaperScissors.SCISSORS)
}

enum class RoundOutcome(val outcomePoints: Int) {
  X(0), Y(3), Z(6)
}

enum class RockPaperScissors(val movePoints: Int) {
  ROCK(1) {
    override fun play(playerMove: RockPaperScissors): Int = when (playerMove) {
      ROCK -> ROCK.movePoints + 3
      PAPER -> PAPER.movePoints + 6
      SCISSORS -> SCISSORS.movePoints + 0
      else -> throw IllegalArgumentException("Invalid move: $playerMove")
    }

    override fun strategy(outcome: RoundOutcome): Int = when (outcome) {
      RoundOutcome.X -> outcome.outcomePoints + SCISSORS.movePoints
      RoundOutcome.Y -> outcome.outcomePoints + ROCK.movePoints
      RoundOutcome.Z -> outcome.outcomePoints + PAPER.movePoints
      else -> throw IllegalArgumentException("Invalid outcome: $outcome")
    }
  },

  PAPER(2) {
    override fun play(playerMove: RockPaperScissors): Int = when (playerMove) {
      ROCK -> ROCK.movePoints + 0
      PAPER -> PAPER.movePoints + 3
      SCISSORS -> SCISSORS.movePoints + 6
      else -> throw IllegalArgumentException("Invalid move: $playerMove")
    }

    override fun strategy(outcome: RoundOutcome): Int = when (outcome) {
      RoundOutcome.X -> outcome.outcomePoints + ROCK.movePoints
      RoundOutcome.Y -> outcome.outcomePoints + PAPER.movePoints
      RoundOutcome.Z -> outcome.outcomePoints + SCISSORS.movePoints
      else -> throw IllegalArgumentException("Invalid outcome: $outcome")
    }
  },

  SCISSORS(3) {
    override fun play(playerMove: RockPaperScissors): Int = when (playerMove) {
      ROCK -> ROCK.movePoints + 6
      PAPER -> PAPER.movePoints + 0
      SCISSORS -> SCISSORS.movePoints + 3
      else -> throw IllegalArgumentException("Invalid move: $playerMove")
    }

    override fun strategy(outcome: RoundOutcome): Int = when (outcome) {
      RoundOutcome.X -> outcome.outcomePoints + PAPER.movePoints
      RoundOutcome.Y -> outcome.outcomePoints + SCISSORS.movePoints
      RoundOutcome.Z -> outcome.outcomePoints + ROCK.movePoints
      else -> throw IllegalArgumentException("Invalid outcome: $outcome")
    }
  };

  abstract fun play(playerMove: RockPaperScissors): Int
  abstract fun strategy(outcome: RoundOutcome): Int
}

fun round(opponent: String, player: String): Int {
  val opponentMove = OpponentMove.valueOf(opponent).move
  val playerMove = PlayerMove.valueOf(player).move

  return opponentMove.play(playerMove)
}

fun strategyRound(opponent: String, outcome: String): Int {
  val opponentMove = OpponentMove.valueOf(opponent).move
  val expectedOutcome = RoundOutcome.valueOf(outcome)

  return opponentMove.strategy(expectedOutcome)
}

fun main() {
  fun part1(input: List<String>): Int {
    return input.sumOf {
      val (opponent, player) = it.split(" ")
      round(opponent, player)
    }
  }

  fun part2(input: List<String>): Int {
    return input.sumOf {
      val (opponent, outcome) = it.split(" ")
      strategyRound(opponent, outcome)
    }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day02_test")
  check(part1(testInput) == 15)
  check(part2(testInput) == 12)

  val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
