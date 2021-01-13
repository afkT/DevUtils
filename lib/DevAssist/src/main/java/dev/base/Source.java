package dev.base;

import android.net.Uri;

import java.io.File;

/**
 * detail: 资源路径通用类
 * @author Ttt
 */
public class Source {

    public String mUrl;
    public Uri    mUri;
    public byte[] mBytes;
    public int    mResource;
    public File   mFile;

    private Source() {
    }

    public static Source create(final String url) {
        Source source = new Source();
        source.mUrl = url;
        return source;
    }

    public static Source create(final Uri uri) {
        Source source = new Source();
        source.mUri = uri;
        return source;
    }

    public static Source create(final byte[] bytes) {
        Source source = new Source();
        source.mBytes = bytes;
        return source;
    }

    public static Source create(final int resource) {
        Source source = new Source();
        source.mResource = resource;
        return source;
    }

    public static Source create(final File file) {
        Source source = new Source();
        source.mFile = file;
        return source;
    }

    public static Source createWithPath(final String path) {
        return create(new File(path));
    }
}