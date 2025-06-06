package afkt.httpmanager.use.feature.media.data.model

// ====================
// = 媒体信息模块数据模型 =
// ====================

// ===============
// = 摄影相关实体类 =
// ===============

/**
 * detail: 摄影图片信息
 * @author Ttt
 */
data class PhotoBean(
    val aperture: String,
    val author: String,
    val awardCategory: String,
    val cameraBrand: String,
    val focalLength: String,
    val imageName: String,
    val imageUrl: String,
    val iso: String,
    val model: String
)

// ===============
// = 电影相关实体类 =
// ===============

/**
 * detail: 电影详情信息
 * @author Ttt
 */
data class MovieDetailBean(
    val backdrop_path: String,
    val id: Int,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val status: String,
    val title: String,
    val video: Boolean
)