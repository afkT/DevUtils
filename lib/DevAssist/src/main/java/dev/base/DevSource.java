package dev.base;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.io.File;
import java.io.InputStream;

/**
 * detail: 资源来源通用类
 * @author Ttt
 */
public class DevSource {

    public final String      mUrl;
    public final Uri         mUri;
    public final byte[]      mBytes;
    public final int         mResource;
    public final File        mFile;
    public final InputStream mInputStream;
    public final Drawable    mDrawable;
    public final Bitmap      mBitmap;

    public DevSource(
            String url,
            Uri uri,
            byte[] bytes,
            int resource,
            File file,
            InputStream inputStream,
            Drawable drawable,
            Bitmap bitmap
    ) {
        this.mUrl         = url;
        this.mUri         = uri;
        this.mBytes       = bytes;
        this.mResource    = resource;
        this.mFile        = file;
        this.mInputStream = inputStream;
        this.mDrawable    = drawable;
        this.mBitmap      = bitmap;
    }

    public static DevSource create(final String url) {
        return new DevSource(
                url, null, null, 0,
                null, null, null, null
        );
    }

    public static DevSource create(final Uri uri) {
        return new DevSource(
                null, uri, null, 0,
                null, null, null, null
        );
    }

    public static DevSource create(final byte[] bytes) {
        return new DevSource(
                null, null, bytes, 0,
                null, null, null, null
        );
    }

    public static DevSource create(final int resource) {
        return new DevSource(
                null, null, null, resource,
                null, null, null, null
        );
    }

    public static DevSource create(final File file) {
        return new DevSource(
                null, null, null, 0,
                file, null, null, null
        );
    }

    public static DevSource create(final InputStream inputStream) {
        return new DevSource(
                null, null, null, 0,
                null, inputStream, null, null
        );
    }

    public static DevSource create(final Drawable drawable) {
        return new DevSource(
                null, null, null, 0,
                null, null, drawable, null
        );
    }

    public static DevSource create(final Bitmap bitmap) {
        return new DevSource(
                null, null, null, 0,
                null, null, null, bitmap
        );
    }

    public static DevSource createWithPath(final String path) {
        return create(path != null ? new File(path) : null);
    }

    // =

    public boolean isUrl() {
        return mUrl != null;
    }

    public boolean isUri() {
        return mUri != null;
    }

    public boolean isBytes() {
        return mBytes != null;
    }

    public boolean isResource() {
        return mResource != 0;
    }

    public boolean isFile() {
        return mFile != null;
    }

    public boolean isInputStream() {
        return mInputStream != null;
    }

    public boolean isDrawable() {
        return mDrawable != null;
    }

    public boolean isBitmap() {
        return mBitmap != null;
    }

    /**
     * 是否有效资源
     * @return {@code true} yes, {@code false} no
     */
    public boolean isSource() {
        return isUrl() || isUri() || isBytes() || isResource()
                || isFile() || isInputStream() || isDrawable() || isBitmap();
    }
}