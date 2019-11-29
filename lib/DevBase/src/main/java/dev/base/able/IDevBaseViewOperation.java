package dev.base.able;

import android.view.View;

/**
 * detail: 基类 View 相关方法
 * @author Ttt
 */
public interface IDevBaseViewOperation {

    /**
     * 获取 Content View
     * @return Content View
     */
    View getContentView();

    // ================
    // = Content View =
    // ================

    /**
     * 获取 Content Layout id
     * @return Content Layout Id
     */
    int contentId();

    /**
     * 获取 Content Layout View
     * @return Content Layout View
     */
    View contentView();
}
