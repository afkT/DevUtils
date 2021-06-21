package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.ActivityUiEffectBinding
import afkt.project.model.item.TabItem
import afkt.project.util.assist.TabLayoutAssist
import afkt.project.util.assist.TabLayoutAssist.TabChangeListener
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import dev.base.widget.BaseTextView
import dev.engine.log.DevLogEngine
import dev.utils.app.*
import dev.utils.app.helper.QuickHelper
import dev.utils.app.helper.ViewHelper
import dev.utils.common.ArrayUtils
import java.util.*

/**
 * detail: 常见 UI、GradientDrawable 效果等
 * @author Ttt
 */
@Route(path = RouterPath.UIEffectActivity_PATH)
class UIEffectActivity : BaseActivity<ActivityUiEffectBinding>() {

    // 当前选中的索引
    private var selectTabIndex = -1

    override fun baseLayoutId(): Int = R.layout.activity_ui_effect

    override fun initValue() {
        super.initValue()

        // 默认选中
        ViewHelper.get()
            .setSelected(true, binding.vidAue10Tv)
            .setSelected(false, binding.vidAue11Tv)
        changeTab1(binding.vidAue20Tv, binding.vidAue21Tv)
        changeTab2(binding.vidAue31Tv, binding.vidAue30Tv)

        // 动态设置
        StateListUtils.newSelector(R.drawable.btn_pressed, R.drawable.btn_normal)
            .also { binding.vidAue40Btn.background = it }

        binding.vidAue40Btn.setTextColor(
            StateListUtils.createColorStateList(
                R.color.black, R.color.white
            )
        )

        // 默认就设置背景色
        ShapeUtils.newShape()
            .setCornerRadiusLeft(10.0f)
            .setColor(Color.BLACK)
            .drawable.also { binding.vidAue41Btn.background = it }

        // 设置点击效果
        val drawable1 = ShapeUtils.newShape(10f, ResourceUtils.getColor(R.color.black))
            .setStroke(5, ResourceUtils.getColor(R.color.darkorange)).drawable
        val drawable2 = ShapeUtils.newShape(10f, ResourceUtils.getColor(R.color.sky_blue))
            .setStroke(5, ResourceUtils.getColor(R.color.gray)).drawable

        StateListUtils.newSelector(drawable2, drawable1)
            .also { binding.vidAue41Btn.background = it }

        binding.vidAue41Btn.setTextColor(
            StateListUtils.createColorStateList(
                R.color.red, R.color.white
            )
        )

        // ==========
        // = 渐变效果 =
        // ==========

        ShapeUtils.newShape(
            GradientDrawable.Orientation.BR_TL,
            intArrayOf(Color.RED, Color.BLUE, Color.GREEN)
        ).setDrawable(binding.vidAue50View)

        val colors = IntArray(3)
        colors[0] = ContextCompat.getColor(this, R.color.black)
        colors[1] = ContextCompat.getColor(this, R.color.sky_blue)
        colors[2] = ContextCompat.getColor(this, R.color.orange)
        val drawable = ShapeUtils.newShape(GradientDrawable.Orientation.BR_TL, colors).drawable
//        drawable.gradientType = GradientDrawable.LINEAR_GRADIENT   // 线性渐变, 这是默认设置
//        drawable.gradientType = GradientDrawable.RADIAL_GRADIENT   // 放射性渐变, 以开始色为中心
        drawable.gradientType = GradientDrawable.SWEEP_GRADIENT     // 扫描线式的渐变
        binding.vidAue60View.background = drawable

        // ============================
        // = HorizontalScrollView Tab =
        // ============================

        // Tab 数据
        val listTabs: MutableList<TabItem> = ArrayList()
        listTabs.add(TabItem("全部", 0))
        listTabs.add(TabItem("待付款", 1))
        listTabs.add(TabItem("待发货", 2))
        listTabs.add(TabItem("待收货", 3))
        listTabs.add(TabItem("待评价", 4))
        listTabs.add(TabItem("已取消", 5))
        listTabs.add(TabItem("已完成", 6))
        listTabs.add(TabItem("已关闭", 7))
        listTabs.add(TabItem("售后", 8))

        // 循环添加 Tab
        for (i in 0 until listTabs.size) {
            val tabItem = listTabs[i]
            val view = QuickHelper.get(BaseTextView(this))
                .setText(tabItem.title)
                .setTextColor(ResourceUtils.getColor(R.color.black))
                .setPadding(30, 30, 30, 30)
                .setOnClicks {
                    ViewHelper.get()
                        .setBold(
                            ViewUtils.getChildAt(binding.vidAue70Linear, selectTabIndex),
                            false
                        )
                        .setTextColor(
                            ViewUtils.getChildAt(
                                binding.vidAue70Linear, selectTabIndex
                            ), ResourceUtils.getColor(R.color.black)
                        )
                        .setBold(ViewUtils.getChildAt(binding.vidAue70Linear, i), true)
                        .setTextColor(
                            ViewUtils.getChildAt(binding.vidAue70Linear, i),
                            ResourceUtils.getColor(R.color.red)
                        )
                    // 修改索引
                    selectTabIndex = i
                    // 滑动 Tab 处理
                    scrollTab(i)
                }.getView<View>()
            binding.vidAue70Linear.addView(view)
        }
        ViewUtils.getChildAt<View>(binding.vidAue70Linear).performClick()

        // =================
        // = TabLayout Tab =
        // =================

        TabLayoutAssist[binding.vidAue80Tab]?.apply {
            setListTabs(listTabs, object : TabChangeListener {
                override fun onTabChange(
                    tabItem: TabItem,
                    pos: Int
                ) {
                    DevLogEngine.getEngine().dTag(TAG, "TabItem: %s, pos: %s", tabItem.title, pos)
                    // 设置选中
                    setSelect(pos)
                }
            }).setSelect(tabCount - 1)
        }

        TabLayoutAssist[binding.vidAue90Tab]?.apply {
            setListTabs(ArrayUtils.asList(ArrayUtils.subArray(listTabs.toTypedArray(), 0, 3)),
                object : TabChangeListener {
                    override fun onTabChange(
                        tabItem: TabItem,
                        pos: Int
                    ) {
                        DevLogEngine.getEngine()
                            .dTag(TAG, "TabItem: %s, pos: %s", tabItem.title, pos)
                    }
                }
            ).setSelect(0)
        }
    }

    override fun initListener() {
        super.initListener()
        ListenerUtils.setOnClicks(
            this,
            binding.vidAue10Tv, binding.vidAue11Tv,
            binding.vidAue20Tv, binding.vidAue21Tv,
            binding.vidAue30Tv, binding.vidAue31Tv
        )
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.vid_aue_1_0_tv -> {
                ViewHelper.get()
                    .setSelected(true, binding.vidAue10Tv)
                    .setSelected(false, binding.vidAue11Tv)
            }
            R.id.vid_aue_1_1_tv -> {
                ViewHelper.get()
                    .setSelected(true, binding.vidAue11Tv)
                    .setSelected(false, binding.vidAue10Tv)
            }
            R.id.vid_aue_2_0_tv -> changeTab1(binding.vidAue20Tv, binding.vidAue21Tv)
            R.id.vid_aue_2_1_tv -> changeTab1(binding.vidAue21Tv, binding.vidAue20Tv)
            R.id.vid_aue_3_0_tv -> changeTab2(binding.vidAue30Tv, binding.vidAue31Tv)
            R.id.vid_aue_3_1_tv -> changeTab2(binding.vidAue31Tv, binding.vidAue30Tv)
        }
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 切换 Tab
     * @param clickTab   点击 Tab
     * @param unClickTab 未点击 Tab
     */
    private fun changeTab1(
        clickTab: BaseTextView,
        unClickTab: BaseTextView
    ) {
        // 判断点击的是左边还是右边
        if (clickTab.id == R.id.vid_aue_2_0_tv) { // 点击左边
            clickTab.setBackgroundResource(R.drawable.shape_tab_left_pressed)
            unClickTab.setBackgroundResource(R.drawable.shape_tab_right_selector)
        } else {
            clickTab.setBackgroundResource(R.drawable.shape_tab_right_pressed)
            unClickTab.setBackgroundResource(R.drawable.shape_tab_left_selector)
        }
        // = 字体效果 =
        // 选中变白色
        clickTab.setTextColor(resources.getColor(R.color.white))
        // 未选中增加点击效果
        unClickTab.setTextColor(ResourceUtils.getColorStateList(R.color.selector_tab_text_color))
    }

    /**
     * 切换 Tab
     * @param clickTab   点击 Tab
     * @param unClickTab 未点击 Tab
     */
    private fun changeTab2(
        clickTab: BaseTextView,
        unClickTab: BaseTextView
    ) {
        // 判断点击的是左边还是右边
        if (clickTab.id == R.id.vid_aue_3_0_tv) { // 点击左边
            // 设置选中颜色
            ShapeUtils.newShape().setCornerRadiusLeft(10f)
                .setColor(ResourceUtils.getColor(R.color.sky_blue))
                .setDrawable(clickTab)

            // 设置未选中颜色
            val drawable1 = ShapeUtils.newShape()
                .setCornerRadiusRight(10f)
                .setColor(ResourceUtils.getColor(R.color.sky_blue))
                .drawable
            val drawable2 = ShapeUtils.newShape()
                .setCornerRadiusRight(10f)
                .setColor(ResourceUtils.getColor(R.color.color_33))
                .drawable
            unClickTab.background = StateListUtils.newSelector(drawable1, drawable2)
        } else {
            // 设置选中颜色
            ShapeUtils.newShape().setCornerRadiusRight(10f)
                .setColor(ResourceUtils.getColor(R.color.sky_blue))
                .setDrawable(clickTab)

            // 设置未选中颜色
            val drawable1 = ShapeUtils.newShape()
                .setCornerRadiusLeft(10f)
                .setColor(ResourceUtils.getColor(R.color.sky_blue))
                .drawable
            val drawable2 = ShapeUtils.newShape()
                .setCornerRadiusLeft(10f)
                .setColor(ResourceUtils.getColor(R.color.color_33))
                .drawable
            unClickTab.background = StateListUtils.newSelector(drawable1, drawable2)
        }
        // = 字体效果 =
        // 选中变白色
        clickTab.setTextColor(resources.getColor(R.color.white))
        // 设置未选中颜色
        unClickTab.setTextColor(StateListUtils.createColorStateList(R.color.white, R.color.red))
    }

    /**
     * 滑动 Tab 处理
     * @param position 需要滑动的索引
     */
    private fun scrollTab(position: Int) {
        // 延时移动
        HandlerUtils.postRunnable({
            var x = 0
            // 循环遍历
            for (i in 1 until position) {
                binding.vidAue70Linear.getChildAt(i)?.apply {
                    // 累加宽度
                    x += width
                }
            }
            // 开始移动位置
            binding.vidAue70Scroll.scrollTo(x, 0)
        }, 50)
    }
}