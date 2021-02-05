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
    // 每页请求条数
    private       int        mPageSize;
    // 是否最后一页
    private       boolean    mLastPage;

    public DevPage(
            int page,
            int pageSize
    ) {
        config = new PageConfig(page, pageSize);
        // 重置页数信息
        setPage(config.page).setPageSize(config.pageSize);
    }

    /**
     * detail: 内部页数配置
     * @author Ttt
     */
    private class PageConfig {

        // 页数配置
        public int page;
        // 每页请求条数配置
        public int pageSize;

        public PageConfig(
                int page,
                int pageSize
        ) {
            this.page = page;
            this.pageSize = pageSize;
        }
    }

    // ===============
    // = 对外公开方法 =
    // ===============

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
        this.mPage = page;
        return this;
    }

    /**
     * 判断当前页数是否一致
     * @param page 待校验当前页数
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsPage(final int page) {
        return this.mPage == page;
    }

    // =

    /**
     * 获取每页请求条数
     * @return 每页请求条数
     */
    public int getPageSize() {
        return mPageSize;
    }

    /**
     * 设置每页请求条数
     * @param pageSize 每页请求条数
     * @return {@link DevPage}
     */
    public DevPage<T> setPageSize(final int pageSize) {
        this.mPageSize = pageSize;
        return this;
    }

    /**
     * 判断每页请求条数是否一致
     * @param pageSize 待校验每页请求条数
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsPageSize(final int pageSize) {
        return this.mPageSize == pageSize;
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
     * @param lastPage {@code true} yes, {@code false} no
     * @return {@link DevPage}
     */
    public DevPage<T> setLastPage(final boolean lastPage) {
        this.mLastPage = lastPage;
        return this;
    }

    /**
     * 计算是否最后一页 ( 并同步更新 )
     * @param size 数据条数
     * @return {@link DevPage}
     */
    public DevPage<T> calculateLastPage(final int size) {
        return setLastPage(size < config.pageSize);
    }
}