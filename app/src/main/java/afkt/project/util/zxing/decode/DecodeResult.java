package afkt.project.util.zxing.decode;

import android.os.Bundle;

import com.google.zxing.Result;

/**
 * detail: 解码结果回调
 * @author Ttt
 */
public interface DecodeResult {

    /**
     * 处理解码 ( 解码成功 )
     * @param result 识别数据 {@link Result}
     * @param bundle {@link Bundle}
     */
    void handleDecode(
            Result result,
            Bundle bundle
    );
}