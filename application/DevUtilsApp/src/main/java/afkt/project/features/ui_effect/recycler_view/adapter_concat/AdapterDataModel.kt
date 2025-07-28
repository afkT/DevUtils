package afkt.project.features.ui_effect.recycler_view.adapter_concat

import afkt.project.model.helper.RandomHelper
import android.graphics.Bitmap
import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import dev.mvvm.utils.toPriceString
import dev.mvvm.utils.toRMBSubZeroAndDot
import dev.utils.app.ResourceUtils
import dev.utils.app.image.ImageUtils
import dev.utils.common.ChineseUtils
import dev.utils.common.ColorUtils
import dev.utils.common.RandomUtils
import dev.utils.common.StringUtils

// =====================
// = 模拟 - 后台返回实体类 =
// =====================

/**
 * detail: 首页信息实体类
 * @author Ttt
 */
class MainBean(
    val bannerLists: List<BannerBean>,
    val classifyLists: List<ClassifyBean>,
    val commodityLists: List<CommodityBean>,
    val shapeableImageLists: List<ShapeableImageBean>,
    val articleLists: List<ArticleBean1>
)

/**
 * detail: Banner 实体类
 * @author Ttt
 */
class BannerBean(
    val id: String,
    val imageUrl: String
)

/**
 * detail: 分类实体类
 * @author Ttt
 */
class ClassifyBean(
    // 分类 id
    val id: String,
    // 分类名
    val name: String,
    // 父类 id
    val rootId: String,
    // 背景颜色
    val background: Int,
    // 子类数据
    var subLists: List<ClassifyBean>? = null
)

/**
 * detail: 商品实体类
 * @author Ttt
 */
class CommodityBean(
    // 商品名
    val name: String,
    // 商品图片
    val picture: String,
    // 商品价格
    price: Double,
    // 商品类型 ( 是否评价商品 )
    val isEvaluateCommodity: Boolean = false,
    // 商品评价等级
    var evaluateLevel: Float = 0F,
    // 商品评价内容
    var evaluateContent: String = "",
    // 是否首个向上边距 ( MultiType 使用 )
    var isFirst: Boolean = false
) {
    val priceText = price.toPriceString()?.toRMBSubZeroAndDot()

    // 输入的内容
    val inputText = ObservableField<String>(evaluateContent)

    // 输入数量
    val inputNumberText = ObservableField<String>()

    // 评价等级
    val ratingValue = ObservableFloat(evaluateLevel)

    init {
        val inputNumber = StringUtils.length(evaluateContent)
        inputNumberText.set("${120 - inputNumber}")
    }
}

/**
 * detail: ShapeableImageView 实体类
 * @author Ttt
 */
class ShapeableImageBean(
    val type: Int,
    val imageUrl: String
)

/**
 * detail: 文章实体类
 * @author Ttt
 */
class ArticleBean1(
    // 文章标题
    val title: String,
    // 配图
    val pictures: Bitmap?,
    // 文章内容
    val content: String,
    // 背景颜色
    val background: Int
)

// ==============================
// = 配合 ConcatAdapter 转换 Item =
// ==============================

class BannerBeanItem(
    val obj: List<BannerBean>
)

// 一级分类
class ClassifyBeanItem1(
    val obj: ClassifyBean
)

// 二级分类
class ClassifyBeanItem2(
    val obj: ClassifyBean
)

// 三级分类
class ClassifyBeanItem3(
    val obj: ClassifyBean
)

class CommodityBeanItem(
    val obj: CommodityBean
)

class CommodityEvaluateBeanItem(
    val obj: CommodityBean
)

class ShapeableImageBeanItem(
    val obj: ShapeableImageBean
)

class ArticleBean1Item(
    val obj: ArticleBean1
)

class HeaderFooterItem(
    val title: String
)

// =============
// = 创建数据方法 =
// =============

fun createMainData(): MainBean {
    return MainBean(
        createBannerLists(),
        createClassifyLists(),
        createCommodityLists(),
        createShapeableImageLists(),
        createArticleLists(),
    )
}

private fun createBannerLists(): List<BannerBean> {
    return ArrayList<BannerBean>().apply {
        for (position in 1 until 5) {
            add(
                BannerBean(
                    id = position.toString(),
                    imageUrl = RandomHelper.randomImage800x400()
                )
            )
        }
    }
}

private fun createClassifyLists(): List<ClassifyBean> {
    return ArrayList<ClassifyBean>().apply {
        // 主分类背景色
        val level0Bg = ColorUtils.getRandomColor()
        // 二级分类背景色
        val level1Bg = ColorUtils.getRandomColor()
        // 三级分类背景色
        val level2Bg = ColorUtils.getRandomColor()

        add(
            // 添加一级分类
            ClassifyBean(
                id = "0-0",
                name = RandomHelper.randomWordRange(5, 15),
                rootId = "0",
                background = level0Bg,
                // 创建二级分类
                subLists = randomClassifyList(
                    1, "0-0", level1Bg, 4
                )
            )
        )

        add(
            // 添加一级分类
            ClassifyBean(
                id = "0-1",
                name = RandomHelper.randomWordRange(5, 15),
                rootId = "0",
                background = level0Bg,
            )
        )

        add(
            // 添加一级分类
            ClassifyBean(
                id = "0-2",
                name = RandomHelper.randomWordRange(5, 15),
                rootId = "0",
                background = level0Bg,
                // 创建二级分类
                subLists = randomClassifyList(
                    1, "0-2", level1Bg, 2
                )
            )
        )

        // 创建一级分类
        val temp = ClassifyBean(
            id = "0-3",
            name = RandomHelper.randomWordRange(5, 15),
            rootId = "0",
            background = level0Bg,
            // 创建二级分类
            subLists = randomClassifyList(
                1, "0-3", level1Bg, 5
            )
        )

        // 创建三级分类
        temp.subLists?.let {
            for (i in it.indices) {
                it[i].subLists = randomClassifyList(
                    2, "1-$i", level2Bg,
                    RandomUtils.getRandom(1, 4)
                )
            }
        }
        add(temp)

        add(
            // 添加一级分类
            ClassifyBean(
                id = "0-4",
                name = RandomHelper.randomWordRange(5, 15),
                rootId = "0",
                background = level0Bg,
                // 创建二级分类
                subLists = randomClassifyList(
                    1, "0-4", level1Bg, 1
                )
            )
        )
    }
}

fun createCommodity(): CommodityBean {
    return CommodityBean(
        // 商品名
        name = RandomHelper.randomWordRange(5, 40),
        // 商品图片
        picture = RandomHelper.randomImage200X(),
        // 商品价格
        price = RandomHelper.randomPrice(),
    )
}

fun createCommodityEvaluate(): CommodityBean {
    return CommodityBean(
        // 商品名
        name = RandomHelper.randomWordRange(5, 40),
        // 商品图片
        picture = RandomHelper.randomImage200X(),
        // 商品价格
        price = RandomHelper.randomPrice(),
        // 商品类型 ( 是否评价商品 )
        isEvaluateCommodity = true,
        // 商品评价等级
        evaluateLevel = RandomHelper.randomFloat(max = 6),
        // 商品评价内容
        evaluateContent = RandomHelper.randomText(50, 10)
    )
}

private fun createCommodityLists(): List<CommodityBean> {
    return ArrayList<CommodityBean>().apply {

        // =============
        // = 添加普通商品 =
        // =============

        for (position in 1 until 5) {
            add(createCommodity())
        }

        // =============
        // = 添加评价商品 =
        // =============

        for (position in 1 until 5) {
            add(createCommodityEvaluate())
        }

        // =============
        // = 随机打乱顺序 =
        // =============

        this.shuffle()
    }
}

private fun createShapeableImageLists(): List<ShapeableImageBean> {
    return ArrayList<ShapeableImageBean>().apply {
        add(
            ShapeableImageBean(
                type = 1, // 圆形
                imageUrl = RandomHelper.randomImage400(11)
            )
        )
        add(
            ShapeableImageBean(
                type = 2, // 圆角
                imageUrl = RandomHelper.randomImage400(12)
            )
        )
        add(
            ShapeableImageBean(
                type = 3, // 水滴形
                imageUrl = RandomHelper.randomImage400(13)
            )
        )
        add(
            ShapeableImageBean(
                type = 4, // 叶子形状
                imageUrl = RandomHelper.randomImage400(14)
            )
        )
    }
}

private fun createArticleLists(): List<ArticleBean1> {
    return ArrayList<ArticleBean1>().apply {
        for (position in 1 until 3) {
            add(
                ArticleBean1(
                    title = "第${
                        ChineseUtils.numberToCHN(
                            position.toString(), false
                        )
                    }篇: ${RandomHelper.randomWord(5)}",
                    pictures = randomBitmap(position),
                    content = RandomHelper.randomWordRange(50, 100),
                    background = ColorUtils.getRandomColor()
                )
            )
        }
    }
}

// ==========
// = 临时方法 =
// ==========

private fun randomBitmap(position: Int): Bitmap? {
    val rawId = ResourceUtils.getRawId("wallpaper_${position}")
    val stream = ResourceUtils.openRawResource(rawId)
    return ImageUtils.decodeStream(stream)
}

private fun randomClassifyList(
    // 分类级别
    level: Int,
    // 父类 id
    rootId: String,
    // 背景颜色
    background: Int,
    // 随机数量
    count: Int
): List<ClassifyBean> {
    return ArrayList<ClassifyBean>().apply {
        for (position in 1 until count) {
            add(
                ClassifyBean(
                    id = "$level-$position",
                    name = RandomHelper.randomWordRange(5, 15),
                    rootId = rootId,
                    background = background,
                    subLists = null
                )
            )
        }
    }
}

// ============
// = 转换 Item =
// ============

fun convertMainDataItem(mainData: MainBean): ArrayList<Any> {
    val lists = ArrayList<Any>()

    lists.add(BannerBeanItem(mainData.bannerLists))

    var isFirst = false

    mainData.commodityLists.forEach {
        if (!isFirst) {
            isFirst = true
            it.isFirst = true
        }
        if (it.isEvaluateCommodity) {
            lists.add(CommodityEvaluateBeanItem(it))
        } else {
            lists.add(CommodityBeanItem(it))
        }
    }

    mainData.shapeableImageLists.forEach {
        lists.add(ShapeableImageBeanItem(it))
    }

    mainData.articleLists.forEach {
        lists.add(ArticleBean1Item(it))
    }

    mainData.classifyLists.forEach { it1 ->
        // 一级分类
        lists.add(ClassifyBeanItem1(it1))
        // 二级分类
        it1.subLists?.forEach { it2 ->
            lists.add(ClassifyBeanItem2(it2))
            // 三级分类
            it2.subLists?.forEach { it3 ->
                lists.add(ClassifyBeanItem3(it3))
            }
        }
    }
    return lists
}