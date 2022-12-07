class Node(val name: String, var size: Int = 0, val parent: Node? = null) {
  val children: MutableList<Node> = mutableListOf()
}

fun cd(rootNode: Node, currentNode: Node, arg: String): Node {
  val toAncestor = ".."
  val toRoot = "/"

  return when (arg) {
    toRoot -> rootNode
    toAncestor -> currentNode.parent ?: rootNode
    else -> currentNode.children.find { it.name == arg } ?: throw IllegalStateException("Directory $arg does not exist in ${currentNode.name}")
  }
}

fun constructTree(input: List<String>): Node {
  var currentNode = Node("/", 0)
  val rootNode = currentNode

  for (line in input) {
    if (line.startsWith("$")) {
      if (line.contains("cd")) {
        val (_, _, operand) = line.split(" ")
        currentNode = cd(rootNode, currentNode, operand)
      }
    } else {
      val (dirOrSize, name) = line.split(" ")

      if (dirOrSize == "dir") {
        currentNode.children.add(Node(name, 0, currentNode))
      } else {
        val fileSize = dirOrSize.toInt()
        currentNode.children.add(Node(name, fileSize, currentNode))
      }
    }
  }

  return rootNode
}

// DFS
fun computeDirSize(node: Node): Int {
  if (node.children.size == 0) return node.size

  var dirSize = 0

  for (child in node.children) {
    dirSize += computeDirSize(child)
  }

  node.size += dirSize
  return node.size
}

fun calculateTotal(node: Node): Int {
  if (node.children.size == 0) return 0

  val threshold = 100000
  var total = 0

  for (child in node.children) {
    total += calculateTotal(child)
  }

  if (node.size <= threshold) {
    total += node.size
  }

  return total
}

fun findSufficientDir(node: Node, toDelete: Int): Int {
  if (node.children.size == 0) return 0

  var bestDir = node.size

  for (child in node.children) {
    val size = findSufficientDir(child, toDelete)
    if (size in toDelete..bestDir) {
      bestDir = size
    }
  }

  return bestDir
}

fun main() {
  fun part1(input: List<String>): Int {
    val rootNode = constructTree(input)
    computeDirSize(rootNode)
    return calculateTotal(rootNode)
  }

  fun part2(input: List<String>): Int {
    val rootNode = constructTree(input)
    computeDirSize(rootNode)
    return findSufficientDir(rootNode, rootNode.size - 40000000)
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

  val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
