package java.dev.engine.barcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import dev.engine.barcode.IBarCodeEngine;

/**
 * detail: BarCode Result
 * @author Ttt
 */
public class BarCodeResult
        extends IBarCodeEngine.EngineResult {

    private Result mResult;

    public BarCodeResult(Result result) {
        this.mResult = result;
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 是否解析成功
     * @return {@code true} success, {@code false} fail
     */
    public boolean isSuccess() {
        return mResult != null;
    }

    /**
     * 获取解析结果
     * @return 解析结果数据
     */
    public Result getResult() {
        return mResult;
    }

    // =

    /**
     * 获取扫描结果数据
     * @return 扫描结果数据
     */
    public String getResultData() {
        if (isSuccess()) {
            return mResult.getText();
        }
        return null;
    }

    /**
     * 获取条码类型
     * @return 条码类型
     */
    public BarcodeFormat getBarcodeFormat() {
        if (isSuccess()) {
            return mResult.getBarcodeFormat();
        }
        return null;
    }
}