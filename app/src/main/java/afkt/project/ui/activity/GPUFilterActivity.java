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
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.ActivityGpuFilterBinding;
import afkt.project.model.item.FilterItem;
import afkt.project.ui.adapter.GPUFilterAdapter;
import afkt.project.util.GPUFilterUtils;
import dev.engine.log.DevLogEngine;
import dev.other.picture.PictureSelectorUtils;
import dev.utils.app.HandlerUtils;
import dev.utils.app.image.ImageUtils;

/**
 * detail: GPU 滤镜效果
 * @author Ttt
 */
public class GPUFilterActivity
        extends BaseActivity<ActivityGpuFilterBinding> {

    // 适配器
    GPUFilterAdapter gpuFilterAdapter;
    // 图片 Bitmap
    Bitmap           selectBitmap;
    // 滤镜线程
    static Runnable filterThread;

    @Override
    public int baseLayoutId() {
        return R.layout.activity_gpu_filter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        filterThread = null;
    }

    @Override
    public void initValue() {
        super.initValue();

        // 设置滤镜线程
        filterThread = new Runnable() {
            @Override
            public void run() {
                setFilter();
            }
        };

        // 设置适配器
        binding.vidAgfGallery.setAdapter(gpuFilterAdapter = new GPUFilterAdapter(this));
        binding.vidAgfGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(
                    AdapterView<?> parent,
                    View view,
                    int position,
                    long id
            ) {
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
        binding.vidAgfGallery.setSelection(0);
    }

    @Override
    public void initListener() {
        super.initListener();
        binding.vidAgfSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 初始化图片配置
                PictureSelectorUtils.PicConfig picConfig = new PictureSelectorUtils.PicConfig()
                        .setCompress(false).setMaxSelectNum(1).setCrop(false).setMimeType(PictureMimeType.ofImage())
                        .setCamera(true).setGif(false);
                // 打开图片选择器
                PictureSelectorUtils.openGallery(PictureSelector.create(mActivity), picConfig);
            }
        });
    }

    // ===========
    // = 图片回传 =
    // ===========

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data
    ) {
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

    // ===========
    // = 滤镜处理 =
    // ===========

    /**
     * 设置滤镜效果
     */
    private void setFilter() {
        try {
            if (selectBitmap == null) return;
            // 获取选中的滤镜
            int position = binding.vidAgfGallery.getSelectedItemPosition();
            // 获取滤镜 Item
            FilterItem filterItem = gpuFilterAdapter.getItem(position);
            // 设置滤镜效果
            Bitmap bitmapFilter = GPUFilterUtils.getFilterBitmap(selectBitmap, FilterItem.createFilterForType(filterItem.filterType));
            binding.vidAgfIgview.setImageBitmap(bitmapFilter);
        } catch (Exception e) {
            DevLogEngine.getEngine().eTag(TAG, e, "setFilter");
        }
    }
}