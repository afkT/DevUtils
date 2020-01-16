package afkt.project.base.constants.http;

import dev.environment.annotation.Environment;
import dev.environment.annotation.Module;

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

    @Module(alias = "服务器接口")
    private class BaseService {

        @Environment(value = "https://www.wanandroid.com/article", isRelease = true, alias = "文章模块")
        private String Article;

        @Environment(value = "https://www.baidu.com", alias = "临时测试")
        private String Temp;
    }
}
