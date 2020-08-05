package afkt.project.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.AdapterView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.model.item.FilterItem;
import afkt.project.ui.adapter.GPUFilterAdapter;
import afkt.project.ui.widget.BaseImageView;
import afkt.project.util.GPUFilterUtils;
import butterknife.BindView;
import butterknife.OnClick;
import dev.other.picture.PictureSelectorUtils;
import dev.utils.app.HandlerUtils;
import dev.utils.app.image.ImageUtils;
import dev.utils.app.logger.DevLogger;
import dev.widget.custom.CustomGallery;

/**
 * detail: GPU 滤镜效果
 * @author Ttt
 */
public class GPUFilterActivity extends BaseToolbarActivity {

    // = View =
    @BindView(R.id.vid_agf_gallery)
    CustomGallery vid_agf_gallery;
    @BindView(R.id.vid_agf_igview)
    BaseImageView vid_agf_igview;
    // 适配器
    GPUFilterAdapter gpuFilterAdapter;
    // 图片 Bitmap
    Bitmap           selectBitmap;
    // 滤镜线程
    static Runnable filterThread;

    @Override
    public int getLayoutId() {
        return R.layout.activity_gpu_filter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        filterThread = null;
    }

    @Override
    public void initValues() {
        super.initValues();

        // 设置滤镜线程
        filterThread = new Runnable() {
            @Override
            public void run() {
                setFilter();
            }
        };

        // 设置适配器
        vid_agf_gallery.setAdapter(gpuFilterAdapter = new GPUFilterAdapter(this));
        vid_agf_gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gpuFilterAdapter.setSelectPosition(position);
                // 延迟一会进行滤镜
                HandlerUtils.removeRunnable(filterThread);
                HandlerUtils.postRunnable(filterThread, 500);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // 默认选中第一个
        vid_agf_gallery.setSelection(0);
    }

    @OnClick({R.id.vid_agf_select_btn})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.vid_agf_select_btn:
                // 初始化图片配置
                PictureSelectorUtils.PicConfig picConfig = new PictureSelectorUtils.PicConfig()
                        .setCompress(false).setMaxSelectNum(1).setCrop(false).setMimeType(PictureMimeType.ofImage())
                        .setCamera(true).setGif(false);
                // 打开图片选择器
                PictureSelectorUtils.openGallery(PictureSelector.create(this), picConfig);
                break;
        }
    }

    // ============
    // = 图片回传 =
    // ============

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 判断是否属于图片选择
        if (resultCode == Activity.RESULT_OK && data != null) {
            LocalMedia localMedia = PictureSelectorUtils.getSingleMedia(data);
            // 获取图片地址
            String imgPath = PictureSelectorUtils.getLocalMediaPath(localMedia, true);
            // 获取图片 Bitmap
            selectBitmap = ImageUtils.decodeFile(imgPath);
            // 设置图片滤镜
            setFilter();
        }
    }

    // ============
    // = 滤镜处理 =
    // ============

    /**
     * 设置滤镜效果
     */
    private void setFilter() {
        try {
            if (selectBitmap == null) return;
            // 获取选中的滤镜
            int position = vid_agf_gallery.getSelectedItemPosition();
            // 获取滤镜 Item
            FilterItem filterItem = gpuFilterAdapter.getItem(position);
            // 设置滤镜效果
            Bitmap bitmapFilter = GPUFilterUtils.getFilterBitmap(selectBitmap, FilterItem.createFilterForType(filterItem.filterType));
            vid_agf_igview.setImageBitmap(bitmapFilter);
        } catch (Exception e) {
            DevLogger.eTag(mTag, e, "setFilter");
        }
    }
}