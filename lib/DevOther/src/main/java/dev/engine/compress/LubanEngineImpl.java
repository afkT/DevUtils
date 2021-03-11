package dev.engine.compress;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.engine.compress.listener.CompressFilter;
import dev.engine.compress.listener.OnCompressListener;
import dev.engine.compress.listener.OnRenameListener;
import dev.other.LubanUtils;

/**
 * detail: Luban Image Compress Engine 实现
 * @author Ttt
 */
public class LubanEngineImpl
        implements ICompressEngine<CompressConfig> {

    @Override
    public <T> boolean compress(
            T data,
            CompressConfig config,
            OnCompressListener compressListener
    ) {
        return compress(data, config, null, null, compressListener);
    }

    @Override
    public <T> boolean compress(
            T data,
            CompressConfig config,
            CompressFilter filter,
            OnRenameListener renameListener,
            OnCompressListener compressListener
    ) {
        if (data == null || config == null || compressListener == null) return false;
        List<T> lists = new ArrayList<>();
        lists.add(data);
        return compress(lists, config, filter, renameListener, compressListener);
    }

    @Override
    public <T> boolean compress(
            List<T> lists,
            CompressConfig config,
            OnCompressListener compressListener
    ) {
        return compress(lists, config, null, null, compressListener);
    }

    @Override
    public <T> boolean compress(
            List<T> lists,
            CompressConfig config,
            CompressFilter filter,
            OnRenameListener renameListener,
            OnCompressListener compressListener
    ) {
        if (lists == null || config == null || compressListener == null) return false;

        top.zibin.luban.CompressionPredicate predicate = null;
        if (filter != null) {
            predicate = path -> filter.apply(path);
        }
        top.zibin.luban.OnRenameListener rename = null;
        if (renameListener != null) {
            rename = filePath -> renameListener.rename(filePath);
        }
        return LubanUtils.compress(
                lists, config.ignoreSize, config.focusAlpha, config.targetDir,
                predicate, rename, new LubanUtils.OnCompressListener() {
                    @Override
                    public void onStart(int index) {
                        if (compressListener != null) {
                            compressListener.onStart(index);
                        }
                    }

                    @Override
                    public void onSuccess(
                            File file,
                            int index
                    ) {
                        if (compressListener != null) {
                            compressListener.onSuccess(file, index);
                        }
                    }

                    @Override
                    public void onError(
                            Throwable error,
                            int index
                    ) {
                        if (compressListener != null) {
                            compressListener.onError(error, index);
                        }
                    }

                    @Override
                    public void onComplete(
                            List<File> lists,
                            Map<Integer, File> maps
                    ) {
                        if (compressListener != null) {
                            compressListener.onComplete(lists, maps);
                        }
                    }
                }
        );
    }
}