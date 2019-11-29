package afkt.base.able;

/**
 * detail: 基类通用方法
 * @author Ttt
 */
public interface IDevBaseMethod {

    /**
     * 初始化方法顺序
     * <pre>
     *     initViews() => initValues() => initListeners() => initOtherOperate()
     * </pre>
     */
    void initMethodOrder();

    // ==============
    // = 初始化方法 =
    // ==============

    /**
     * 初始化 Views
     */
    void initViews();

    /**
     * 初始化全部参数、配置
     */
    void initValues();

    /**
     * 初始化绑定事件
     */
    void initListeners();

    /**
     * 初始化其他操作
     */
    void initOtherOperate();
}
