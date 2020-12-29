package afkt.project.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.ActivityQrcodeCreateBinding;
import dev.other.ZXingQRCodeUtils;
import dev.other.picture.PictureSelectorUtils;
import dev.utils.app.EditTextUtils;
import dev.utils.app.HandlerUtils;
import dev.utils.app.ImageViewUtils;
import dev.utils.app.ListenerUtils;
import dev.utils.app.SizeUtils;
import dev.utils.app.image.ImageUtils;
import dev.utils.common.ThrowableUtils;

/**
 * detail: 创建二维码
 * @author Ttt
 */
public class QRCodeCreateActivity
        extends BaseActivity<ActivityQrcodeCreateBinding> {

    // 图片 Bitmap
    Bitmap selectBitmap;

    @Override
    public int baseLayoutId() {
        return R.layout.activity_qrcode_create;
    }

    @Override
    public void initListener() {
        super.initListener();
        ListenerUtils.setOnClicks(this,
                binding.vidAqcCreateBtn, binding.vidAqcSelectBtn);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.vid_aqc_create_btn:
                String text = EditTextUtils.getText(binding.vidAqcContentEdit);
                // 判断是否存在内容
                if (TextUtils.isEmpty(text)) {
                    showToast(false, "请输入生成二维码内容");
                    return;
                }
                int size = SizeUtils.dipConvertPx(200f);
                // 创建二维码
                ZXingQRCodeUtils.createQRCodeImage(text, size, selectBitmap, new ZXingQRCodeUtils.QRResultCallback() {
                    @Override
                    public void onResult(
                            boolean success,
                            Bitmap bitmap,
                            Exception e
                    ) {
                        if (success) {
                            HandlerUtils.postRunnable(new Runnable() {
                                @Override
                                public void run() {
                                    ImageViewUtils.setImageBitmap(binding.vidAqcIgview, bitmap);
                                }
                            });
                        } else {
                            showToast(false, ThrowableUtils.getThrowable(e));
                        }
                    }
                });

                break;
            case R.id.vid_aqc_select_btn:
                // 初始化图片配置
                PictureSelectorUtils.PicConfig picConfig = new PictureSelectorUtils.PicConfig()
                        .setCompress(false).setMaxSelectNum(1).setCrop(false).setMimeType(PictureMimeType.ofImage())
                        .setCamera(true).setGif(false);
                // 打开图片选择器
                PictureSelectorUtils.openGallery(PictureSelector.create(this), picConfig);
                break;
        }
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
        }
    }
}