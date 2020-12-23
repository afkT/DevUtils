package afkt.project.util.zxing.decode;

import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Handler;

/**
 * detail: 解码配置
 * @author Ttt
 */
public interface DecodeConfig {

    // 条形码数据 Key
    String BARCODE_BITMAP = "barcode_bitmap";

    // 获取处理 Handler
    Handler getHandler();

    // 是否裁减
    boolean isCropRect();

    // 裁减区域
    Rect getCropRect();

    // 获取预览大小
    Camera.Size getPreviewSize();

    // 判断是否出现异常
    boolean isError();

    /**
     * 设置异常
     * @param isError 是否异常
     * @param e       异常信息
     */
    void setError(
            boolean isError,
            Exception e
    );
}