package dev.simple.bindingadapters.view.attribute

/**
 * detail: 通用 X、Y、Index 类
 * @author Ttt
 */
open class XYI(
    val x: Int = 0,
    val y: Int = 0,
    val index: Int = -1
) {

    companion object {
        val ZERO get() = XYI()

        fun ofXY(
            x: Int,
            y: Int
        ): XYI {
            return XYI(x, y, -1)
        }

        fun ofIndex(index: Int): XYI {
            return XYI(0, 0, index)
        }
    }

    open fun clone(): XYI {
        return XYI(x, y, index)
    }

    // ============
    // = override =
    // ============

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as XYI
        if (x != other.x) return false
        if (y != other.y) return false
        if (index != other.index) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        result = 31 * result + index
        return result
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("XYI{")
        builder.append("x: ").append(x)
        builder.append(", y: ").append(y)
        builder.append(", index: ").append(index)
        builder.append("}")
        return builder.toString()
    }
}