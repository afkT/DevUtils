package afkt.project.base.constants.http;

/**
 * detail: Http 请求接口地址
 * @author Ttt
 */
public final class HttpApis {

    private HttpApis() {
    }

    public static final String BASE_URL = "https://www.wanandroid.com/";
    // 文章列表
    public static final String ARTICLE_LIST = BASE_URL + "article/list/%s/json";
}
