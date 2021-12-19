import java.io.File
import kotlin.math.ceil
import kotlin.math.max

fun explode(node: Node, depth: Int = 0): Boolean {
    if (node.left == null) {
        return false
    }

    if (depth == 4) {
        val next = node.next()
        val prev = node.prev()
        next?.value = node.right!!.value + next?.value!!
        prev?.value = node.left!!.value + prev?.value!!
        val parent = node.parent!!
        if (parent.left === node) {
            parent.left = Node(0, parent = parent)
        } else {
            parent.right = Node(0, parent = parent)
        }
        return true
    }

    return explode(node.left!!, depth + 1) || explode(node.right!!, depth + 1)
}

fun split(node: Node): Boolean {
    if (node.value == -1) {
        return split(node.left!!) || split(node.right!!)
    }

    if (node.value < 10) {
        return false
    }

    val newNode = Node(-1, Node(node.value / 2), Node(ceil(node.value / 2.0).toInt()), node.parent)
    newNode.left!!.parent = newNode
    newNode.right!!.parent = newNode
    val parent = node.parent!!
    if (parent.left === node) {
        parent.left = newNode
    } else {
        parent.right = newNode
    }
    return true
}

fun reduce(node: Node): Boolean {
    return explode(node) || split(node)
}

data class Node(var value: Int = -1, var left: Node? = null, var right: Node? = null, var parent: Node? = null) {

    override fun toString(): String {
        if (value >= 0) {
            return value.toString()
        }

        return "[${left!!},${right!!}]"
    }

    operator fun plus(other: Node): Node {
        val node = Node(-1, this, other)
        this.parent = node
        other.parent = node
        @Suppress("ControlFlowWithEmptyBody")
        while(reduce(node));
        return node
    }

    fun magnitude(): Long {
        if (value >= 0) {
            return value.toLong()
        }

        return left!!.magnitude() * 3L + right!!.magnitude() * 2
    }

    fun prev(): Node? {
        var cur = this
        while(cur.parent != null && cur.parent!!.left === cur) {
            cur = cur.parent!!
        }

        if (cur.parent == null) {
            return null
        }

        cur = cur.parent!!.left!!
        while(cur.right != null) {
            cur = cur.right!!
        }

        return cur
    }

    fun next(): Node? {
        var cur = this
        while(cur.parent != null && cur.parent!!.right === cur) {
            cur = cur.parent!!
        }

        if (cur.parent == null) {
            return null
        }

        cur = cur.parent!!.right!!
        while(cur.left != null) {
            cur = cur.left!!
        }

        return cur
    }

    fun readExpression(exp: String, i: Int = 0, node: Node = this): Int {
        if (exp.length == i) {
            return i
        }

        when (exp[i]) {
            '[' -> {
                node.left = Node(parent = node)
                node.right = Node(parent = node)
                var nextToken = readExpression(exp, i + 1, node.left!!)
                nextToken = readExpression(exp, nextToken, node.right!!)
                return nextToken
            }
            ',', ']' -> return readExpression(exp, i + 1, node)
            else -> {
                var nextToken = i + 1
                while (exp[nextToken] != ']' && exp[nextToken] != ',') nextToken++
                val value = exp.substring(i, nextToken).toInt()
                node.value = value
                return nextToken
            }
        }
    }
}

fun read(exp: String): Node {
    val node = Node()
    node.readExpression(exp, 0)
    return node
}

fun main() {
    val input = File("src/main/resources/day18.in").readLines()
    val ans = input.map { read(it) }
        .reduce(Node::plus)
        .magnitude()
    println(ans)

    var maxMagnitude = 0L
    for (l1 in input) {
        for (l2 in input) {
            if (l2 === l1) {
                continue
            }
            val root1 = read(l1)
            val root2 = read(l2)
            maxMagnitude = max((root1 + root2).magnitude(), maxMagnitude)
        }
    }

    println(maxMagnitude)
}