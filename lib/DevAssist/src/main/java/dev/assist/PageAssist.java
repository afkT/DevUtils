package dev.assist;

/**
 * detail: Page 页数辅助类
 * @author Ttt
 */
public class PageAssist<T> extends RequestStatusAssist<T> {

    // 全局默认初始化页数配置
    public static int DF_PAGE_NUM = 1;
    // 全局默认配置每页请求条数
    public static int DF_PAGE_SIZE = 20;
    // 默认页数配置
    private int config_page_num = DF_PAGE_NUM;
    // 默认每页请求条数配置
    private int config_page_size = DF_PAGE_SIZE;

    // 当前请求页数 ( 已成功 )
    private int pageNum = config_page_num;
    // 当前 ( 正在 / 准备 ) 请求的页数, 如果成功则赋值给 pageNum
    private int pageNumReady = config_page_num;
    // 每页请求条数
    private int pageSize = config_page_size;
    // 数据总条数 ( 全部 ), 非当前页数有多少条
    private int totalRow;
    // 判断是否最后一页
    private boolean lastPage;

    public PageAssist() {
    }

    public PageAssist(int config_page_num) {
        this.config_page_num = config_page_num;
    }

    public PageAssist(int config_page_num, int config_page_size) {
        this.config_page_num = config_page_num;
        this.config_page_size = config_page_size;
    }

    // ================
    // = 对外公开方法 =
    // ================

    /**
     * 初始化全局分页配置
     * @param pageNum  初始化页数
     * @param pageSize 初始化条数
     */
    public static void initPageConfig(final int pageNum, final int pageSize) {
        PageAssist.DF_PAGE_NUM = pageNum;
        PageAssist.DF_PAGE_SIZE = pageSize;
    }

    // =

    /**
     * 获取当前请求页数
     * @return 当前请求页数
     */
    public int getPageNum() {
        return pageNum;
    }

    /**
     * 获取当前请求页数
     * @param pageNum 当前请求页数
     * @return 当前请求页数
     */
    public int getPageNum(final int pageNum) {
        return (this.pageNum = pageNum);
    }

    /**
     * 设置当前请求页数
     * @param pageNum 当前请求页数
     * @return {@link PageAssist}
     */
    public PageAssist<T> setPageNum(final int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    // =

    /**
     * 获取当前 ( 正在 / 准备 ) 请求的页数
     * @return 当前 ( 正在 / 准备 ) 请求的页数
     */
    public int getPageNumReady() {
        return pageNumReady;
    }

    /**
     * 获取当前 ( 正在 / 准备 ) 请求的页数
     * @param pageNumReady 请求的页数
     * @return 当前 ( 正在 / 准备 ) 请求的页数
     */
    public int getPageNumReady(final int pageNumReady) {
        return (this.pageNumReady = pageNumReady);
    }

    /**
     * 设置当前 ( 正在 / 准备 ) 请求的页数
     * @param pageNumReady 请求的页数
     * @return {@link PageAssist}
     */
    public PageAssist<T> setPageNumReady(final int pageNumReady) {
        this.pageNumReady = pageNumReady;
        return this;
    }

    // =

    /**
     * 获取每页请求条数
     * @return 每页请求条数
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 获取每页请求条数
     * @param pageSize 每页请求条数
     * @return 每页请求条数
     */
    public int getPageSize(final int pageSize) {
        return (this.pageSize = pageSize);
    }

    /**
     * 设置每页请求条数
     * @param pageSize 每页请求条数
     * @return {@link PageAssist}
     */
    public PageAssist<T> setPageSize(final int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    // =

    /**
     * 获取数据总条数
     * @return 数据总条数
     */
    public int getTotalRow() {
        return totalRow;
    }

    /**
     * 获取数据总条数
     * @param totalRow 数据总条数
     * @return 数据总条数
     */
    public int getTotalRow(final int totalRow) {
        return (this.totalRow = totalRow);
    }

    /**
     * 设置数据总条数
     * @param totalRow 数据总条数
     * @return {@link PageAssist}
     */
    public PageAssist<T> setTotalRow(final int totalRow) {
        this.totalRow = totalRow;
        return this;
    }

    // =

    /**
     * 判断是否最后一页
     * @return {@code true} yes, {@code false} no
     */
    public boolean isLastPage() {
        return lastPage;
    }

    /**
     * 判断是否最后一页
     * @param lastPage {@code true} yes, {@code false} no
     * @return {@code true} yes, {@code false} no
     */
    public boolean isLastPage(final boolean lastPage) {
        return (this.lastPage = lastPage);
    }

    /**
     * 设置是否最后一页
     * @param lastPage {@code true} yes, {@code false} no
     * @return {@link PageAssist}
     */
    public PageAssist<T> setLastPage(final boolean lastPage) {
        this.lastPage = lastPage;
        return this;
    }

    // ============
    // = 快捷方法 =
    // ============

    /**
     * 判断是否第一页
     * @return {@code true} yes, {@code false} no
     */
    public boolean isFirstPage() {
        return isFirstPage(config_page_num);
    }

    /**
     * 判断是否第一页
     * @param firstPage 第一页页数
     * @return {@code true} yes, {@code false} no
     */
    public boolean isFirstPage(final int firstPage) {
        return pageNum == firstPage;
    }

    /**
     * 判断是否允许请求下一页
     * @return {@code true} yes, {@code false} no
     */
    public boolean isAllowNextPage() {
        return !lastPage; // 非最后一页则可以请求
    }

    // =

    /**
     * 获取下一页页数
     * @return 下一页页数
     */
    public int getNextPage() {
        return pageNum + 1;
    }

    /**
     * 设置下一页页数
     * @return {@link PageAssist}
     */
    public PageAssist<T> nextPage() {
        pageNum += 1;
        return this;
    }

    // =

    /**
     * 获取下一页页数 ( 预加载页数 )
     * @return 下一页页数 ( 预加载页数 )
     */
    public int getNextPageReady() {
        return pageNumReady + 1;
    }

    /**
     * 设置下一页页数 ( 预加载页数 )
     * @return {@link PageAssist}
     */
    public PageAssist<T> nextPageReady() {
        pageNumReady += 1;
        return this;
    }

    // ============
    // = 其他逻辑 =
    // ============

    /**
     * 判断是否小于每页请求条数
     * <pre>
     *     如果小于每页请求条数, 也表明已经没有下一页
     * </pre>
     * @param size 当前请求返回条数
     * @return {@code true} yes, {@code false} no
     */
    public boolean isLessThanPageSize(final int size) {
        return size < pageSize;
    }

    /**
     * 重置操作
     * <pre>
     *     设置 请求页数、每页请求条数、非最后一页、请求默认状态
     * </pre>
     * @return {@link PageAssist}
     */
    public PageAssist<T> reset() {
        setPageNum(config_page_num).setPageNumReady(config_page_num).setLastPage(false).setRequestNormal();
        return this;
    }
}