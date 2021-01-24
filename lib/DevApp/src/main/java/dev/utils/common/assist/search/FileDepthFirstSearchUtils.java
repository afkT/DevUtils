package dev.utils.common.assist.search;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dev.utils.JCLogUtils;

/**
 * detail: 文件深度优先搜索算法 ( 递归搜索某个目录下的全部文件 )
 * @author Ttt
 */
public final class FileDepthFirstSearchUtils {

    // 日志 TAG
    private static final String TAG = FileDepthFirstSearchUtils.class.getSimpleName();

    /**
     * 构造函数
     */
    public FileDepthFirstSearchUtils() {
    }

    /**
     * 构造函数
     * @param searchHandler 搜索处理接口
     */
    public FileDepthFirstSearchUtils(final SearchHandler searchHandler) {
        this.mSearchHandler = searchHandler;
    }

    /**
     * detail: 文件信息 Item
     * @author Ttt
     */
    public final class FileItem {

        public FileItem(final File file) {
            this.file = file;
        }

        // 文件
        public File file;

        // 子文件夹目录
        public List<FileItem> listChilds = null;
    }

    /**
     * detail: 搜索处理接口
     * @author Ttt
     */
    public interface SearchHandler {

        /**
         * 判断是否处理该文件
         * @param file 文件
         * @return {@code true} 处理该文件, {@code false} 跳过该文件不处理
         */
        boolean isHandlerFile(File file);

        /**
         * 是否添加到集合
         * @param file 文件
         * @return {@code true} 添加, {@code false} 不添加
         */
        boolean isAddToList(File file);

        /**
         * 搜索结束监听
         * @param lists     根目录的子文件目录集合
         * @param startTime 开始扫描时间
         * @param endTime   扫描结束时间
         */
        void onEndListener(
                List<FileItem> lists,
                long startTime,
                long endTime
        );
    }

    // 搜索处理接口
    private SearchHandler mSearchHandler;

    // 内部实现接口
    private final SearchHandler mInsideHandler = new SearchHandler() {
        @Override
        public boolean isHandlerFile(File file) {
            if (mSearchHandler != null) {
                return mSearchHandler.isHandlerFile(file);
            }
            return true;
        }

        @Override
        public boolean isAddToList(File file) {
            if (mSearchHandler != null) {
                return mSearchHandler.isAddToList(file);
            }
            return true;
        }

        @Override
        public void onEndListener(
                List<FileItem> lists,
                long startTime,
                long endTime
        ) {
            // 表示非搜索中
            mIsRunning = false;
            // 触发回调
            if (mSearchHandler != null) {
                mSearchHandler.onEndListener(lists, startTime, endTime);
            }
        }
    };

    /**
     * 设置搜索处理接口
     * @param searchHandler 搜索处理接口
     * @return {@link FileDepthFirstSearchUtils}
     */
    public FileDepthFirstSearchUtils setSearchHandler(final SearchHandler searchHandler) {
        this.mSearchHandler = searchHandler;
        return this;
    }

    /**
     * 是否搜索中
     * @return {@code true} 搜索 / 运行中, {@code false} 非搜索 / 运行中
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
     * @return {@code true} 已停止搜索, {@code false} 搜索中
     */
    public boolean isStop() {
        return mIsStop;
    }

    /**
     * 获取开始搜索时间 ( 毫秒 )
     * @return 开始搜索时间 ( 毫秒 )
     */
    public long getStartTime() {
        return mStartTime;
    }

    /**
     * 获取结束搜索时间 ( 毫秒 )
     * @return 结束搜索时间 ( 毫秒 )
     */
    public long getEndTime() {
        return mEndTime;
    }

    // =

    // 判断是否运行中
    private boolean mIsRunning = false;
    // 是否停止搜索
    private boolean mIsStop    = false;
    // 开始搜索时间
    private long    mStartTime = 0L;
    // 结束搜索时间
    private long    mEndTime   = 0L;

    /**
     * 搜索目录
     * @param path       根目录路径
     * @param isRelation 是否关联到 Child List
     */
    public synchronized void query(
            final String path,
            final boolean isRelation
    ) {
        if (mIsRunning) {
            return;
        } else if (path == null || path.trim().length() == 0) {
            // 触发结束回调
            mInsideHandler.onEndListener(null, -1, -1);
            return;
        }
        // 表示运行中
        mIsRunning = true;
        mIsStop = false;
        // 设置开始搜索时间
        mStartTime = System.currentTimeMillis();
        try {
            // 获取根目录 File
            final File file = new File(path);
            // 判断是否文件
            if (file.isFile()) {
                List<FileItem> lists = new ArrayList<>();
                lists.add(new FileItem(file));
                // 触发结束回调
                mEndTime = System.currentTimeMillis();
                mInsideHandler.onEndListener(lists, mStartTime, mEndTime);
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
                        mEndTime = System.currentTimeMillis();
                        mInsideHandler.onEndListener(lists, mStartTime, mEndTime);
                    }
                }).start();
            } else {
                // 触发结束回调
                mEndTime = System.currentTimeMillis();
                mInsideHandler.onEndListener(null, mStartTime, mEndTime);
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "query");
            // 触发结束回调
            mEndTime = System.currentTimeMillis();
            mInsideHandler.onEndListener(null, mStartTime, mEndTime);
        }
    }

    /**
     * 搜索文件
     * @param file       文件
     * @param lists      保存数据源
     * @param isRelation 是否关联到 Child List
     */
    private void queryFile(
            final File file,
            final List<FileItem> lists,
            final boolean isRelation
    ) {
        try {
            if (mIsStop) {
                return;
            }
            if (file != null && file.exists()) {
                // 判断是否处理
                if (mInsideHandler.isHandlerFile(file)) {
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
                                    if (mInsideHandler.isAddToList(f)) {
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
                        if (mInsideHandler.isAddToList(file)) {
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