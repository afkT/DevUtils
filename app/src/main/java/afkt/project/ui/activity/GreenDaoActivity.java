package afkt.project.ui.activity;

import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import butterknife.BindView;

/**
 * detail: GreenDao 使用
 * @author Ttt
 * <pre>
 *     官方文档
 *     @see <a href="https://greenrobot.org/greendao/documentation/modelling-entities"/>
 *     SQL 语句写到累了? 试试 GreenDAO
 *     @see <a href="https://www.jianshu.com/p/11bdd9d761e6"/>
 *     Android GreenDao 数据库
 *     @see <a href="https://www.jianshu.com/p/26c60d59e76d"/>
 *     Android ORM 框架 : GreenDao 使用详解 ( 进阶篇 )
 *     @see <a href="https://blog.csdn.net/speedystone/article/details/74193053"/>
 * </pre>
 */
public class GreenDaoActivity extends BaseToolbarActivity {

    // = View =
    @BindView(R.id.vid_agd_refresh)
    SmartRefreshLayout vid_agd_refresh;
    @BindView(R.id.vid_agd_recy)
    RecyclerView       vid_agd_recy;

    @Override
    public int getLayoutId() {
        return R.layout.activity_green_dao;
    }

    @Override
    public void initValues() {
        super.initValues();
    }

//    @OnClick({R.id.vid_avp_allow_btn, R.id.vid_avp_ban_btn})
//    @Override
//    public void onClick(View v) {
//        super.onClick(v);
//        switch (v.getId()) {
//            case R.id.vid_avp_allow_btn:
//                vid_avp_viewpager.setSlide(true);
//                showToast(true, "已允许滑动");
//                break;
//            case R.id.vid_avp_ban_btn:
//                vid_avp_viewpager.setSlide(false);
//                showToast(false, "已禁止滑动");
//                break;
//        }
//    }
}