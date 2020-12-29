package afkt.project.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;

import com.google.zxing.Result;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.ActivityQrcodeImageBinding;
import dev.other.ZXingQRCodeUtils;
import dev.other.picture.PictureSelectorUtils;
import dev.utils.DevFinal;
import dev.utils.app.ClipboardUtils;
import dev.utils.app.HandlerUtils;
import dev.utils.app.ListenerUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.TextViewUtils;
import dev.utils.app.image.ImageUtils;
import dev.utils.app.toast.ToastTintUtils;
import dev.utils.common.StringUtils;
import dev.utils.common.ThrowableUtils;

/**
 * detail: 二维码图片解析
 * @author Ttt
 */
public class QRCodeImageActivity
        extends BaseActivity<ActivityQrcodeImageBinding> {

    // 图片 Bitmap
    private Bitmap selectBitmap;

    @Override
    public int baseLayoutId() {
        return R.layout.activity_qrcode_image;
    }

    @Override
    public void initListener() {
        super.initListener();
        ListenerUtils.setOnClicks(this,
                binding.vidAqiSelectBtn, binding.vidAqiTv);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.vid_aqi_select_btn:
                // 初始化图片配置
                PictureSelectorUtils.PicConfig picConfig = new PictureSelectorUtils.PicConfig()
                        .setCompress(false).setMaxSelectNum(1).setCrop(false).setMimeType(PictureMimeType.ofImage())
                        .setCamera(true).setGif(false);
                // 打开图片选择器
                PictureSelectorUtils.openGallery(PictureSelector.create(this), picConfig);
                break;
            case R.id.vid_aqi_tv:
                String text = TextViewUtils.getText(binding.vidAqiTv);
                if (TextUtils.isEmpty(text)) return;
                // 复制到剪切板
                ClipboardUtils.copyText(text);
                // 进行提示
                ToastTintUtils.success(ResourceUtils.getString(R.string.str_copy_suc) + " -> " + text);
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
            // 解析图片
            ZXingQRCodeUtils.decodeQRCode(selectBitmap, new ZXingQRCodeUtils.QRScanCallback() {
                @Override
                public void onResult(
                        boolean success,
                        Result result,
                        Exception e
                ) {
                    HandlerUtils.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            if (success) {
                                StringBuilder builder = new StringBuilder();
                                builder.append("二维码解析数据: \n");
                                builder.append(StringUtils.checkValue(DevFinal.NULL_STR, ZXingQRCodeUtils.getResultData(result)));
                                TextViewUtils.setText(binding.vidAqiTv, builder.toString());
                            } else {
                                TextViewUtils.setText(binding.vidAqiTv, "图片非二维码 / 识别失败\n" + ThrowableUtils.getThrowableStackTrace(e));
                            }
                        }
                    });
                }
            });
        }
    }
}