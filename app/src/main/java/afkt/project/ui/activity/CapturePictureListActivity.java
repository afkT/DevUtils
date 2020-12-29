package afkt.project.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.base.config.PathConfig;
import afkt.project.databinding.ActivityCapturePictureListBinding;
import afkt.project.model.bean.AdapterBean;
import dev.base.widget.BaseTextView;
import dev.utils.app.CapturePictureUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.helper.QuickHelper;
import dev.utils.app.helper.ViewHelper;
import dev.utils.app.image.ImageUtils;

/**
 * detail: CapturePictureUtils ListView 截图
 * @author Ttt
 */
public class CapturePictureListActivity
        extends BaseActivity<ActivityCapturePictureListBinding> {

    @Override
    public int baseLayoutId() {
        return R.layout.activity_capture_picture_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 截图按钮
        View view = QuickHelper.get(new BaseTextView(this))
                .setText("截图")
                .setBold()
                .setTextColor(ResourceUtils.getColor(R.color.white))
                .setTextSizeBySp(15.0f)
                .setPaddingLeft(30)
                .setPaddingRight(30)
                .setOnClicks(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String filePath = PathConfig.AEP_DOWN_IMAGE_PATH;
                        String fileName = "list.jpg";

                        Bitmap  bitmap = CapturePictureUtils.snapshotByListView(binding.vidAcpList);
                        boolean result = ImageUtils.saveBitmapToSDCardJPEG(bitmap, filePath + fileName);
                        showToast(result, "保存成功\n" + (filePath + fileName), "保存失败");
                    }
                }).getView();
        getToolbar().addView(view);
    }

    @Override
    public void initValue() {
        super.initValue();

        List<AdapterBean> lists = AdapterBean.newAdapterBeanList(15);
        // 设置适配器
        binding.vidAcpList.setAdapter(new BaseAdapter() {
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
            public View getView(
                    int position,
                    View convertView,
                    ViewGroup parent
            ) {
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