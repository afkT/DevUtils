package afkt.project.model.helper

import dev.utils.DevFinal
import dev.utils.common.ChineseUtils
import dev.utils.common.RandomUtils

/**
 * detail: 随机 Helper 类
 * @author Ttt
 */
object RandomHelper {

    // ==========
    // = 随机文本 =
    // ==========

    /**
     * 随机生成汉字
     * @param number 字数
     * @return 随机汉字
     */
    fun randomWord(number: Int): String {
        return ChineseUtils.randomWord(number)
    }

    /**
     * 随机生成汉字【在指定范围内】
     * @param min 最小随机数
     * @param max 最大随机数
     * @return 随机汉字
     */
    fun randomWordRange(
        min: Int = 0,
        max: Int
    ): String {
        return randomWord(RandomUtils.getRandom(min, max))
    }

    /**
     * 随机生成汉字 + 字母组合文本
     * @param workNumber 汉字数量
     * @param lettersNumber 字母数量
     * @return 随机汉字 + 字母组合
     */
    fun randomText(
        workNumber: Int,
        lettersNumber: Int
    ): String {
        val text = randomWordRange(max = workNumber) + RandomUtils.getRandomLetters(
            RandomUtils.getRandom(lettersNumber)
        )
        return RandomUtils.getRandom(text.toCharArray(), text.length)
    }

    // =========
    // = Image =
    // =========

    /**
     * 随机生成像素 200-210 图片 Url【正方形】
     * @return 图片 Url
     */
    fun randomImage200X(): String {
        return "https://picsum.photos/20${RandomUtils.getRandom(0, 10)}"
    }

    /**
     * 随机生成像素 400 图片 Url【正方形】
     * @return 图片 Url
     */
    fun randomImage400(typeId: Int): String {
        return String.format("https://picsum.photos/id/%s/500", typeId)
    }

    /**
     * 随机生成像素 500 图片 Url【正方形】
     * @return 图片 Url
     */
    fun randomImage500(): String {
        return String.format(
            "https://picsum.photos/id/%s/500",
            RandomUtils.getRandom(1, 50)
        )
    }

    /**
     * 随机生成像素 1080x1920 图片 Url
     * @return 图片 Url
     */
    fun randomImage1080x1920(typeMax: Int = 50): String {
        return String.format(
            "https://picsum.photos/id/%s/1080/1920",
            RandomUtils.getRandom(1, typeMax)
        )
    }

    /**
     * 随机生成像素 800x400 图片 Url
     * @return 图片 Url
     */
    fun randomImage800x400(): String {
        return String.format(
            "https://picsum.photos/id/%s/800/400",
            RandomUtils.getRandom(10, 20)
        )
    }

    // =======
    // = 价格 =
    // =======

    /**
     * 随机生成价格
     */
    fun randomPrice(): Double {
        return RandomUtils.nextDoubleRange(15.1, 79.3)
    }

    // =======
    // = 时间 =
    // =======

    /**
     * 随机生成当前时间差
     */
    fun randomTimeDiff(): Long {
        return System.currentTimeMillis() - RandomUtils.nextLongRange(
            DevFinal.TIME.MINUTE_MS,
            DevFinal.TIME.DAY_MS
        )
    }

    // =======
    // = 其他 =
    // =======

    /**
     * 随机生成 Float
     * @param min 最小随机数
     * @param max 最大随机数
     */
    fun randomFloat(
        min: Int = 0,
        max: Int
    ): Float {
        return RandomUtils.getRandom(min, max).toFloat()
    }
}