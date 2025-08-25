package dev.simple.mvvm.base.attribute

/**
 * detail: 通用 Padding 类
 * @author Ttt
 */
class Paddings(
    var left: Int = 0,
    var top: Int = 0,
    var right: Int = 0,
    var bottom: Int = 0
) {

    constructor() : this(0, 0, 0, 0)

    constructor(value: Int) : this(value, value, value, value)

    constructor(
        leftRight: Int = 0,
        topBottom: Int = 0
    ) : this(leftRight, topBottom, leftRight, topBottom)

    // =

    companion object {
        val NO_PADDINGS get() = Paddings()
    }

    fun clone(): Paddings {
        return Paddings(left, top, right, bottom)
    }

    // ============
    // = override =
    // ============

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Paddings
        if (left != other.left) return false
        if (top != other.top) return false
        if (right != other.right) return false
        if (bottom != other.bottom) return false

        return true
    }

    override fun hashCode(): Int {
        var result = left
        result = 31 * result + top
        result = 31 * result + right
        result = 31 * result + bottom
        return result
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("Paddings{")
        builder.append("left: ").append(left)
        builder.append(", top: ").append(top)
        builder.append(", right: ").append(right)
        builder.append(", bottom: ").append(bottom)
        builder.append("}")
        return builder.toString()
    }
}