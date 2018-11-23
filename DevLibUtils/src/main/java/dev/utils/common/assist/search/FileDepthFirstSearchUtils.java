package dev.utils.common.assist.search;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * detail: 文件深度优先搜索算法 (搜索某个目录下的全部文件)
 * Created by Ttt
 */
public class FileDepthFirstSearchUtils {

    // = 构造函数 =

    public FileDepthFirstSearchUtils(){
    }

    public FileDepthFirstSearchUtils(ISearchHandle iSearchHandle) {
        this.iSearchHandle = iSearchHandle;
    }

    /**
     * 文件信息 Item
     */
    public static class FileItem {

        public FileItem(File file) {
            this.file = file;
        }

        // 文件
        public File file;

        // 子文件夹、文件对象
        public List<FileItem> listChilds = null;
    }

    /**
     * 搜索处理接口
     */
    public interface ISearchHandle {

        /**
         * 判断是否处理该文件
         * @param file
         * @return
         */
        boolean isHandleFile(File file);

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
    private ISearchHandle iSearchHandle;

    // 内部实现接口
    private ISearchHandle inside = new ISearchHandle() {
        @Override
        public boolean isHandleFile(File file) {
            if (iSearchHandle != null){
                return iSearchHandle.isHandleFile(file);
            }
            return true;
        }

        @Override
        public boolean isAddToList(File file) {
            if (iSearchHandle != null){
                return iSearchHandle.isAddToList(file);
            }
            return true;
        }

        @Override
        public void OnEndListener(List<FileItem> lists, long startTime, long endTime) {
            // 表示非搜索中
            isRuning = false;
            // 触发回调
            if (iSearchHandle != null){
                iSearchHandle.OnEndListener(lists, startTime, endTime);
            }
        }
    };

    /**
     * 设置搜索处理接口
     * @param iSearchHandle
     * @return
     */
    public FileDepthFirstSearchUtils setSearchHandle(ISearchHandle iSearchHandle) {
        this.iSearchHandle = iSearchHandle;
        return this;
    }

    /**
     * 是否搜索中
     * @return
     */
    public boolean isRuning() {
        return isRuning;
    }

    /**
     * 停止搜索
     */
    public void stop(){
        isStop = true;
    }

    /**
     * 是否停止搜索
     * @return
     */
    public boolean isStop() {
        return isStop;
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
    private boolean isRuning = false;
    // 是否停止搜索
    private boolean isStop = false;
    // 开始搜索时间
    private long startTime = 0l;
    // 结束搜索时间
    private long endTime = 0l;

    /**
     * 查询
     * @param path 根目录地址
     * @param isRelation 是否关联到 Child List
     */
    public synchronized void query(String path, final boolean isRelation) {
        if (isRuning){
            return;
        }
        // 表示运行中
        isRuning = true;
        isStop = false;
        // 设置开始搜索时间
        startTime = System.currentTimeMillis();
        try {
            // 获取根目录 File
            final File file = new File(path);
            if (file != null) {
                // 判断是否文件
                if (file.isFile()){
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
            // 触发结束回调
            endTime = System.currentTimeMillis();
            inside.OnEndListener(null, startTime, endTime);
        }
    }

    /**
     * 查询文件
     * @param file
     * @param lists 保存数据源
     * @param isRelation 是否关联到 Child List
     */
    private void queryFile(File file, List<FileItem> lists, boolean isRelation) {
        try {
            if (isStop){
                return;
            }
            if (file != null && file.exists()) {
                // 判断是否处理
                if (inside.isHandleFile(file)){
                    // 如果属于文件夹
                    if (file.isDirectory()){
                        // 获取文件夹全部子文件
                        File[] files = file.listFiles();
                        if (files == null){
                            return;
                        }
                        // 循环处理
                        for (File f : files){
                            if (isRelation) {
                                if (f.isDirectory()){
                                    List<FileItem> childs = new ArrayList<>();
                                    // 查找文件
                                    queryFile(f, childs, isRelation);
                                    // 保存数据
                                    FileItem fileItem = new FileItem(f);
                                    fileItem.listChilds = childs;
                                    lists.add(fileItem);
                                } else {
                                    // 属于文件
                                    if (inside.isAddToList(f)){
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
                        if (inside.isAddToList(file)){
                            // 属于文件则直接保存
                            lists.add(new FileItem(file));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
