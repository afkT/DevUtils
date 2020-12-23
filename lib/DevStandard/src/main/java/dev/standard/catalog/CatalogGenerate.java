package dev.standard.catalog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.utils.common.CollectionUtils;
import dev.utils.common.FileUtils;
import dev.utils.common.StringUtils;

/**
 * detail: 文件目录结构生成
 * @author Ttt
 */
final class CatalogGenerate {

    private CatalogGenerate() {
    }

    // ===================
    // = 目录层级计算回调 =
    // ===================

    /**
     * detail: 文件目录层级回调
     * @author Ttt
     */
    private interface CatalogCallback {

        /**
         * 回调通知
         * @param name       目录名
         * @param lineNumber 行数
         * @param classTag   Class TAG 标记
         */
        void callback(
                String name,
                int lineNumber,
                String classTag
        );
    }

    // ====================
    // = 文件目录遍历实体类 =
    // ====================

    /**
     * 获取文件夹目录列表
     * @param path          文件路径
     * @param callback      目录回调通知
     * @param ignoreCatalog 忽略目录
     * @param layer         目录层级
     * @return 文件夹目录列表集合
     */
    private static List<FileUtils.FileList> getFolderLists(
            final String path,
            final CatalogCallback callback,
            final String[] ignoreCatalog,
            final int layer
    ) {
        return getFolderLists(path, callback, ignoreCatalog, layer, 0);
    }

    /**
     * 获取文件夹目录列表
     * @param path          文件路径
     * @param callback      目录回调通知
     * @param ignoreCatalog 忽略目录
     * @param layer         目录层级
     * @param curLayer      当前层级
     * @return 文件夹目录列表集合
     */
    private static List<FileUtils.FileList> getFolderLists(
            final String path,
            final CatalogCallback callback,
            final String[] ignoreCatalog,
            final int layer,
            final int curLayer
    ) {
        // 当前层级大于想要的层级则 return
        if (curLayer > layer) return new ArrayList<>();
        List<FileUtils.FileList> lists = new ArrayList<>();
        // 获取文件路径
        File baseFile = new File(path);
        // 获取子文件
        File[] files = baseFile.listFiles();
        for (File file : files) {
            String name = file.getName();
            // 隐藏文件跳过
            if (file.isHidden() || name.startsWith(".")) {
                continue;
            }
            // 判断根目录是否需要忽略
            if (curLayer != 0 && StringUtils.isContains(baseFile.getName(), ignoreCatalog)) {
                return lists;
            }
            // 属于文件夹才处理
            if (file.isDirectory()) {
                FileUtils.FileList catalog = new FileUtils.FileList(file,
                        getFolderLists(file.getAbsolutePath(), callback, ignoreCatalog, layer, curLayer + 1));
                lists.add(catalog);
                // 触发回调
                if (callback != null) {
                    // lineNumber 固定传 1 只是为了增加默认空格间距
                    callback.callback(name, curLayer + 1, name + "." + name);
                }
            }
        }
        return lists;
    }

    // ===========
    // = 内部方法 =
    // ===========

    // 目录信息最大长度
    private static int sMaxLength = 0;

    /**
     * 计算目录最大长度
     * @param name       目录名
     * @param lineNumber 行数
     */
    private static void calculateMaxLength(
            final String name,
            final int lineNumber
    ) {
        StringBuilder builder = new StringBuilder(); // 添加目录
        builder.append(createCatalog(name, lineNumber));
        int length = builder.length();
        // 判断长度 => 大于最大长度, 则重新设置
        if ((length + 6) >= sMaxLength) {
            sMaxLength = length + 6;
        }
    }

    /**
     * 创建目录信息
     * @param name       目录名
     * @param lineNumber 行数
     * @return 目录信息
     */
    private static String createCatalog(
            final String name,
            final int lineNumber
    ) {
        StringBuilder builder = new StringBuilder(); // 添加空格
        builder.append(StringUtils.appendSpace(lineNumber * 3));
        builder.append("- ").append(name); // 打印目录
        return builder.toString();
    }

    /**
     * 创建目录行
     * @param name       目录名
     * @param lineNumber 行数
     * @param classTag   Class TAG 标记
     * @param mapCatalog 对应目录注释
     * @return 目录行信息
     */
    private static String createCatalogLine(
            final String name,
            final int lineNumber,
            final String classTag,
            final Map<String, String> mapCatalog
    ) {
        StringBuilder builder = new StringBuilder(); // 添加目录
        builder.append(createCatalog(name, lineNumber));
        // 设置间隔长度
        builder.append(StringUtils.appendSpace(sMaxLength - builder.length()));
        // 添加 间隔 |
        builder.append("| ").append(mapCatalog.get(classTag));
        return builder.toString();
    }

    /**
     * 递归目录拼接目录列表信息
     * @param builder    拼接 Builder
     * @param lists      目录列表
     * @param lineNumber 行数
     * @param classTag   Class TAG 标记
     * @param mapCatalog 对应目录注释
     */
    private static void forCatalog(
            final StringBuilder builder,
            final List<FileUtils.FileList> lists,
            final int lineNumber,
            final String classTag,
            final Map<String, String> mapCatalog
    ) {
        for (int i = 0, len = lists.size(); i < len; i++) {
            // 获取目录
            FileUtils.FileList catalog = lists.get(i);
            // 获取目录名
            String name = catalog.getFile().getName();
            // 进行换行
            builder.append("\n");
            // 添加目录行
            builder.append(createCatalogLine(name, lineNumber, classTag + "." + name, mapCatalog));
            // 判断是否存在子文件夹
            if (catalog.getSubFiles().size() != 0) {
                forCatalog(builder, catalog.getSubFiles(), lineNumber + 1, classTag + "." + name, mapCatalog);
            }
        }
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 生成目录信息
     * @param path              文件路径
     * @param dirName           文件名
     * @param mapCatalog        对应目录注释
     * @param listIgnoreCatalog 忽略目录
     * @param layer             目录层级
     * @return 目录信息
     */
    public static String generate(
            final String path,
            final String dirName,
            final Map<String, String> mapCatalog,
            final List<String> listIgnoreCatalog,
            final int layer
    ) {
        StringBuilder builder = new StringBuilder();
        // 获取文件夹列表
        List<FileUtils.FileList> lists = getFolderLists(path, new CatalogCallback() {
            @Override
            public void callback(
                    String name,
                    int lineNumber,
                    String classTag
            ) {
                // 计算目录最大长度
                calculateMaxLength(name, lineNumber);
            }
        }, CollectionUtils.toArrayT(listIgnoreCatalog), layer);
        // 默认头部
        String head = "- " + dirName;
        builder.append("```\n");
        // 增加根目录
        builder.append(head).append(StringUtils.appendSpace(sMaxLength - head.length())).append("| ").append(mapCatalog.get(dirName));
        // 递归循环目录
        forCatalog(builder, lists, 1, "", mapCatalog);
        builder.append("\n```\n");
        return builder.toString();
    }
}