package dev.utils.common.assist.search;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import dev.utils.JCLogUtils;

/**
 * detail: 文件广度优先搜索算法 (多线程 + 队列，搜索某个目录下的全部文件)
 * Created by Ttt
 */
public final class FileBreadthFirstSearchUtils {

    // 日志 TAG
    private static final String TAG = FileBreadthFirstSearchUtils.class.getSimpleName();

    // = 构造函数 =

    public FileBreadthFirstSearchUtils() {
    }

    public FileBreadthFirstSearchUtils(final ISearchHandler iSearchHandler) {
        this.iSearchHandler = iSearchHandler;
    }

    /**
     * detail: 文件信息 Item
     * Created by Ttt
     */
    public final class FileItem {

        public FileItem(final File file) {
            this.file = file;
        }

        // 文件
        public File file;

        // HashMap 保存目录信息
        public Map<String, FileItem> mapChilds;

        /**
         * 保存子文件信息
         * @param file
         */
        private synchronized FileItem put(final File file) {
            if (mapChilds == null) {
                mapChilds = new HashMap<>();
            }
            if (file != null) {
                FileItem fileItem = new FileItem(file);
                mapChilds.put(file.getAbsolutePath(), fileItem);
                return fileItem;
            }
            return null;
        }
    }

    /**
     * detail: 文件队列
     * Created by Ttt
     */
    private class FileQueue {

        public FileQueue(File file, FileItem fileItem) {
            this.file = file;
            this.fileItem = fileItem;
        }

        // 当前准备处理文件夹
        private File file;

        // 上一级目录对象
        private FileItem fileItem;
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
         * @param rootFileItem
         * @param startTime
         * @param endTime
         */
        void OnEndListener(FileItem rootFileItem, long startTime, long endTime);
    }

    // 搜索处理接口
    private ISearchHandler iSearchHandler;

    // 内部实现接口
    private ISearchHandler insideHandler = new ISearchHandler() {
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
        public void OnEndListener(FileItem rootFileItem, long startTime, long endTime) {
            // 表示非搜索中
            mIsRunning = false;
            // 触发回调
            if (iSearchHandler != null) {
                iSearchHandler.OnEndListener(rootFileItem, startTime, endTime);
            }
        }
    };

    /**
     * 设置搜索处理接口
     * @param iSearchHandler
     * @return
     */
    public FileBreadthFirstSearchUtils setSearchHandler(final ISearchHandler iSearchHandler) {
        this.iSearchHandler = iSearchHandler;
        return this;
    }

    /**
     * 获取任务队列同时进行数量
     * @return
     */
    public int getQueueSameTimeNumber() {
        return queueSameTimeNumber;
    }

    /**
     * 任务队列同时进行数量
     * @param queueSameTimeNumber
     * @return
     */
    public synchronized FileBreadthFirstSearchUtils setQueueSameTimeNumber(final int queueSameTimeNumber) {
        if (mIsRunning) {
            return this;
        }
        this.queueSameTimeNumber = queueSameTimeNumber;
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

    /**
     * 获取延迟校验时间
     * @return
     */
    public long getDelayTime() {
        return delayTime;
    }

    /**
     * 设置延迟校验时间
     * @param delayTime
     */
    public void setDelayTime(final long delayTime) {
        this.delayTime = delayTime;
    }

    // =

    // 根目录对象
    private FileItem rootFileItem;
    // 判断是否运行中
    private boolean mIsRunning = false;
    // 是否停止搜索
    private boolean mIsStop = false;
    // 开始搜索时间
    private long startTime = 0l;
    // 结束搜索时间
    private long endTime = 0l;
    // 延迟时间
    private long delayTime = 50l;
    // 任务队列同时进行数量
    private int queueSameTimeNumber = 5;
    // 线程池
    private ExecutorService executor = Executors.newCachedThreadPool();
    // 任务队列
    private LinkedBlockingQueue<FileQueue> taskQueue = new LinkedBlockingQueue<>();

    /**
     * 搜索目录
     * @param path 根目录地址
     */
    public synchronized void query(final String path) {
        if (mIsRunning) {
            return;
        } else if (path == null || path.trim().length() == 0) {
            // 触发结束回调
            insideHandler.OnEndListener(null, -1, -1);
            return;
        }
        // 表示运行中
        mIsRunning = true;
        mIsStop = false;
        // 设置开始搜索时间
        startTime = System.currentTimeMillis();
        try {
            // 获取根目录 File
            File file = new File(path);
            if (file != null) {
                // 初始化根目录
                rootFileItem = new FileItem(file);
                // 判断是否文件
                if (file.isFile()) {
                    // 触发结束回调
                    endTime = System.currentTimeMillis();
                    insideHandler.OnEndListener(rootFileItem, startTime, endTime);
                    return;
                }
                // 获取文件夹全部子文件
                String[] fileArys = file.list();
                // 获取文件总数
                if (fileArys != null && fileArys.length != 0) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // 查询文件
                            queryFile(rootFileItem.file, rootFileItem);
                            // 循环队列
                            whileQueue();
                        }
                    }).start();
                } else {
                    // 触发结束回调
                    endTime = System.currentTimeMillis();
                    insideHandler.OnEndListener(rootFileItem, startTime, endTime);
                }
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "query");
            // 触发结束回调
            endTime = System.currentTimeMillis();
            insideHandler.OnEndListener(rootFileItem, startTime, endTime);
        }
    }

    /**
     * 搜索文件
     * @param file
     * @param fileItem 所在文件夹对象(上一级目录)
     */
    private void queryFile(final File file, final FileItem fileItem) {
        try {
            if (mIsStop) {
                return;
            }
            if (file != null && file.exists()) {
                // 判断是否处理
                if (insideHandler.isHandlerFile(file)) {
                    // 如果属于文件夹
                    if (file.isDirectory()) {
                        // 获取文件夹全部子文件
                        File[] files = file.listFiles();
                        if (files == null) {
                            return;
                        }
                        // 循环处理
                        for (File f : files) {
                            // 属于文件夹
                            if (f.isDirectory()) {
                                if (mIsStop) {
                                    return;
                                }
                                FileItem subFileItem = fileItem.put(f);
                                // 添加任务
                                taskQueue.offer(new FileQueue(f, subFileItem));
                            } else { // 属于文件
                                if (!mIsStop && insideHandler.isAddToList(f)) {
                                    // 属于文件则直接保存
                                    fileItem.put(f);
                                }
                            }
                        }
                    } else { // 属于文件
                        if (!mIsStop && insideHandler.isAddToList(file)) {
                            // 属于文件则直接保存
                            fileItem.put(file);
                        }
                    }
                }
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "queryFile");
        }
    }

    // = 线程池处理 =

    /**
     * 循环队列
     */
    private void whileQueue() {
        // 判断是否为null
        boolean isEmpty = taskQueue.isEmpty();
        // 循环则不处理
        while (!isEmpty) {
            if (mIsStop) {
                break;
            }
            // 获取线程活动数量
            int threadCount = ((ThreadPoolExecutor) executor).getActiveCount();
            // 判断是否超过
            if (threadCount > queueSameTimeNumber) {
                continue;
            }
            // 获取文件对象
            final FileQueue fileQueue = taskQueue.poll();
            // 判断是否为null
            if (fileQueue != null) {
                // 后台运行
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        queryFile(fileQueue.file, fileQueue.fileItem);
                    }
                });
            }

            // 判断是否存在队列数据
            isEmpty = (taskQueue.isEmpty() && threadCount == 0);
            if (isEmpty) { // 如果不存在, 防止搜索过快, 延迟再次判断
                if (mIsStop) {
                    break;
                }
                try {
                    Thread.sleep(delayTime);
                } catch (Exception e) {
                }
                isEmpty = (taskQueue.isEmpty() && threadCount == 0);
            }
        }
        // 触发结束回调
        endTime = System.currentTimeMillis();
        insideHandler.OnEndListener(rootFileItem, startTime, endTime);
    }
}
