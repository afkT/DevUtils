package dev.capture

/**
 * detail: 接口所属功能注释获取
 * @author Ttt
 */
interface UrlFunctionGet {

    /**
     * 接口所属功能注释获取
     * @param moduleName    模块名 ( 要求唯一性 )
     * @param url           原始请求链接
     * @param method        请求方法
     * @param convertUrlKey url 匹配规则 ( 拆分 ? 前为 key 进行匹配 )
     * @param cacheFunction 缓存功能注释
     * @return 接口所属功能注释
     */
    fun toUrlFunction(
        moduleName: String,
        url: String,
        method: String,
        convertUrlKey: String?,
        cacheFunction: String?
    ): String?
}