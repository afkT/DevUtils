package afkt.project.base.constants.http;

import dev.DevUtils;
import dev.environment.DevEnvironment;

/**
 * detail: Http 请求接口地址
 * @author Ttt
 */
public final class HttpApis {

    private HttpApis() {
    }

    /**
     * 获取文章列表请求地址
     * @return 文章列表请求地址
     */
    public static final String getArticleListUrl() {
        return DevEnvironment.getServiceEnvironmentValue(DevUtils.getContext())
                + "article/list/%s/json";
    }
}