package dev.engine.barcode

import com.google.zxing.BarcodeFormat
import com.google.zxing.Result

/**
 * detail: BarCode Result
 * @author Ttt
 */
class BarCodeResult(
    private val mResult: Result?
) : IBarCodeEngine.EngineResult() {

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 是否解析成功
     * @return `true` success, `false` fail
     */
    fun isSuccess(): Boolean {
        return mResult != null
    }

    /**
     * 获取解析结果
     * @return 解析结果数据
     */
    fun getResult(): Result? {
        return mResult
    }

    // =

    /**
     * 获取扫描结果数据
     * @return 扫描结果数据
     */
    fun getResultData(): String? {
        return mResult?.text
    }

    /**
     * 获取条码类型
     * @return 条码类型
     */
    fun getBarcodeFormat(): BarcodeFormat? {
        return mResult?.barcodeFormat
    }
}