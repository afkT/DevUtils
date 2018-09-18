package com.dev.use.toasty;

import android.graphics.Color;
import android.graphics.Typeface;

import dev.utils.app.toast.cus.Toasty;

/**
 * detail: Toasty 使用方法
 * Created by Ttt
 */
class ToastyUse {

    /**
     * 配置相关
     */
    private void config(){
        Toasty.Config config = Toasty.Config.getInstance();
        // 设置异常、错误颜色 - 红色
        config.setErrorColor(Color.parseColor("#D50000"));
        // 设置打印信息颜色 - 海洋蓝
        config.setInfoColor(Color.parseColor("#3F51B5"));
        // 设置成功颜色 - 绿色
        config.setSuccessColor(Color.parseColor("#388E3C"));
        // 设置警告颜色 - 橙色
        config.setWarningColor(Color.parseColor("#FFA900"));
        // ==
        // 设置字体样式
        config.setToastTypeface(Typeface.create("sans-serif-condensed", Typeface.NORMAL));
        // 设置字体大小 sp
        config.setTextSize(16);
        // 是否渲染图标
        config.setTintIcon(true);
        // 是否使用新的Toast
        config.setNewToast(true);
        // 应用配置
        config.apply();
    }

    /**
     * 使用
     */
    private void use(){
//        // 显示成功 Toast
//        Toasty.success(mActivity, "Success!", Toast.LENGTH_SHORT, true);
//        // 显示异常 Toast
//        Toasty.error(mActivity, "Error!", Toast.LENGTH_SHORT, true);
//        // 显示信息 Toast
//        Toasty.info(mActivity, "Info!", Toast.LENGTH_SHORT, true);
//        // 显示警告 Toast
//        Toasty.warning(mActivity, "Warning!", Toast.LENGTH_SHORT, true);
//        // 显示默认 Toast (灰色)
//        Toasty.normal(mActivity, "Normal!", Toast.LENGTH_SHORT);
//
//        // -- 使用自定义配置 --
//        Toasty.custom(mActivity, "自定义Toast", ToastyUtils.getDrawable(DevUtils.getContext(), R.mipmap.ic_launcher), Color.RED, Toast.LENGTH_LONG, true);
//
//        Toasty.custom(mActivity, "自定义Toast", ToastyUtils.getDrawable(DevUtils.getContext(), R.mipmap.ic_launcher), Color.RED, Toast.LENGTH_LONG, true, false);
//
//        // -- 或者获取 Toast View 进行自定义 --
//
//        // 引入View
//        final View toastLayout = ((LayoutInflater) DevUtils.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(dev.utils.R.layout.dev_toast_layout, null);
//        // 初始化View
//        final ImageView toastIcon = (ImageView) toastLayout.findViewById(dev.utils.R.id.vid_dtl_toast_igview);
//        final TextView toastTextView = (TextView) toastLayout.findViewById(dev.utils.R.id.vid_dtl_toast_tv);
//
//        // 显示Toast
//        Toasty.showToasty(mActivity, toastLayout, duration, isNewToast);
    }
}
