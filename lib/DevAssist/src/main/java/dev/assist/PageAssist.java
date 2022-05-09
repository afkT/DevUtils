package dev.assist;

import dev.base.DevPage;
import dev.base.state.RequestState;

/**
 * detail: Page 辅助类
 * @author Ttt
 */
public class PageAssist<T>
        extends RequestState<T> {

    // 全局页数配置
    public static int DEF_PAGE      = DevPage.DEF_PAGE;
    // 全局每页请求条数配置
    public static int DEF_PAGE_SIZE = DevPage.DEF_PAGE_SIZE;

    // Page Object
    private final DevPage<T> mPage;

    public PageAssist() {
        this(DEF_PAGE, DEF_PAGE_SIZE);
    }

    public PageAssist(final DevPage.PageConfig pageConfig) {
        this(pageConfig.page, pageConfig.pageSize);
    }

    public PageAssist(
            final int page,
            final int pageSize
    ) {
        super();
        mPage = new DevPage<>(page, pageSize);
    }

    // =

    /**
     * 初始化全局分页配置
     * @param page     页数
     * @param pageSize 每页请求条数
     */
    public static void initPageConfig(
            final int page,
            final int pageSize
    ) {
        PageAssist.DEF_PAGE      = page;
        PageAssist.DEF_PAGE_SIZE = pageSize;
    }

    /**
     * 重置操作
     * @return {@link PageAssist}
     */
    public PageAssist<T> reset() {
        mPage.reset();
        setRequestNormal();
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
        return mPage.getPage();
    }

    /**
     * 设置当前页数
     * @param page 当前页数
     * @return {@link PageAssist}
     */
    public PageAssist<T> setPage(final int page) {
        mPage.setPage(page);
        return this;
    }

    /**
     * 判断当前页数是否一致
     * @param page 待校验当前页数
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsPage(final int page) {
        return mPage.equalsPage(page);
    }

    // =

    /**
     * 获取页数配置信息
     * @return {@link DevPage.PageConfig}
     */
    public DevPage.PageConfig getConfig() {
        return mPage.getConfig();
    }

    /**
     * 获取配置初始页页数
     * @return 初始页页数
     */
    public int getConfigPage() {
        return mPage.getConfigPage();
    }

    /**
     * 获取配置每页请求条数
     * @return 每页请求条数
     */
    public int getConfigPageSize() {
        return mPage.getConfigPageSize();
    }

    // =

    /**
     * 获取每页请求条数
     * @return 每页请求条数
     */
    public int getPageSize() {
        return mPage.getPageSize();
    }

    /**
     * 判断每页请求条数是否一致
     * @param pageSize 待校验每页请求条数
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsPageSize(final int pageSize) {
        return mPage.equalsPageSize(pageSize);
    }

    // =

    /**
     * 判断是否最后一页
     * @return {@code true} yes, {@code false} no
     */
    public boolean isLastPage() {
        return mPage.isLastPage();
    }

    /**
     * 设置是否最后一页
     * @param lastPage 是否最后一页
     * @return {@link PageAssist}
     */
    public PageAssist<T> setLastPage(final boolean lastPage) {
        mPage.setLastPage(lastPage);
        return this;
    }

    /**
     * 计算是否最后一页 ( 并同步更新 )
     * @param size 数据条数
     * @return {@link PageAssist}
     */
    public PageAssist<T> calculateLastPage(final int size) {
        mPage.calculateLastPage(size);
        return this;
    }

    // ==========
    // = 快捷方法 =
    // ==========

    /**
     * 判断是否第一页
     * @return {@code true} yes, {@code false} no
     */
    public boolean isFirstPage() {
        return mPage.isFirstPage();
    }

    /**
     * 判断是否允许请求下一页
     * @return {@code true} yes, {@code false} no
     */
    public boolean canNextPage() {
        return mPage.canNextPage();
    }

    /**
     * 获取下一页页数
     * @return 下一页页数
     */
    public int getNextPage() {
        return mPage.getNextPage();
    }

    /**
     * 累加当前页数 ( 下一页 )
     * @return {@link PageAssist}
     */
    public PageAssist<T> nextPage() {
        mPage.nextPage();
        return this;
    }

    /**
     * 判断是否小于每页请求条数
     * @param size 数据条数
     * @return {@code true} yes, {@code false} no
     */
    public boolean isLessThanPageSize(final int size) {
        return mPage.isLessThanPageSize(size);
    }

    // =

    /**
     * 请求响应处理
     * @param refresh 是否刷新操作
     * @return {@link PageAssist}
     */
    public PageAssist<T> response(final boolean refresh) {
        mPage.response(refresh);
        return this;
    }

    /**
     * 请求响应处理
     * @param refresh  是否刷新操作
     * @param lastPage 是否最后一页
     * @return {@link PageAssist}
     */
    public PageAssist<T> response(
            final boolean refresh,
            final boolean lastPage
    ) {
        mPage.response(refresh, lastPage);
        return this;
    }
}