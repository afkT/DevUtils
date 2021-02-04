package dev.base;

import android.net.Uri;

import java.io.File;

/**
 * detail: 资源路径通用类
 * @author Ttt
 */
public class DevSource {

    public String mUrl;
    public Uri    mUri;
    public byte[] mBytes;
    public int    mResource;
    public File   mFile;

    private DevSource() {
    }

    public static DevSource create(final String url) {
        DevSource source = new DevSource();
        source.mUrl = url;
        return source;
    }

    public static DevSource create(final Uri uri) {
        DevSource source = new DevSource();
        source.mUri = uri;
        return source;
    }

    public static DevSource create(final byte[] bytes) {
        DevSource source = new DevSource();
        source.mBytes = bytes;
        return source;
    }

    public static DevSource create(final int resource) {
        DevSource source = new DevSource();
        source.mResource = resource;
        return source;
    }

    public static DevSource create(final File file) {
        DevSource source = new DevSource();
        source.mFile = file;
        return source;
    }

    public static DevSource createWithPath(final String path) {
        return create(path != null ? new File(path) : null);
    }
}