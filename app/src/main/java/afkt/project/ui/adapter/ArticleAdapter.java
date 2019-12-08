package afkt.project.ui.adapter;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import afkt.project.R;
import afkt.project.model.bean.ArticleBean;
import dev.other.GlideUtils;
import dev.utils.app.AppUtils;
import dev.utils.app.ListenerUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.common.DateUtils;
import dev.utils.common.DevCommonUtils;

/**
 * detail: 文章 Adapter
 * @author Ttt
 */
public class ArticleAdapter extends BaseQuickAdapter<ArticleBean.DataBean.DatasBean, BaseViewHolder> {

    // 圆角 RequestOptions
    private RequestOptions requestOptions;

    public ArticleAdapter() {
        super(R.layout.adapter_article, new ArrayList<>());
        // 获取默认 RequestOptions
        requestOptions = GlideUtils.defaultOptions();
        // 设置圆角, 使用 RoundedCorners 图片不会闪烁
        requestOptions.transform(new RoundedCorners((int) ResourceUtils.getDimension(R.dimen.un_radius)));
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleBean.DataBean.DatasBean item) {
        // 当前索引
        int position = helper.getLayoutPosition();
        // 标题
        helper.setText(R.id.vid_aa_title_tv, item.title);
        // 时间
        helper.setText(R.id.vid_aa_time_tv, DevCommonUtils.toCheckValue(item.niceShareDate, item.niceDate));
        // 随机图片
        GlideUtils.with().displayImage("https://picsum.photos/2" + DateUtils.convertTime(position), helper.getView(R.id.vid_aa_pic_igview), requestOptions);
        // 绑定点击事件
        ListenerUtils.setOnClicks(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = item.link;
                if (!TextUtils.isEmpty(link)) {
                    Uri uri = Uri.parse(link);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    AppUtils.startActivity(intent);
                }
            }
        }, helper.getView(R.id.vid_aa_cardview));
    }
}
