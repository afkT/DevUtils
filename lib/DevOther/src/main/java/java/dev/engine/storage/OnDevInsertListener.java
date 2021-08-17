package java.dev.engine.storage;

import dev.engine.storage.listener.OnInsertListener;

/**
 * detail: 插入多媒体资源事件 封装接口
 * @author Ttt
 * <pre>
 *     减少需要 OnInsertListener<StorageItem, StorageResult> 声明使用
 *     内部直接封装具体泛型类
 * </pre>
 */
public interface OnDevInsertListener
        extends OnInsertListener<StorageItem, StorageResult> {
}