package afkt.project.ui.activity;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import java.io.Serializable;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.BaseViewRecyclerviewBinding;
import afkt.project.model.item.ButtonList;
import afkt.project.model.item.ButtonValue;
import afkt.project.ui.adapter.ButtonAdapter;
import dev.utils.app.PathUtils;
import dev.utils.app.cache.DevCache;
import dev.utils.app.toast.ToastTintUtils;
import utils_use.cache.CacheUse;

/**
 * detail: DevCache 缓存工具类
 * @author Ttt
 * <pre>
 *     {@link CacheUse}
 * </pre>
 */
public class CacheActivity
        extends BaseActivity<BaseViewRecyclerviewBinding> {

    @Override
    public int baseLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    public void initValue() {
        super.initValue();

        // 初始化布局管理器、适配器
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonList.getCacheButtonValues());
        binding.vidBvrRecy.setAdapter(buttonAdapter);
        buttonAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(
                    BaseQuickAdapter adapter,
                    View view,
                    int position
            ) {
                // 获取字符串
                String str;

                ButtonValue buttonValue = buttonAdapter.getItem(position);
                switch (buttonValue.type) {
                    case ButtonValue.BTN_CACHE_STRING:
                        DevCache.newCache().put("str", "这是字符串");
                        showToast(true, "存储字符串成功");
                        break;
                    case ButtonValue.BTN_CACHE_STRING_TIME:
                        DevCache.newCache().put("str", "这是有效期 3 秒字符串", 3);
                        showToast(true, "存储有效期字符串成功");
                        break;
                    case ButtonValue.BTN_CACHE_STRING_GET:
                        str = DevCache.newCache().getAsString("str");
                        showToast(str != null, str, "未存储 key 为 str 的字符串");
                        break;
                    case ButtonValue.BTN_CACHE_BEAN:
                        DevCache.newCache().put("bean", new CacheActivity.CacheVo("这是实体类"));
                        showToast(true, "存储实体类成功");
                        break;
                    case ButtonValue.BTN_CACHE_BEAN_TIME:
                        DevCache.newCache().put("bean", new CacheActivity.CacheVo("这是有效期 3 秒实体类"), 3);
                        showToast(true, "存储有效期实体类成功");
                        break;
                    case ButtonValue.BTN_CACHE_BEAN_GET:
                        Object object = DevCache.newCache().getAsObject("bean");
                        str = (object != null) ? ((CacheVo) object).toString() : null;
                        showToast(object != null, str, "未存储 key 为 bean 的实体类");
                        break;
                    case ButtonValue.BTN_CACHE_FILE:
                        // 保存到指定文件夹下
                        DevCache.newCache(PathUtils.getSDCard().getSDCardFile("DevCache")).put("fileStr", "这是指定位置字符串");
                        showToast(true, "存储到指定位置成功");
                        break;
                    case ButtonValue.BTN_CACHE_FILE_GET:
                        str = DevCache.newCache(PathUtils.getSDCard().getSDCardFile("DevCache")).getAsString("fileStr");
                        showToast(str != null, str, "未存储 key 为 fileStr 的字符串");
                        break;
                    case ButtonValue.BTN_CACHE_CLEAR:
                        DevCache.newCache().clear();
                        DevCache.newCache(PathUtils.getSDCard().getSDCardPath("DevCache")).clear();
                        showToast(true, "清除全部数据成功");
                        break;
                    default:
                        ToastTintUtils.warning("未处理 " + buttonValue.text + " 事件");
                        break;
                }
            }
        });
    }

    /**
     * detail: 缓存实体类
     * @author Ttt
     */
    static class CacheVo
            implements Serializable {

        String name;

        long time;

        public CacheVo(String name) {
            this.name = name;
            this.time = System.currentTimeMillis();
        }

        public CacheVo(
                String name,
                long time
        ) {
            this.name = name;
            this.time = time;
        }

        @Override
        public String toString() {
            return "name: " + name + ", time: " + time;
        }
    }
}