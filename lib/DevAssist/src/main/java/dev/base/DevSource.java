package dev.base;

import android.net.Uri;

import java.io.File;

/**
 * detail: 资源路径通用类
 * @author Ttt
 */
public class DevSource {

    public final String mUrl;
    public final Uri    mUri;
    public final byte[] mBytes;
    public final int    mResource;
    public final File   mFile;

    public DevSource(
            String url,
            Uri uri,
            byte[] bytes,
            int resource,
            File file
    ) {
        this.mUrl = url;
        this.mUri = uri;
        this.mBytes = bytes;
        this.mResource = resource;
        this.mFile = file;
    }

    public static DevSource create(final String url) {
        return new DevSource(
                url, null, null, 0, null
        );
    }

    public static DevSource create(final Uri uri) {
        return new DevSource(
                null, uri, null, 0, null
        );
    }

    public static DevSource create(final byte[] bytes) {
        return new DevSource(
                null, null, bytes, 0, null
        );
    }

    public static DevSource create(final int resource) {
        return new DevSource(
                null, null, null, resource, null
        );
    }

    public static DevSource create(final File file) {
        return new DevSource(
                null, null, null, 0, file
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

    public boolean isSource() {
        return isUrl() || isUri() || isBytes() || isResource() || isFile();
    }
}