package afkt.project.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.List;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.base.config.PathConfig;
import afkt.project.model.bean.AdapterBean;
import afkt.project.ui.widget.BaseTextView;
import butterknife.BindView;
import dev.utils.app.CapturePictureUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.helper.ViewHelper;
import dev.utils.app.image.ImageUtils;

/**
 * detail: CapturePictureUtils ListView 截图
 * @author Ttt
 */
public class CapturePictureListActivity extends BaseToolbarActivity {

    // = View =
    @BindView(R.id.vid_acpl_list)
    ListView vid_acpl_list;

    @Override
    public int getLayoutId() {
        return R.layout.activity_capture_picture_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 截图按钮
        BaseTextView baseTextView = new BaseTextView(this);
        ViewHelper.get().setText(baseTextView, "截图").setBold(baseTextView)
                .setTextColor(baseTextView, ResourceUtils.getColor(R.color.white))
                .setTextSizeBySp(baseTextView, 15.0f)
                .setPaddingLeft(baseTextView, 30)
                .setPaddingRight(baseTextView, 30)
                .setOnClicks(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String filePath = PathConfig.SDP_DOWN_IMAGE_PATH;
                        String fileName = "list.jpg";

                        Bitmap bitmap = CapturePictureUtils.snapshotByListView(vid_acpl_list);
                        boolean result = ImageUtils.saveBitmapToSDCardJPEG(bitmap, filePath + fileName);
                        showToast(result, "保存成功\n" + (filePath + fileName), "保存失败");
                    }
                }, baseTextView);
        vid_bt_toolbar.addView(baseTextView);
    }

    @Override
    public void initValues() {
        super.initValues();

        List<AdapterBean> lists = AdapterBean.newAdapterBeanList(15);
        // 设置适配器
        vid_acpl_list.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return lists.size();
            }

            @Override
            public AdapterBean getItem(int position) {
                return lists.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                AdapterBean adapterBean = getItem(position);
                // 初始化 View 设置 TextView
                View view = ViewUtils.inflate(CapturePictureListActivity.this, R.layout.adapter_capture_picture);
                ViewHelper.get().setText(ViewUtils.findViewById(view, R.id.vid_acp_title_tv), adapterBean.title)
                        .setText(ViewUtils.findViewById(view, R.id.vid_acp_content_tv), adapterBean.content);
                return view;
            }
        });
    }
}