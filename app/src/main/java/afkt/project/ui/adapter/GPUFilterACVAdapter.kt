package afkt.project.ui.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;

import java.util.List;

import afkt.project.R;
import afkt.project.model.bean.ACVFileBean;
import dev.base.widget.BaseTextView;
import dev.utils.app.ResourceUtils;
import dev.utils.app.SizeUtils;
import dev.utils.app.helper.QuickHelper;

/**
 * detail: GPU ACV 文件滤镜效果适配器
 * @author Ttt
 */
public class GPUFilterACVAdapter
        extends BaseAdapter {

    // Context
    Context           context;
    // ACV 文件集合
    List<ACVFileBean> listACVFiles;
    // 当前选中索引
    int               selectPosition = -1;

    public GPUFilterACVAdapter(
            Context context,
            List<ACVFileBean> listACVFiles
    ) {
        this.context = context;
        this.listACVFiles = listACVFiles;
    }

    @Override
    public int getCount() {
        return listACVFiles.size();
    }

    @Override
    public ACVFileBean getItem(int position) {
        return listACVFiles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(
            int position,
            View convertView,
            ViewGroup parent
    ) {
        return createTextView(position);
    }

    /**
     * 设置选中索引
     * @param selectPosition 选中索引
     */
    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

    /**
     * 创建 TextView
     * @param position 对应索引
     * @return {@link BaseTextView}
     */
    private BaseTextView createTextView(int position) {
        ACVFileBean acvFileBean = getItem(position);

        boolean isSelect = (selectPosition == position);
        int     width    = SizeUtils.dipConvertPx(100f);
        Gallery.LayoutParams layoutParams = new Gallery.LayoutParams(
                width, Gallery.LayoutParams.MATCH_PARENT
        );
        // 初始化 View
        return QuickHelper.get(new BaseTextView(context))
                .setText(acvFileBean.acvName)
                .setBold(isSelect)
                .setTextGravity(Gravity.CENTER)
                .setTextColor(ResourceUtils.getColor(isSelect ? R.color.red : R.color.black))
                .setTextSizeBySp(isSelect ? 18.0f : 13.0f)
                .setLayoutParams(layoutParams)
                .getView();
    }
}