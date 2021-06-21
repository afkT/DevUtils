package dev.base;

import android.net.Uri;

import java.io.File;
import java.io.InputStream;

/**
 * detail: 资源路径通用类
 * @author Ttt
 */
public class DevSource {

    public final String      mUrl;
    public final Uri         mUri;
    public final byte[]      mBytes;
    public final int         mResource;
    public final File        mFile;
    public final InputStream mInputStream;

    public DevSource(
            String url,
            Uri uri,
            byte[] bytes,
            int resource,
            File file,
            InputStream inputStream
    ) {
        this.mUrl         = url;
        this.mUri         = uri;
        this.mBytes       = bytes;
        this.mResource    = resource;
        this.mFile        = file;
        this.mInputStream = inputStream;
    }

    public static DevSource create(final String url) {
        return new DevSource(
                url, null, null, 0, null, null
        );
    }

    public static DevSource create(final Uri uri) {
        return new DevSource(
                null, uri, null, 0, null, null
        );
    }

    public static DevSource create(final byte[] bytes) {
        return new DevSource(
                null, null, bytes, 0, null, null
        );
    }

    public static DevSource create(final int resource) {
        return new DevSource(
                null, null, null, resource, null, null
        );
    }

    public static DevSource create(final File file) {
        return new DevSource(
                null, null, null, 0, file, null
        );
    }

    public static DevSource create(final InputStream inputStream) {
        return new DevSource(
                null, null, null, 0, null, inputStream
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

    public boolean isSource() {
        return isUrl() || isUri() || isBytes() || isResource() || isFile() || isInputStream();
    }
}