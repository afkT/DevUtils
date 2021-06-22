package dev.engine.storage;

import android.graphics.Bitmap;

/**
 * detail: Storage Item Params
 * @author Ttt
 */
public class StorageItem
        extends IStorageEngine.EngineItem {

    // 图片格式
    private Bitmap.CompressFormat format  = Bitmap.CompressFormat.PNG;
    // 图片质量
    private int                   quality = 100;

    public Bitmap.CompressFormat getFormat() {
        return format;
    }

    public StorageItem setFormat(Bitmap.CompressFormat format) {
        this.format = format;
        return this;
    }

    public int getQuality() {
        return quality;
    }

    public StorageItem setQuality(int quality) {
        this.quality = quality;
        return this;
    }
}