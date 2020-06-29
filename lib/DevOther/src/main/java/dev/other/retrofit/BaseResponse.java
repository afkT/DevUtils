package dev.other.retrofit;

/**
 * detail: 请求响应统一解析类
 * @author Ttt
 * <pre>
 *     统一约定响应格式, 如果有特殊则自己实现
 *     Retrofit ConverterFactory 实现解析映射
 *     <p></p>
 *     Retrofit 自定义 GsonConverter 处理所有请求错误情况
 *     @see <a href="https://www.jianshu.com/p/5b8b1062866b"/>
 * </pre>
 */
public class BaseResponse<T> {

    // 返回数据
    public T      data;
    // 返回消息
    public String message;
    // 返回结果状态 ( 内部定义 )
    public String code;
}