package afkt.project.ui.adapter;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import afkt.project.R;
import afkt.project.model.bean.ArticleBean;
import dev.other.GlideUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.ViewUtils;

/**
 * detail: 文章 Adapter
 * @author Ttt
 */
public class ArticleAdapter extends BaseQuickAdapter<ArticleBean.DataBean.DatasBean, BaseViewHolder> {

    // 圆角 RequestOptions
    private RequestOptions requestOptions;

    public ArticleAdapter() {
        super(R.layout.adapter_item_edits, new ArrayList<>());
        // 获取默认 RequestOptions
        requestOptions = GlideUtils.defaultOptions();
        // 设置圆角, 使用 RoundedCorners 图片不会闪烁
        requestOptions.transform(new RoundedCorners((int) ResourceUtils.getDimension(R.dimen.un_radius)));
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleBean.DataBean.DatasBean item) {
        // 当前索引
        int position = helper.getLayoutPosition();
        // 判断是否显示边距
        ViewUtils.setVisibility(position == 0, helper.getView(R.id.vid_aie_line));
    }
}
