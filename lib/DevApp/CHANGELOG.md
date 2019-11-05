Change Log
==========

Version 1.8.4 *(2019-11-05)*
----------------------------
 
* `[Add]` FileUtils#isImageFormats、isAudioFormats、isVideoFormats、isFileFormats

* `[Add]` ViewUtils#getWidthHeight、getNextFocusUpId、getNextFocusRightId、getNextFocusLeftId、getNextFocusDownId、getNextFocusForwardId、isScrollContainer、getChildCount、getRotation、getRotationX、getRotationY、getScaleX、getScaleY、getTextAlignment、getTextDirection、getPivotX、getPivotY、getTranslationX、getTranslationY、getLayerType、isFocusable、isSelected、isEnabled、isClickable、isLongClickable、findFocus、isFocused、hasFocus、hasFocusable、isFocusableInTouchMode、setFocusableInTouchMode、scrollTo、scrollBy、setScrollX、setScrollY、getScrollX、getScrollY、isHorizontalScrollBarEnabled、setHorizontalScrollBarEnabled、isVerticalScrollBarEnabled、setVerticalScrollBarEnabled、setDescendantFocusability、setOverScrollMode

* `[Add]` TextViewUtils#getTypeface、getLetterSpacing、getLineSpacingExtra、getLineSpacingMultiplier、getTextScaleX、getIncludeFontPadding、getInputType、getImeOptions、getMaxLines、getMinLines、getMaxEms、getMinEms、getEllipsize、getAutoLinkMask、getGravity、clearFocus、requestFocus、requestLayout、getTransformationMethod、setTransformationMethod

* `[Add]` EditTextUtils#isCursorVisible、getInputType、getImeOptions、getTransformationMethod、setTransformationMethod

* `[Add]` AnimationUtils#setAnimationListener

* `[Add]` ListViewUtils - 列表 View 相关工具类(支持快捷滑动到指定索引、指定 x、y轴坐标、回到顶部、底部等)

* `[Add]` DevHelper、ViewHelper 快捷链式调用 Helper 类

Version 1.8.3 *(2019-10-31)*
----------------------------
 
* `[Add]` ArrayUtils#getMinimum、getMaximum、getMinimumIndex、getMaximumIndex、sumarray

* `[Add]` CollectionUtils#getMinimum、getMaximum、、getMinimumIndex、getMaximumIndex、sumlist

* `[Add]` AnimationUtils#setAnimation、getAnimation、clearAnimation、startAnimation、cancel

* `[Add]` ViewUtils#setAnimation、getAnimation、clearAnimation、startAnimation、cancel、measureView、setWidthHeight、setWidth、setHeight、addRule、removeRule、getRule、addRules、removeRules

* `[Add]` AppUtils#startActivity、startActivityForResult

* `[Add]` IntentUtils#getLaunchAppInstallPermissionSettingsIntent、getLaunchAppNotificationSettingsIntent

* `[Add]` PermissionUtils#canRequestPackageInstalls

* `[Add]` NotificationUtils#isNotificationEnabled

* `[Add]` CapturePictureUtils 截图工具类 ( 支持 View、Activity、FrameLayout、RelativeLayout、LinearLayout、ListView、GridView、ScrollView、HorizontalScrollView、NestedScrollView、WebView、RecyclerView(GridLayoutManager、LinearLayoutManager、StaggeredGridLayoutManager) )

Version 1.8.2 *(2019-10-18)*
----------------------------
 
 * `[Add]` TextViewUtils#setMinLines、setMaxEms、setMinEms、setEms、setMaxLength、setMaxLengthAndText、setInputType、setImeOptions

 * `[Add]` EditTextUtils#setInputType、setImeOptions

 * `[Add]` JSONObjectUtils#isJSON

 * `[Add]` ViewUtils#setLayerType、setAllCaps、setAlpha、getAlpha、setScrollContainer、setNextFocusForwardId、setNextFocusDownId、setNextFocusLeftId、setNextFocusRightId、setNextFocusUpId、setRotation、setRotationX、setRotationY、setScaleX、setScaleY、setTextAlignment、setTextDirection、setPivotX、setPivotY、setTranslationX、setTranslationY

Version 1.8.1 *(2019-10-13)*
----------------------------
 
 * `[Add]` EditTextUtils#addTextChangedListener、removeTextChangedListener、setTexts

 * `[Add]` TextViewUtils#getHint、getHints、getHintTextColors、setHintTextColor、setHintTextColors、getTextColors、setTextColor、setTextColors、setGravity、setHint、setAutoLinkMask、setEllipsize、setMaxLines、setLines

 * `[Add]` ViewUtils#getMinimumHeight、setMinimumHeight、getMinimumWidth、setMinimumWidth

 * `[Add]` ImageViewUtils#getAdjustViewBounds、setAdjustViewBounds、getMaxHeight、setMaxHeight、getMaxWidth、setMaxWidth

Version 1.8.0 *(2019-10-09)*
----------------------------
 
 * `[Update]` TextViewUtils#calcTextWidth 使用二分法优化处理
 
 * `[Add]` TextViewUtils#calcTextLine、TextViewUtils#getPaint、TextViewUtils#getTextWidth
 
 * `[Add]` DialogUtils#dismiss(DialogFragment)
 
 * `[Add]` ViewUtils#inflate
 
 * `[Add]` NumberUtils#getMultiple、getMultipleI、getMultipleD、getMultipleL、getMultipleF

Version 1.7.9 *(2019-09-19)*
----------------------------
 
 * `[Update]` compileSdkVersion 29 Android Q
 
 * `[Update]` AppCommonUtils#convertSDKVersion
 
 * `[Update]` ImageUtils#getImageType、ImageUtils#isImage modify to isImageFormats
 
 * `[Update]` 修改部分方法 void 返回值 (返回当前对象, 方便链式调用)
 
 * `[Add]` AppCommonUtils#isQ
 
 * `[Add]` BitmapUtils#isImage
 
 * `[Add]` ListenerUtils#setOnLongClicks
 
 * `[Add]` ImageUtils#isICO、ImageUtils#isTIFF

 * `[Add]` ViewUtils#getTag、setTag


Version 1.7.8 *(2019-09-12)*
----------------------------
 
 * `[Add]` ImageViewUtils#setBackgroundResources
 
 * `[Add]` ViewUtils#getParent
 
 * `[Add]` ConvertUtils#convert

 * `[Fix]` DialogUtils#showDialog、closeDialog try catch
 

Version 1.7.7 *(2019-08-25)*
----------------------------

 * `[New]` Support for AndroidX

 * `[Add]` DevCommonUtils#subSetSymbol

 * `[Add]` ScreenUtils#setWindowSecure
 
 * `[Add]` ViewUtils#set/getMargin、set/getPadding, ViewUtils#set/getLayoutParams
 
 * `[Add]` AndroidManifest.xml FileProvider config
 
 * `[Update]` Update AppUtils、IntentUtils、UriUtils 使用 FileProvider authority 处理
 
 * `[Fix]` Reflect2Utils#getDeclaredFieldParent fieldNumber param 判断处理


Version 1.7.6 *(2019-08-02)*
----------------------------

 * `[New]` SpannableStringUtils

 * `[Add]` ViewUtils#set/getCompoundDrawables、set/getCompoundDrawablePadding
 
 * `[Add]` ImageUtils#setBounds


Version 1.0.0~1.7.5 *(2019-07-26)*
----------------------------

 * 整个工具类 review code, 并规范代码风格、检测注释、代码间距等

