package dev.base;

/**
 * detail: Page 实体类
 * @author Ttt
 */
public class DevPage<T>
        extends DevObject<T> {

    // 页数配置
    private final PageConfig config;
    // 当前页数 ( 已成功 )
    private       int        mPage;
    // 是否最后一页
    private       boolean    mLastPage;

    public DevPage(final PageConfig pageConfig) {
        this(pageConfig.page, pageConfig.pageSize);
    }

    public DevPage(
            final int page,
            final int pageSize
    ) {
        config = new PageConfig(page, pageSize);
        // 设置页数信息
        mPage = config.page;
    }

    /**
     * detail: 页数配置信息
     * @author Ttt
     */
    public static class PageConfig {

        // 页数配置
        public final int page;
        // 每页请求条数配置
        public final int pageSize;

        public PageConfig(
                final int page,
                final int pageSize
        ) {
            this.page     = page;
            this.pageSize = pageSize;
        }
    }

    /**
     * 重置操作
     * @return {@link DevPage}
     */
    public DevPage<T> reset() {
        return setPage(config.page).setLastPage(false);
    }

    /**
     * 重置操作
     * @param reset 是否进行重置 ( 方便判断是否刷新进行调用 )
     * @return {@link DevPage}
     */
    public DevPage<T> reset(final boolean reset) {
        if (reset) reset();
        return this;
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取当前页数
     * @return 当前页数
     */
    public int getPage() {
        return mPage;
    }

    /**
     * 设置当前页数
     * @param page 当前页数
     * @return {@link DevPage}
     */
    public DevPage<T> setPage(final int page) {
        mPage = page;
        return this;
    }

    /**
     * 判断当前页数是否一致
     * @param page 待校验当前页数
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsPage(final int page) {
        return mPage == page;
    }

    // =

    /**
     * 获取页数配置信息
     * @return {@link PageConfig}
     */
    public PageConfig getConfig() {
        return config;
    }

    /**
     * 获取配置初始页页数
     * @return 初始页页数
     */
    public int getConfigPage() {
        return config.page;
    }

    /**
     * 获取配置每页请求条数
     * @return 每页请求条数
     */
    public int getConfigPageSize() {
        return config.pageSize;
    }

    // =

    /**
     * 获取每页请求条数
     * @return 每页请求条数
     */
    public int getPageSize() {
        return config.pageSize;
    }

    /**
     * 判断每页请求条数是否一致
     * @param pageSize 待校验每页请求条数
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsPageSize(final int pageSize) {
        return config.pageSize == pageSize;
    }

    // =

    /**
     * 判断是否最后一页
     * @return {@code true} yes, {@code false} no
     */
    public boolean isLastPage() {
        return mLastPage;
    }

    /**
     * 设置是否最后一页
     * @param lastPage 是否最后一页
     * @return {@link DevPage}
     */
    public DevPage<T> setLastPage(final boolean lastPage) {
        mLastPage = lastPage;
        return this;
    }

    /**
     * 计算是否最后一页 ( 并同步更新 )
     * @param size 数据条数
     * @return {@link DevPage}
     */
    public DevPage<T> calculateLastPage(final int size) {
        return setLastPage(isLessThanPageSize(size));
    }

    // ==========
    // = 快捷方法 =
    // ==========

    /**
     * 判断是否第一页
     * @return {@code true} yes, {@code false} no
     */
    public boolean isFirstPage() {
        return mPage == config.page;
    }

    /**
     * 判断是否允许请求下一页
     * @return {@code true} yes, {@code false} no
     */
    public boolean canNextPage() {
        return !mLastPage; // 非最后一页则可以请求
    }

    /**
     * 获取下一页页数
     * @return 下一页页数
     */
    public int getNextPage() {
        return mPage + 1;
    }

    /**
     * 累加当前页数 ( 下一页 )
     * @return {@link DevPage}
     */
    public DevPage<T> nextPage() {
        return setPage(mPage + 1);
    }

    /**
     * 判断是否小于每页请求条数
     * <pre>
     *     如果小于每页请求条数, 也表明已经没有下一页
     * </pre>
     * @param size 数据条数
     * @return {@code true} yes, {@code false} no
     */
    public boolean isLessThanPageSize(final int size) {
        return size < config.pageSize;
    }

    // =

    /**
     * 请求响应处理
     * @param refresh 是否刷新操作
     * @return {@link DevPage}
     */
    public DevPage<T> response(final boolean refresh) {
        // 刷新重置操作
        if (refresh) reset();
        // 累加当前页数 ( 下一页 )
        return nextPage();
    }

    /**
     * 请求响应处理
     * @param refresh  是否刷新操作
     * @param lastPage 是否最后一页
     * @return {@link DevPage}
     */
    public DevPage<T> response(
            final boolean refresh,
            final boolean lastPage
    ) {
        return response(refresh).setLastPage(lastPage);
    }

    // ===========
    // = Default =
    // ===========

    // 默认页数配置
    public static final int DF_PAGE      = 1;
    // 默认每页请求条数配置
    public static final int DF_PAGE_SIZE = 10;

    /**
     * 获取默认配置 Page 实体类
     * @param <T> 泛型
     * @return {@link DevPage}
     */
    public static <T> DevPage<T> getDefault() {
        return new DevPage<>(
                DF_PAGE, DF_PAGE_SIZE
        );
    }
}