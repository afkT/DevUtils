package afkt.project.util;

import android.view.LayoutInflater;
import android.view.View;

import afkt.project.R;
import dev.utils.app.ListenerUtils;
import dev.widget.assist.ViewAssist;

/**
 * detail: ViewAssist Adapter 工具类
 * @author Ttt
 */
public final class ViewAssistUtils {

    private ViewAssistUtils() {
    }

    /**
     * 注册 Recycler Loading type
     * @param viewAssist {@link ViewAssist}
     * @param listener   点击事件
     */
    public static void registerRecyclerLoading(ViewAssist viewAssist, View.OnClickListener listener) {
        if (viewAssist != null) {
            viewAssist.register(ViewAssist.TYPE_ING, ViewAssistUtils.getLoadingAdapter());
            viewAssist.register(ViewAssist.TYPE_FAILED, ViewAssistUtils.getFailedAdapter(listener));
            viewAssist.register(ViewAssist.TYPE_SUCCESS, ViewAssistUtils.getSuccessAdapter());
        }
    }

    /**
     * 获取加载中 Adapter
     * @return 加载中 Adapter
     */
    public static ViewAssist.Adapter getLoadingAdapter() {
        return new ViewAssist.Adapter() {
            @Override
            public View onCreateView(ViewAssist assist, LayoutInflater inflater) {
                return inflater.inflate(R.layout.view_assist_recy_loading, null);
            }

            @Override
            public void onBindView(ViewAssist assist, View view, int type) {
            }
        };
    }

    /**
     * 获取加载成功 Adapter
     * @return 加载成功 Adapter
     */
    public static ViewAssist.Adapter getSuccessAdapter() {
        return new ViewAssist.Adapter() {
            @Override
            public View onCreateView(ViewAssist assist, LayoutInflater inflater) {
                return null;
            }

            @Override
            public void onBindView(ViewAssist assist, View view, int type) {
                assist.gone(); // 可以设置渐变动画, 并在结束时隐藏根布局 -> assist.gone()
            }
        };
    }

    /**
     * 获取加载失败 Adapter
     * @param listener 点击事件
     * @return 加载失败 Adapter
     */
    public static ViewAssist.Adapter getFailedAdapter(View.OnClickListener listener) {
        return new ViewAssist.Adapter() {
            @Override
            public View onCreateView(ViewAssist assist, LayoutInflater inflater) {
                return inflater.inflate(R.layout.view_assist_recy_failed, null);
            }

            @Override
            public void onBindView(ViewAssist assist, View view, int type) {
                ListenerUtils.setOnClicks(listener, view);
            }
        };
    }
}
