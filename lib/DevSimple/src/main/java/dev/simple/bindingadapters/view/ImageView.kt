package dev.simple.bindingadapters.view

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import dev.simple.bindingadapters.qualifiesBindingAction
import dev.utils.app.ImageViewUtils

// ============================
// = ImageView BindingAdapter =
// ============================

/**
 * [ImageView] 的 Data Binding 适配集合。
 *
 * 布局属性统一为 `binding_iv_*`；实现主要对应 [dev.utils.app.ImageViewUtils] 中适合在单节点上表达的 `set*`、`remove*` 等 API。
 * <pre>
 *     未封装 `get*` 系列、`getImageView`、多 `View…` 变参批量操作、`getImageViewSize` 等不适合布局绑定的接口。
 *     不经图片引擎的原生 `setImage*` 亦可使用 [ImageViewLoadNative] 中 `binding_image_native_*`（与本文件属性名互不重复）。
 *     经引擎加载请使用 [ImageViewLoadEngine] 中 `binding_image_*`。
 *     需对仅接收者型移除等副作用多次触发时，使用带 `_ts` 后缀属性并绑定正时间戳（判定同 [qualifiesBindingAction]）。
 * </pre>
 */

// ==========
// = 尺寸约束 =
// ==========

/**
 * 通过数据绑定设置是否保持 drawable 原始宽高比。
 * <pre>
 *     布局属性 `binding_iv_adjust_view_bounds`；`null` 时不修改；委托 [ImageViewUtils.setAdjustViewBounds]。
 * </pre>
 *
 * @param adjustViewBounds 是否调整边界以保持纵横比
 */
@BindingAdapter("binding_iv_adjust_view_bounds")
fun ImageView.bindingIVAdjustViewBounds(adjustViewBounds: Boolean?) {
    if (adjustViewBounds == null) return
    ImageViewUtils.setAdjustViewBounds(this, adjustViewBounds)
}

/**
 * 通过数据绑定设置最大高度。
 * <pre>
 *     布局属性 `binding_iv_max_height`；`null` 时不修改；委托 [ImageViewUtils.setMaxHeight]。
 * </pre>
 *
 * @param maxHeight 最大高度（像素）
 */
@BindingAdapter("binding_iv_max_height")
fun ImageView.bindingIVMaxHeight(maxHeight: Int?) {
    if (maxHeight == null) return
    ImageViewUtils.setMaxHeight(this, maxHeight)
}

/**
 * 通过数据绑定设置最大宽度。
 * <pre>
 *     布局属性 `binding_iv_max_width`；`null` 时不修改；委托 [ImageViewUtils.setMaxWidth]。
 * </pre>
 *
 * @param maxWidth 最大宽度（像素）
 */
@BindingAdapter("binding_iv_max_width")
fun ImageView.bindingIVMaxWidth(maxWidth: Int?) {
    if (maxWidth == null) return
    ImageViewUtils.setMaxWidth(this, maxWidth)
}

// ========
// = Level =
// ========

/**
 * 通过数据绑定设置前景 Level。
 * <pre>
 *     布局属性 `binding_iv_image_level`；`null` 时不修改；委托 [ImageViewUtils.setImageLevel]；
 *     常配合 [android.graphics.drawable.LevelListDrawable] 使用。
 * </pre>
 *
 * @param level drawable level
 */
@BindingAdapter("binding_iv_image_level")
fun ImageView.bindingIVImageLevel(level: Int?) {
    if (level == null) return
    ImageViewUtils.setImageLevel(this, level)
}

// ========
// = 前景图 =
// ========

/**
 * 通过数据绑定按位图设置前景图。
 * <pre>
 *     布局属性 `binding_iv_bitmap`；`null` 时不修改；委托 [ImageViewUtils.setImageBitmap]。
 *     清空请用 `binding_iv_remove_bitmap_ts` 或 [ImageViewLoadNative] 的 `binding_image_native_bitmap`。
 * </pre>
 *
 * @param bitmap 位图
 */
@BindingAdapter("binding_iv_bitmap")
fun ImageView.bindingIVBitmap(bitmap: Bitmap?) {
    ImageViewUtils.setImageBitmap(this, bitmap)
}

/**
 * 通过数据绑定按 Drawable 设置前景图。
 * <pre>
 *     布局属性 `binding_iv_drawable`；`null` 时不修改；委托 [ImageViewUtils.setImageDrawable]。
 *     清空请用 `binding_iv_remove_drawable_ts` 或 `binding_image_native_drawable`。
 * </pre>
 *
 * @param drawable 前景 Drawable
 */
@BindingAdapter("binding_iv_drawable")
fun ImageView.bindingIVDrawable(drawable: Drawable?) {
    ImageViewUtils.setImageDrawable(this, drawable)
}

/**
 * 通过数据绑定按资源 ID 设置前景图。
 * <pre>
 *     布局属性 `binding_iv_resource`；`null` 时不修改；委托 [ImageViewUtils.setImageResource]。
 * </pre>
 *
 * @param resId drawable 资源 ID
 */
@BindingAdapter("binding_iv_resource")
fun ImageView.bindingIVResource(@DrawableRes resId: Int?) {
    if (resId == null) return
    ImageViewUtils.setImageResource(this, resId)
}

/**
 * 通过数据绑定按资源 ID 设置 View 背景（非前景 `src`）。
 * <pre>
 *     布局属性 `binding_iv_background_resource`；`null` 不修改；委托 [ImageViewUtils.setBackgroundResources]。
 *     与 `binding_iv_resource`（前景图）区分；通用 View 背景亦可使用 `binding_view_background_resource`。
 * </pre>
 *
 * @param resId 背景 drawable 资源 ID
 */
@BindingAdapter("binding_iv_background_resource")
fun ImageView.bindingIVBackgroundResource(@DrawableRes resId: Int?) {
    if (resId == null) return
    ImageViewUtils.setBackgroundResources(resId, this)
}

/**
 * 通过数据绑定设置前景 [Matrix]。
 * <pre>
 *     布局属性 `binding_iv_image_matrix`；`null` 时不修改；委托 [ImageViewUtils.setImageMatrix]。
 * </pre>
 *
 * @param matrix 变换矩阵
 */
@BindingAdapter("binding_iv_image_matrix")
fun ImageView.bindingIVImageMatrix(matrix: Matrix?) {
    if (matrix == null) return
    ImageViewUtils.setImageMatrix(this, matrix)
}

// =======
// = 着色 =
// =======

/**
 * 通过数据绑定设置前景着色颜色。
 * <pre>
 *     布局属性 `binding_iv_image_tint_list`；API 21 以下忽略；`null` 时不修改；
 *     委托 [ImageViewUtils.setImageTintList]。
 * </pre>
 *
 * @param tint 颜色状态列表
 */
@BindingAdapter("binding_iv_image_tint_list")
fun ImageView.bindingIVImageTintList(tint: ColorStateList?) {
    if (tint == null) return
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return
    ImageViewUtils.setImageTintList(this, tint)
}

/**
 * 通过数据绑定设置前景着色模式。
 * <pre>
 *     布局属性 `binding_iv_image_tint_mode`；API 21 以下忽略；`null` 时不修改；
 *     委托 [ImageViewUtils.setImageTintMode]。
 * </pre>
 *
 * @param tintMode [PorterDuff.Mode]
 */
@BindingAdapter("binding_iv_image_tint_mode")
fun ImageView.bindingIVImageTintMode(tintMode: PorterDuff.Mode?) {
    if (tintMode == null) return
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return
    ImageViewUtils.setImageTintMode(this, tintMode)
}

// ==========
// = 缩放类型 =
// ==========

/**
 * 通过数据绑定设置缩放类型。
 * <pre>
 *     布局属性 `binding_iv_scale_type`；`null` 时不修改；委托 [ImageViewUtils.setScaleType]。
 * </pre>
 *
 * @param scaleType [ImageView.ScaleType]
 */
@BindingAdapter("binding_iv_scale_type")
fun ImageView.bindingIVScaleType(scaleType: ImageView.ScaleType?) {
    if (scaleType == null) return
    ImageViewUtils.setScaleType(this, scaleType)
}

// ==========
// = 移除前景 =
// ==========

/**
 * 通过数据绑定以时间戳移除前景位图。
 * <pre>
 *     布局属性 `binding_iv_remove_bitmap_ts`；判定同 [qualifiesBindingAction]；委托 [ImageViewUtils.removeImageBitmap]。
 *     与 `binding_iv_bitmap`、`binding_image_native_bitmap` 等可并存；每次正时间戳触发一次清空。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_iv_remove_bitmap_ts")
fun ImageView.bindingIVRemoveBitmapTs(timestamp: Long?) {
    if (!timestamp.qualifiesBindingAction()) return
    ImageViewUtils.removeImageBitmap(this)
}

/**
 * 通过数据绑定以时间戳移除前景 Drawable。
 * <pre>
 *     布局属性 `binding_iv_remove_drawable_ts`；判定同 [qualifiesBindingAction]；委托 [ImageViewUtils.removeImageDrawable]。
 *     与 `binding_iv_drawable`、`binding_image_native_drawable` 等可并存。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_iv_remove_drawable_ts")
fun ImageView.bindingIVRemoveDrawableTs(timestamp: Long?) {
    if (!timestamp.qualifiesBindingAction()) return
    ImageViewUtils.removeImageDrawable(this)
}