package cn.bingoogolapple.dsl.kotlin

data class ListNode(val value: Int, var next: ListNode? = null)

data class TreeNode(val value: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

/**
 * 尾递归
 *
 * 递归的一种特殊形式
 * 调用自身后直接返回，无其他操作
 * tailrec 关键字提示编译器为递归优化（优化成迭代器的形式）
 *
 * 尾递归表达比较清晰，迭代执行效率比较高。递归开销比较大，可能会 Stack Overflow
 */
tailrec fun findListNode(head: ListNode?, value: Int): ListNode? {
    head ?: return null
    return if (head.value == value) {
        head
    } else {
        findListNode(head.next, value)
    }
}

tailrec fun findTreeNode(root: TreeNode?, value: Int): TreeNode? {
    root ?: return null
    if (root.value == value) {
        return root
    } else {
        return findTreeNode(root.left, value) ?: return findTreeNode(root.right, value)
    }
}

fun factorial(n: Long): Long {
    // 调用自身后乘以 n 后再返回的，非尾递归
    return n * factorial(n - 1)
}

fun main(args: Array<String>) {
    // 不做为递归优化时会 Stack Overflow
    val maxNodeCount = 100000
    val head = ListNode(0)
    var p = head
    for (i in 1..maxNodeCount) {
        p.next = ListNode(i)
        p = p.next!!
    }

    println(findListNode(head, maxNodeCount - 2)?.value)
}
