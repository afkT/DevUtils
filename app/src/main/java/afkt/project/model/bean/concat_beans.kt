package afkt.project.model.bean

import android.graphics.Bitmap
import dev.utils.app.ResourceUtils
import dev.utils.app.image.ImageUtils
import dev.utils.common.ChineseUtils
import dev.utils.common.ColorUtils
import dev.utils.common.RandomUtils

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
    val commodityName: String,
    // 商品图片
    val commodityPicture: String,
    // 商品价格
    val commodityPrice: Double,
    // 商品类型 ( 是否评价商品 )
    val type: Boolean = false,
    // 商品评价等级
    var evaluateLevel: Float = 0F,
    // 商品评价内容
    var evaluateContent: String? = ""
)

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

class TitleBeanItem(
    val title: String
)

class BannerBeanItem(
    val obj: BannerBean
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
                    imageUrl = String.format(
                        "https://picsum.photos/id/%s/800/400",
                        RandomUtils.getRandom(10, 50)
                    )
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
                name = ChineseUtils.randomWord(RandomUtils.getRandom(5, 15)),
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
                name = ChineseUtils.randomWord(RandomUtils.getRandom(5, 15)),
                rootId = "0",
                background = level0Bg,
            )
        )

        add(
            // 添加一级分类
            ClassifyBean(
                id = "0-2",
                name = ChineseUtils.randomWord(RandomUtils.getRandom(5, 15)),
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
            name = ChineseUtils.randomWord(RandomUtils.getRandom(5, 15)),
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
                name = ChineseUtils.randomWord(RandomUtils.getRandom(5, 15)),
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

private fun createCommodityLists(): List<CommodityBean> {
    return ArrayList<CommodityBean>().apply {

        // =============
        // = 添加普通商品 =
        // =============

        for (position in 1 until 5) {
            add(
                CommodityBean(
                    // 商品名
                    commodityName = ChineseUtils.randomWord(RandomUtils.getRandom(5, 40)),
                    // 商品图片
                    commodityPicture = "https://picsum.photos/20${RandomUtils.getRandom(0, 10)}",
                    // 商品价格
                    commodityPrice = RandomUtils.nextDoubleRange(15.1, 79.3),
                )
            )
        }

        // =============
        // = 添加评价商品 =
        // =============

        for (position in 1 until 5) {
            add(
                CommodityBean(
                    // 商品名
                    commodityName = ChineseUtils.randomWord(RandomUtils.getRandom(5, 40)),
                    // 商品图片
                    commodityPicture = "https://picsum.photos/20${RandomUtils.getRandom(0, 10)}",
                    // 商品价格
                    commodityPrice = RandomUtils.nextDoubleRange(15.1, 79.3),
                    // 商品类型 ( 是否评价商品 )
                    type = true,
                    // 商品评价等级
                    evaluateLevel = RandomUtils.getRandom(6).toFloat(),
                    // 商品评价内容
                    evaluateContent = ChineseUtils.randomWord(RandomUtils.getRandom(12, 60))
                )
            )
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
                imageUrl = "https://picsum.photos/id/11/400"
            )
        )
        add(
            ShapeableImageBean(
                type = 2, // 圆角
                imageUrl = "https://picsum.photos/id/12/400"
            )
        )
        add(
            ShapeableImageBean(
                type = 3, // 水滴形
                imageUrl = "https://picsum.photos/id/13/400"
            )
        )
        add(
            ShapeableImageBean(
                type = 4, // 叶子形状
                imageUrl = "https://picsum.photos/id/14/400"
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
                    }篇: ${ChineseUtils.randomWord(5)}",
                    pictures = randomBitmap(position),
                    content = ChineseUtils.randomWord(RandomUtils.getRandom(50, 100)),
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
                    name = ChineseUtils.randomWord(RandomUtils.getRandom(5, 15)),
                    rootId = rootId,
                    background = background,
                    subLists = null
                )
            )
        }
    }
}