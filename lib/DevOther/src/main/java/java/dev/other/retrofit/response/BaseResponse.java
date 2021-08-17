package java.dev.other.retrofit.response;

/**
 * detail: 请求响应统一解析类
 * @author Ttt
 * <pre>
 *     主要用于统一解析后台返回 data、message、code 等
 * </pre>
 */
public class BaseResponse<T> {

    // 返回数据
    public           T         data;
    // 返回消息
    public           String    errorMsg; // message
    // 返回结果状态 ( 内部定义 )
    public           String    errorCode; // code
    // 请求结果
    public transient boolean   result;
    // 请求异常
    public transient Throwable exception;
}