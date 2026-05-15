package dev.simple.interfaces

/**
 * detail: 比对值接口
 * @author Ttt
 */
interface CompareValue {

    /**
     * 获取实际比对值
     * @return 比对值，可为 null
     */
    fun compareValue(): String?

    /**
     * 判断与另一比对值是否一致
     * @param value 参与比对的 [CompareValue]
     * @return `true` 一致，`false` 不一致
     */
    fun equalsCompareValue(value: CompareValue): Boolean
}