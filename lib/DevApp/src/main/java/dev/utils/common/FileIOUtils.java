package dev.utils.common;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import dev.utils.DevFinal;
import dev.utils.JCLogUtils;

/**
 * detail: 文件 ( IO 流 ) 工具类
 * @author Ttt
 */
public final class FileIOUtils {

    private FileIOUtils() {
    }

    // 日志 TAG
    private static final String TAG = FileIOUtils.class.getSimpleName();

    // 缓存大小
    private static      int sBufferSize = 8192;
    // 无数据读取
    public static final int EOF         = -1;

    /**
     * 设置缓冲区的大小, 默认大小等于 8192 字节
     * @param bufferSize 缓冲 Buffer 大小
     */
    public static void setBufferSize(final int bufferSize) {
        sBufferSize = bufferSize;
    }

    /**
     * 获取输入流
     * @param filePath 文件路径
     * @return {@link FileInputStream}
     */
    public static FileInputStream getFileInputStream(final String filePath) {
        return getFileInputStream(FileUtils.getFile(filePath));
    }

    /**
     * 获取输入流
     * @param file 文件
     * @return {@link FileInputStream}
     */
    public static FileInputStream getFileInputStream(final File file) {
        if (file == null) return null;
        try {
            return new FileInputStream(file);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getFileInputStream");
        }
        return null;
    }

    /**
     * 获取输出流
     * @param filePath 文件路径
     * @return {@link FileOutputStream}
     */
    public static FileOutputStream getFileOutputStream(final String filePath) {
        return getFileOutputStream(FileUtils.getFile(filePath));
    }

    /**
     * 获取输出流
     * @param filePath 文件路径
     * @param append   是否追加到结尾
     * @return {@link FileOutputStream}
     */
    public static FileOutputStream getFileOutputStream(
            final String filePath,
            final boolean append
    ) {
        return getFileOutputStream(FileUtils.getFile(filePath), append);
    }

    /**
     * 获取输出流
     * @param file 文件
     * @return {@link FileOutputStream}
     */
    public static FileOutputStream getFileOutputStream(final File file) {
        return getFileOutputStream(file, false);
    }

    /**
     * 获取输出流
     * @param file   文件
     * @param append 是否追加到结尾
     * @return {@link FileOutputStream}
     */
    public static FileOutputStream getFileOutputStream(
            final File file,
            final boolean append
    ) {
        if (file == null) return null;
        try {
            return new FileOutputStream(file, append);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getFileOutputStream");
        }
        return null;
    }

    // =

    /**
     * 通过输入流写入文件
     * @param filePath    文件路径
     * @param inputStream {@link InputStream}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean writeFileFromIS(
            final String filePath,
            final InputStream inputStream
    ) {
        return writeFileFromIS(FileUtils.getFileByPath(filePath), inputStream, false);
    }

    /**
     * 通过输入流写入文件
     * @param filePath    文件路径
     * @param inputStream {@link InputStream}
     * @param append      是否追加到结尾
     * @return {@code true} success, {@code false} fail
     */
    public static boolean writeFileFromIS(
            final String filePath,
            final InputStream inputStream,
            final boolean append
    ) {
        return writeFileFromIS(FileUtils.getFileByPath(filePath), inputStream, append);
    }

    /**
     * 通过输入流写入文件
     * @param file        文件
     * @param inputStream {@link InputStream}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean writeFileFromIS(
            final File file,
            final InputStream inputStream
    ) {
        return writeFileFromIS(file, inputStream, false);
    }

    /**
     * 通过输入流写入文件
     * @param file        文件
     * @param inputStream {@link InputStream}
     * @param append      是否追加到结尾
     * @return {@code true} success, {@code false} fail
     */
    public static boolean writeFileFromIS(
            final File file,
            final InputStream inputStream,
            final boolean append
    ) {
        if (inputStream == null || !FileUtils.createOrExistsFile(file)) return false;
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append));
            byte[] data = new byte[sBufferSize];
            int    len;
            while ((len = inputStream.read(data, 0, sBufferSize)) != EOF) {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            JCLogUtils.eTag(TAG, e, "writeFileFromIS");
            return false;
        } finally {
            CloseUtils.closeIOQuietly(inputStream, os);
        }
    }

    /**
     * 通过字节流写入文件
     * @param filePath 文件路径
     * @param bytes    byte[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean writeFileFromBytesByStream(
            final String filePath,
            final byte[] bytes
    ) {
        return writeFileFromBytesByStream(FileUtils.getFileByPath(filePath), bytes, false);
    }

    /**
     * 通过字节流写入文件
     * @param filePath 文件路径
     * @param bytes    byte[]
     * @param append   是否追加到结尾
     * @return {@code true} success, {@code false} fail
     */
    public static boolean writeFileFromBytesByStream(
            final String filePath,
            final byte[] bytes,
            final boolean append
    ) {
        return writeFileFromBytesByStream(FileUtils.getFileByPath(filePath), bytes, append);
    }

    /**
     * 通过字节流写入文件
     * @param file  文件
     * @param bytes byte[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean writeFileFromBytesByStream(
            final File file,
            final byte[] bytes
    ) {
        return writeFileFromBytesByStream(file, bytes, false);
    }

    /**
     * 通过字节流写入文件
     * @param file   文件
     * @param bytes  byte[]
     * @param append 是否追加到结尾
     * @return {@code true} success, {@code false} fail
     */
    public static boolean writeFileFromBytesByStream(
            final File file,
            final byte[] bytes,
            final boolean append
    ) {
        if (bytes == null || !FileUtils.createOrExistsFile(file)) return false;
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file, append));
            bos.write(bytes);
            return true;
        } catch (IOException e) {
            JCLogUtils.eTag(TAG, e, "writeFileFromBytesByStream");
            return false;
        } finally {
            CloseUtils.closeIOQuietly(bos);
        }
    }

    /**
     * 通过 FileChannel 把字节流写入文件
     * @param filePath 文件路径
     * @param bytes    byte[]
     * @param isForce  是否强制写入
     * @return {@code true} success, {@code false} fail
     */
    public static boolean writeFileFromBytesByChannel(
            final String filePath,
            final byte[] bytes,
            final boolean isForce
    ) {
        return writeFileFromBytesByChannel(FileUtils.getFileByPath(filePath), bytes, false, isForce);
    }

    /**
     * 通过 FileChannel 把字节流写入文件
     * @param filePath 文件路径
     * @param bytes    byte[]
     * @param append   是否追加到结尾
     * @param isForce  是否强制写入
     * @return {@code true} success, {@code false} fail
     */
    public static boolean writeFileFromBytesByChannel(
            final String filePath,
            final byte[] bytes,
            final boolean append,
            final boolean isForce
    ) {
        return writeFileFromBytesByChannel(FileUtils.getFileByPath(filePath), bytes, append, isForce);
    }

    /**
     * 通过 FileChannel 把字节流写入文件
     * @param file    文件
     * @param bytes   byte[]
     * @param isForce 是否强制写入
     * @return {@code true} success, {@code false} fail
     */
    public static boolean writeFileFromBytesByChannel(
            final File file,
            final byte[] bytes,
            final boolean isForce
    ) {
        return writeFileFromBytesByChannel(file, bytes, false, isForce);
    }

    /**
     * 通过 FileChannel 把字节流写入文件
     * @param file    文件
     * @param bytes   byte[]
     * @param append  是否追加到结尾
     * @param isForce 是否强制写入
     * @return {@code true} success, {@code false} fail
     */
    public static boolean writeFileFromBytesByChannel(
            final File file,
            final byte[] bytes,
            final boolean append,
            final boolean isForce
    ) {
        if (bytes == null || !FileUtils.createOrExistsFile(file)) return false;
        FileChannel fc = null;
        try {
            fc = new FileOutputStream(file, append).getChannel();
            fc.position(fc.size());
            fc.write(ByteBuffer.wrap(bytes));
            if (isForce) fc.force(true);
            return true;
        } catch (IOException e) {
            JCLogUtils.eTag(TAG, e, "writeFileFromBytesByChannel");
            return false;
        } finally {
            CloseUtils.closeIOQuietly(fc);
        }
    }

    /**
     * 通过 MappedByteBuffer 把字节流写入文件
     * @param filePath 文件路径
     * @param bytes    byte[]
     * @param isForce  是否强制写入
     * @return {@code true} success, {@code false} fail
     */
    public static boolean writeFileFromBytesByMap(
            final String filePath,
            final byte[] bytes,
            final boolean isForce
    ) {
        return writeFileFromBytesByMap(filePath, bytes, false, isForce);
    }

    /**
     * 通过 MappedByteBuffer 把字节流写入文件
     * @param filePath 文件路径
     * @param bytes    byte[]
     * @param append   是否追加到结尾
     * @param isForce  是否强制写入
     * @return {@code true} success, {@code false} fail
     */
    public static boolean writeFileFromBytesByMap(
            final String filePath,
            final byte[] bytes,
            final boolean append,
            final boolean isForce
    ) {
        return writeFileFromBytesByMap(FileUtils.getFileByPath(filePath), bytes, append, isForce);
    }

    /**
     * 通过 MappedByteBuffer 把字节流写入文件
     * @param file    文件
     * @param bytes   byte[]
     * @param isForce 是否强制写入
     * @return {@code true} success, {@code false} fail
     */
    public static boolean writeFileFromBytesByMap(
            final File file,
            final byte[] bytes,
            final boolean isForce
    ) {
        return writeFileFromBytesByMap(file, bytes, false, isForce);
    }

    /**
     * 通过 MappedByteBuffer 把字节流写入文件
     * @param file    文件
     * @param bytes   byte[]
     * @param append  是否追加到结尾
     * @param isForce 是否强制写入
     * @return {@code true} success, {@code false} fail
     */
    public static boolean writeFileFromBytesByMap(
            final File file,
            final byte[] bytes,
            final boolean append,
            final boolean isForce
    ) {
        if (bytes == null || !FileUtils.createOrExistsFile(file)) return false;
        FileChannel fc = null;
        try {
            fc = new FileOutputStream(file, append).getChannel();
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, fc.size(), bytes.length);
            mbb.put(bytes);
            if (isForce) mbb.force();
            return true;
        } catch (IOException e) {
            JCLogUtils.eTag(TAG, e, "writeFileFromBytesByMap");
            return false;
        } finally {
            CloseUtils.closeIOQuietly(fc);
        }
    }

    /**
     * 通过字符串写入文件
     * @param filePath 文件路径
     * @param content  写入内容
     * @return {@code true} success, {@code false} fail
     */
    public static boolean writeFileFromString(
            final String filePath,
            final String content
    ) {
        return writeFileFromString(FileUtils.getFileByPath(filePath), content, false);
    }

    /**
     * 通过字符串写入文件
     * @param filePath 文件路径
     * @param content  写入内容
     * @param append   是否追加到结尾
     * @return {@code true} success, {@code false} fail
     */
    public static boolean writeFileFromString(
            final String filePath,
            final String content,
            final boolean append
    ) {
        return writeFileFromString(FileUtils.getFileByPath(filePath), content, append);
    }

    /**
     * 通过字符串写入文件
     * @param file    文件
     * @param content 写入内容
     * @return {@code true} success, {@code false} fail
     */
    public static boolean writeFileFromString(
            final File file,
            final String content
    ) {
        return writeFileFromString(file, content, false);
    }

    /**
     * 通过字符串写入文件
     * @param file    文件
     * @param content 写入内容
     * @param append  是否追加到结尾
     * @return {@code true} success, {@code false} fail
     */
    public static boolean writeFileFromString(
            final File file,
            final String content,
            final boolean append
    ) {
        if (content == null || !FileUtils.createOrExistsFile(file)) return false;
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, append));
            bw.write(content);
            return true;
        } catch (IOException e) {
            JCLogUtils.eTag(TAG, e, "writeFileFromString");
            return false;
        } finally {
            CloseUtils.closeIOQuietly(bw);
        }
    }

    // =============
    // = 读写分界线 =
    // =============

    /**
     * 读取文件内容, 返回换行 List
     * @param filePath 文件路径
     * @return 换行 {@link List<String>}
     */
    public static List<String> readFileToList(final String filePath) {
        return readFileToList(FileUtils.getFileByPath(filePath), null);
    }

    /**
     * 读取文件内容, 返回换行 List
     * @param filePath    文件路径
     * @param charsetName 字符编码
     * @return 换行 {@link List<String>}
     */
    public static List<String> readFileToList(
            final String filePath,
            final String charsetName
    ) {
        return readFileToList(FileUtils.getFileByPath(filePath), charsetName);
    }

    /**
     * 读取文件内容, 返回换行 List
     * @param file 文件
     * @return 换行 {@link List<String>}
     */
    public static List<String> readFileToList(final File file) {
        return readFileToList(file, 0, Integer.MAX_VALUE, null);
    }

    /**
     * 读取文件内容, 返回换行 List
     * @param file        文件
     * @param charsetName 字符编码
     * @return 换行 {@link List<String>}
     */
    public static List<String> readFileToList(
            final File file,
            final String charsetName
    ) {
        return readFileToList(file, 0, Integer.MAX_VALUE, charsetName);
    }

    /**
     * 读取文件内容, 返回换行 List
     * @param filePath 文件路径
     * @param start    开始位置
     * @param end      结束位置
     * @return 换行 {@link List<String>}
     */
    public static List<String> readFileToList(
            final String filePath,
            final int start,
            final int end
    ) {
        return readFileToList(FileUtils.getFileByPath(filePath), start, end, null);
    }

    /**
     * 读取文件内容, 返回换行 List
     * @param filePath    文件路径
     * @param start       开始位置
     * @param end         结束位置
     * @param charsetName 字符编码
     * @return 换行 {@link List<String>}
     */
    public static List<String> readFileToList(
            final String filePath,
            final int start,
            final int end,
            final String charsetName
    ) {
        return readFileToList(FileUtils.getFileByPath(filePath), start, end, charsetName);
    }

    /**
     * 读取文件内容, 返回换行 List
     * @param file  文件
     * @param start 开始位置
     * @param end   结束位置
     * @return 换行 {@link List<String>}
     */
    public static List<String> readFileToList(
            final File file,
            final int start,
            final int end
    ) {
        return readFileToList(file, start, end, null);
    }

    /**
     * 读取文件内容, 返回换行 List
     * @param file        文件
     * @param start       开始位置
     * @param end         结束位置
     * @param charsetName 字符编码
     * @return 换行 {@link List<String>}
     */
    public static List<String> readFileToList(
            final File file,
            final int start,
            final int end,
            final String charsetName
    ) {
        if (!FileUtils.isFileExists(file)) return null;
        if (start > end) return null;
        BufferedReader br = null;
        try {
            String       line;
            int          curLine = 1;
            List<String> list    = new ArrayList<>();
            if (StringUtils.isSpace(charsetName)) {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } else {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            }
            while ((line = br.readLine()) != null) {
                if (curLine > end) break;
                if (start <= curLine && curLine <= end) list.add(line);
                ++curLine;
            }
            return list;
        } catch (IOException e) {
            JCLogUtils.eTag(TAG, e, "readFileToList");
            return null;
        } finally {
            CloseUtils.closeIOQuietly(br);
        }
    }

    // =

    /**
     * 读取文件内容, 返回字符串
     * @param filePath 文件路径
     * @return 文件内容字符串
     */
    public static String readFileToString(final String filePath) {
        return readFileToString(FileUtils.getFileByPath(filePath), null);
    }

    /**
     * 读取文件内容, 返回字符串
     * @param filePath    文件路径
     * @param charsetName 字符编码
     * @return 文件内容字符串
     */
    public static String readFileToString(
            final String filePath,
            final String charsetName
    ) {
        return readFileToString(FileUtils.getFileByPath(filePath), charsetName);
    }

    /**
     * 读取文件内容, 返回字符串
     * @param file 文件
     * @return 文件内容字符串
     */
    public static String readFileToString(final File file) {
        return readFileToString(file, null);
    }

    /**
     * 读取文件内容, 返回字符串
     * @param file        文件
     * @param charsetName 字符编码
     * @return 文件内容字符串
     */
    public static String readFileToString(
            final File file,
            final String charsetName
    ) {
        if (!FileUtils.isFileExists(file)) return null;
        BufferedReader br = null;
        try {
            StringBuilder builder = new StringBuilder();
            if (StringUtils.isSpace(charsetName)) {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } else {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            }
            String line;
            if ((line = br.readLine()) != null) {
                builder.append(line);
                while ((line = br.readLine()) != null) {
                    builder.append(DevFinal.NEW_LINE_STR).append(line);
                }
            }
            return builder.toString();
        } catch (IOException e) {
            JCLogUtils.eTag(TAG, e, "readFileToString");
            return null;
        } finally {
            CloseUtils.closeIOQuietly(br);
        }
    }

    /**
     * 读取文件内容, 返回 byte[]
     * @param filePath 文件路径
     * @return 文件内容 byte[]
     */
    public static byte[] readFileToBytesByStream(final String filePath) {
        return readFileToBytesByStream(FileUtils.getFileByPath(filePath));
    }

    /**
     * 读取文件内容, 返回 byte[]
     * @param file 文件
     * @return 文件内容 byte[]
     */
    public static byte[] readFileToBytesByStream(final File file) {
        if (!FileUtils.isFileExists(file)) return null;
        FileInputStream       fis  = null;
        ByteArrayOutputStream baos = null;
        try {
            fis = new FileInputStream(file);
            baos = new ByteArrayOutputStream();
            byte[] b = new byte[sBufferSize];
            int    len;
            while ((len = fis.read(b, 0, sBufferSize)) != EOF) {
                baos.write(b, 0, len);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            JCLogUtils.eTag(TAG, e, "readFileToBytesByStream");
            return null;
        } finally {
            CloseUtils.closeIOQuietly(fis, baos);
        }
    }

    /**
     * 通过 FileChannel, 读取文件内容, 返回 byte[]
     * @param filePath 文件路径
     * @return 文件内容 byte[]
     */
    public static byte[] readFileToBytesByChannel(final String filePath) {
        return readFileToBytesByChannel(FileUtils.getFileByPath(filePath));
    }

    /**
     * 通过 FileChannel, 读取文件内容, 返回 byte[]
     * @param file 文件
     * @return 文件内容 byte[]
     */
    public static byte[] readFileToBytesByChannel(final File file) {
        if (!FileUtils.isFileExists(file)) return null;
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(file, "r").getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) fc.size());
            while (true) {
                if (!((fc.read(byteBuffer)) > 0)) break;
            }
            return byteBuffer.array();
        } catch (IOException e) {
            JCLogUtils.eTag(TAG, e, "readFileToBytesByChannel");
            return null;
        } finally {
            CloseUtils.closeIOQuietly(fc);
        }
    }

    /**
     * 通过 MappedByteBuffer, 读取文件内容, 返回 byte[]
     * @param filePath 文件路径
     * @return 文件内容 byte[]
     */
    public static byte[] readFileToBytesByMap(final String filePath) {
        return readFileToBytesByMap(FileUtils.getFileByPath(filePath));
    }

    /**
     * 通过 MappedByteBuffer, 读取文件内容, 返回 byte[]
     * @param file 文件
     * @return 文件内容 byte[]
     */
    public static byte[] readFileToBytesByMap(final File file) {
        if (!FileUtils.isFileExists(file)) return null;
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(file, "r").getChannel();
            int              size   = (int) fc.size();
            MappedByteBuffer mbb    = fc.map(FileChannel.MapMode.READ_ONLY, 0, size).load();
            byte[]           result = new byte[size];
            mbb.get(result, 0, size);
            return result;
        } catch (IOException e) {
            JCLogUtils.eTag(TAG, e, "readFileToBytesByMap");
            return null;
        } finally {
            CloseUtils.closeIOQuietly(fc);
        }
    }

    // =

    /**
     * 复制 InputStream 到 OutputStream
     * @param inputStream  {@link InputStream} 读取流
     * @param outputStream {@link OutputStream} 写入流
     * @return bytes number
     */
    public static long copyLarge(
            final InputStream inputStream,
            final OutputStream outputStream
    ) {
        if (inputStream == null || outputStream == null) return -1;
        try {
            byte[] data  = new byte[sBufferSize];
            long   count = 0;
            int    n;
            while (EOF != (n = inputStream.read(data))) {
                outputStream.write(data, 0, n);
                count += n;
            }
            return count;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "copyLarge");
        } finally {
            CloseUtils.closeIOQuietly(inputStream, outputStream);
        }
        return -1;
    }
}