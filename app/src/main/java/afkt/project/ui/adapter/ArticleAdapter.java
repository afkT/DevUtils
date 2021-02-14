package afkt.project.ui.adapter;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;

import afkt.project.R;
import afkt.project.model.bean.ArticleBean;
import afkt.project.util.ProjectUtils;
import dev.engine.image.DevImageEngine;
import dev.utils.app.AppUtils;
import dev.utils.app.ListenerUtils;
import dev.utils.common.NumberUtils;
import dev.utils.common.StringUtils;

/**
 * detail: 文章 Adapter
 * @author Ttt
 */
public class ArticleAdapter
        extends BaseQuickAdapter<ArticleBean.DataBean.DatasBean, BaseViewHolder> {

    public ArticleAdapter() {
        super(R.layout.adapter_article, new ArrayList<>());
    }

    @Override
    protected void convert(
            BaseViewHolder helper,
            ArticleBean.DataBean.DatasBean item
    ) {
        // 当前索引
        int position = helper.getLayoutPosition();
        // 标题
        helper.setText(R.id.vid_aa_title_tv, item.title);
        // 时间
        helper.setText(R.id.vid_aa_time_tv, StringUtils.checkValue(item.niceShareDate, item.niceDate));
        // 随机图片
        DevImageEngine.getEngine().display(
                helper.getView(R.id.vid_aa_pic_igview),
                "https://picsum.photos/2" + NumberUtils.addZero(position),
                ProjectUtils.getRoundConfig3()
        );
        // 绑定点击事件
        ListenerUtils.setOnClicks(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = item.link;
                if (!TextUtils.isEmpty(link)) {
                    Uri    uri    = Uri.parse(link);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    AppUtils.startActivity(intent);
                }
            }
        }, helper.getView(R.id.vid_aa_cardview));
    }
}