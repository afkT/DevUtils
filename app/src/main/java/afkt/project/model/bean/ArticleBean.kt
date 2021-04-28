package afkt.project.model.bean

/**
 * detail: 文章信息实体类
 * @author Ttt
 */
class ArticleBean {
    @JvmField
    var data: DataBean? = null

    class DataBean {
        var size = 0

        @JvmField
        var datas: List<ListBean>? = null

        class ListBean {
            var id = 0
            var link: String? = null
            var niceDate: String? = null
            var niceShareDate: String? = null
            var author: String? = null
            var title: String? = null
            var type = 0
        }
    }
}