package dev.engine.cache;

/**
 * detail: Cache Data Item
 * @author Ttt
 */
public class DataItem
        extends ICacheEngine.EngineItem {

    public DataItem(
            String key,
            int type,
            long size,
            long saveTime,
            long validTime,
            boolean isPermanent,
            boolean isDue
    ) {
        super(key, type, size, saveTime, validTime, isPermanent, isDue);
    }
}