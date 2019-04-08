package dev.utils.common.assist.search;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dev.utils.JCLogUtils;

/**
 * detail: 文件深度优先搜索算法 (递归搜索某个目录下的全部文件)
 * Created by Ttt
 */
public final class FileDepthFirstSearchUtils {

    // 日志 TAG
    private static final String TAG = FileDepthFirstSearchUtils.class.getSimpleName();

    // = 构造函数 =

    public FileDepthFirstSearchUtils() {
    }

    public FileDepthFirstSearchUtils(ISearchHandler iSearchHandler) {
        this.iSearchHandler = iSearchHandler;
    }

    /**
     * detail: 文件信息 Item
     * Created by Ttt
     */
    public final class FileItem {

        public FileItem(File file) {
            this.file = file;
        }

        // 文件
        public File file;

        // 子文件夹、文件对象
        public List<FileItem> listChilds = null;
    }

    /**
     * detail: 搜索处理接口
     * Created by Ttt
     */
    public interface ISearchHandler {

        /**
         * 判断是否处理该文件
         * @param file
         * @return
         */
        boolean isHandlerFile(File file);

        /**
         * 是否添加到集合
         * @param file
         * @return
         */
        boolean isAddToList(File file);

        /**
         * 搜索结束监听
         * @param lists
         * @param startTime
         * @param endTime
         */
        void OnEndListener(List<FileItem> lists, long startTime, long endTime);

    }

    // 搜索处理接口
    private ISearchHandler iSearchHandler;

    // 内部实现接口
    private ISearchHandler inside = new ISearchHandler() {
        @Override
        public boolean isHandlerFile(File file) {
            if (iSearchHandler != null) {
                return iSearchHandler.isHandlerFile(file);
            }
            return true;
        }

        @Override
        public boolean isAddToList(File file) {
            if (iSearchHandler != null) {
                return iSearchHandler.isAddToList(file);
            }
            return true;
        }

        @Override
        public void OnEndListener(List<FileItem> lists, long startTime, long endTime) {
            // 表示非搜索中
            mIsRunning = false;
            // 触发回调
            if (iSearchHandler != null) {
                iSearchHandler.OnEndListener(lists, startTime, endTime);
            }
        }
    };

    /**
     * 设置搜索处理接口
     * @param iSearchHandler
     * @return
     */
    public FileDepthFirstSearchUtils setSearchHandler(ISearchHandler iSearchHandler) {
        this.iSearchHandler = iSearchHandler;
        return this;
    }

    /**
     * 是否搜索中
     * @return
     */
    public boolean isRunning() {
        return mIsRunning;
    }

    /**
     * 停止搜索
     */
    public void stop() {
        mIsStop = true;
    }

    /**
     * 是否停止搜索
     * @return
     */
    public boolean isStop() {
        return mIsStop;
    }

    /**
     * 获取开始搜索时间
     * @return
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * 获取结束搜索时间
     * @return
     */
    public long getEndTime() {
        return endTime;
    }

    // ==

    // 判断是否运行中
    private boolean mIsRunning = false;
    // 是否停止搜索
    private boolean mIsStop = false;
    // 开始搜索时间
    private long startTime = 0l;
    // 结束搜索时间
    private long endTime = 0l;

    /**
     * 搜索目录
     * @param path 根目录地址
     * @param isRelation 是否关联到 Child List
     */
    public synchronized void query(final String path, final boolean isRelation) {
        if (mIsRunning) {
            return;
        } else if (path == null) {
            // 触发结束回调
            inside.OnEndListener(null, -1, -1);
            return;
        }
        // 表示运行中
        mIsRunning = true;
        mIsStop = false;
        // 设置开始搜索时间
        startTime = System.currentTimeMillis();
        try {
            // 获取根目录 File
            final File file = new File(path);
            if (file != null) {
                // 判断是否文件
                if (file.isFile()) {
                    List<FileItem> lists = new ArrayList<>();
                    lists.add(new FileItem(file));
                    // 触发结束回调
                    endTime = System.currentTimeMillis();
                    inside.OnEndListener(lists, startTime, endTime);
                    return;
                }
                // 获取文件夹全部子文件
                String[] fileArys = file.list();
                // 获取文件总数
                if (fileArys != null && fileArys.length != 0) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            List<FileItem> lists = new ArrayList<>();
                            // 查询文件
                            queryFile(file, lists, isRelation);
                            // 触发结束回调
                            endTime = System.currentTimeMillis();
                            inside.OnEndListener(lists, startTime, endTime);
                        }
                    }).start();
                } else {
                    // 触发结束回调
                    endTime = System.currentTimeMillis();
                    inside.OnEndListener(null, startTime, endTime);
                }
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "query");
            // 触发结束回调
            endTime = System.currentTimeMillis();
            inside.OnEndListener(null, startTime, endTime);
        }
    }

    /**
     * 搜索文件
     * @param file
     * @param lists 保存数据源
     * @param isRelation 是否关联到 Child List
     */
    private void queryFile(File file, List<FileItem> lists, boolean isRelation) {
        try {
            if (mIsStop) {
                return;
            }
            if (file != null && file.exists()) {
                // 判断是否处理
                if (inside.isHandlerFile(file)) {
                    // 如果属于文件夹
                    if (file.isDirectory()) {
                        // 获取文件夹全部子文件
                        File[] files = file.listFiles();
                        if (files == null) {
                            return;
                        }
                        // 循环处理
                        for (File f : files) {
                            if (isRelation) {
                                if (f.isDirectory()) {
                                    List<FileItem> childs = new ArrayList<>();
                                    // 查找文件
                                    queryFile(f, childs, isRelation);
                                    // 保存数据
                                    FileItem fileItem = new FileItem(f);
                                    fileItem.listChilds = childs;
                                    lists.add(fileItem);
                                } else {
                                    // 属于文件
                                    if (inside.isAddToList(f)) {
                                        // 属于文件则直接保存
                                        lists.add(new FileItem(f));
                                    }
                                }
                            } else {
                                // 查找文件
                                queryFile(f, lists, isRelation);
                            }
                        }
                    } else { // 属于文件
                        if (inside.isAddToList(file)) {
                            // 属于文件则直接保存
                            lists.add(new FileItem(file));
                        }
                    }
                }
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "queryFile");
        }
    }
}
