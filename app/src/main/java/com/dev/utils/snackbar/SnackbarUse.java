package com.dev.utils.snackbar;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.dev.R;

import dev.utils.app.SnackbarUtils;

/**
 * detail: Snackbar 使用方法
 * Created by Ttt
 */
public final class SnackbarUse {

    private SnackbarUse(){
    }

    // 日志TAG
    private static final String TAG = SnackbarUse.class.getSimpleName();

    View view;
    Window window;
    Fragment fragment;
    Activity activity;
    SnackbarUtils.Style style;
    View.OnClickListener clickListener;

    public void snackbarUse(){

        // ===================================================
        // == 只能通过以下四种方式 获取 SnackbarUtils 对象 ==
        // ===================================================

        SnackbarUtils.with(view);

        SnackbarUtils.with(window);

        SnackbarUtils.with(fragment);

        SnackbarUtils.with(activity);


        // ======================
        // ==== 获取相关方法 ====
        // ======================

        // = 获取 View =

        // 获取 Snackbar 底层 View
        View snackbarView = SnackbarUtils.with(view).getSnackbarView();

        // 获取 Snackbar TextView(snackbar_text) - 左侧 文本TextView
        TextView textView = SnackbarUtils.with(view).getTextView();

        // 获取 Snackbar Action Button(snackbar_action) - 右侧 Button
        Button actionButton = SnackbarUtils.with(view).getActionButton();

        // ==

        // 获取 Snackbar 对象
        Snackbar snackbar = SnackbarUtils.with(view).getSnackbar();

        // 获取 View 阴影边距大小 - View 自带阴影
        int shadowMargin = SnackbarUtils.with(view).getShadowMargin();

        // 获取 是否自动计算边距 (如: 显示在 View 下面, 但是下方距离不够, 自动设置为在 View 上方显示)
        boolean autoCalc = SnackbarUtils.with(view).isAutoCalc(); // 只有设置 above / bellow 该属性才有意义

        // 获取 Snackbar 显示效果样式配置信息
        SnackbarUtils.StyleBuilder styleBuilder = SnackbarUtils.with(view).getStyle();


        // ======================
        // ==== 设置相关方法 ====
        // ======================

        // 设置 View 阴影边距大小
        SnackbarUtils.with(view).setShadowMargin(2);

        // 设置是否自动计算边距 (如: 显示在 View 下面, 但是下方距离不够, 自动设置为在 View 上方显示)
        SnackbarUtils.with(view).setAutoCalc(true); // 只有设置 above / bellow 该属性才有意义

        // 设置 Snackbar 显示效果样式
        SnackbarUtils snackbarUtils = SnackbarUtils.with(view).setStyle(style);

        // = 快捷设置指定样式效果的 Snackbar =

        // 设置 Snackbar 显示效果为 当前 SnackbarUtils 对象使用的样式
        Snackbar snackbar1 = SnackbarUtils.with(view).setSnackbarStyle(snackbar);

        // 设置 Snackbar 显示效果为 自定义样式效果
        Snackbar snackbar2 = SnackbarUtils.with(view).setSnackbarStyle(snackbar, style);


        // = 设置 Action Button 文案等 =

        // 设置 Snackbar Action Button(snackbar_action) 文案
        SnackbarUtils.with(view).setAction(R.string.app_name);

        // 设置 Snackbar Action Button(snackbar_action) 文案 - 支持格式化字符串
        SnackbarUtils.with(view).setAction(R.string.app_name, "1", 2);

        // 设置 Snackbar Action Button(snackbar_action) 文案
        SnackbarUtils.with(view).setAction("撤销");

        // 设置 Snackbar Action Button(snackbar_action) 文案 - 支持格式化字符串
        SnackbarUtils.with(view).setAction("撤销 - %s", "3");

        // 设置 Snackbar Action Button(snackbar_action) 文案以及点击事件
        SnackbarUtils.with(view).setAction(clickListener, R.string.app_name);

        // 设置 Snackbar Action Button(snackbar_action) 文案以及点击事件
        SnackbarUtils.with(view).setAction(clickListener, "撤销");


        // = 设置 事件相关 =

        // 设置 Snackbar 展示完成 及 隐藏完成 的监听
        SnackbarUtils.with(view).setCallback(new Snackbar.Callback(){
            @Override
            public void onShown(Snackbar sb) {
                super.onShown(sb);
                // Snackbar 显示
            }

            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                // Snackbar 关闭
            }
        });

        // ==================
        // ==== 操作方法 ====
        // ==================

        // = 关闭 =

        // 关闭显示 Snackbar
        SnackbarUtils.with(view).dismiss();

        // 关闭显示 Snackbar, 但不销毁 Snackbar
        SnackbarUtils.with(view).dismiss(false);

        // = 显示 - 支持 String、R.string.xx 以及格式化字符串 =

        // 显示 Short Snackbar
        SnackbarUtils.with(view).showShort("已收藏该消息");

        // 显示 Long Snackbar
        SnackbarUtils.with(view).showLong("已收藏该消息");

        // 显示 Indefinite Snackbar (无限时, 一直显示)
        SnackbarUtils.with(view).showIndefinite("已收藏该消息");
    }
}
