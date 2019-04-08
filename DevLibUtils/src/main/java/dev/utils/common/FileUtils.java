package dev.utils.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import dev.utils.JCLogUtils;

/**
 * detail: 文件操作工具类
 * Created by Ttt
 */
public final class FileUtils {

    private FileUtils() {
    }

	// 日志 TAG
	private static final String TAG = FileUtils.class.getSimpleName();
	// 换行字符串
	public static final String NEW_LINE_STR = System.getProperty("line.separator");
	// 换行字符串 - 两行
	public static final String NEW_LINE_STR_X2 = NEW_LINE_STR + NEW_LINE_STR;

	/**
	 * 判断是否为null
	 * @param str
	 * @return
	 */
	private static boolean isEmpty(final String str) {
		return (str == null || str.length() == 0);
	}

	/**
	 * 判断字符串是否为 null 或全为空白字符
	 * @param str 待校验字符串
	 * @return
	 */
	private static boolean isSpace(final String str) {
		if (str == null) return true;
		for (int i = 0, len = str.length(); i < len; ++i) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
    
	// =

	/**
	 * 获取文件 - to getFileByPath
	 * @param filePath
	 * @return
	 */
	public static File getFile(final String filePath) {
		return getFileByPath(filePath);
	}

	/**
	 * 获取文件
	 * @param filePath 文件路径
	 * @param fName 文件名
	 * @return
	 */
	public static File getFile(final String filePath, final String fName) {
		return (filePath != null && fName != null) ? new File(filePath, fName) : null;
	}

	/**
	 * 获取文件
	 * @param filePath
	 * @return
	 */
	public static File getFileByPath(final String filePath) {
		return filePath != null ? new File(filePath) : null;
	}

	/**
	 * 获取路径, 并且进行创建目录
	 * @param filePath 保存目录
	 * @param fName 文件名
	 * @return
	 */
	public static File getFileCreateFolder(final String filePath, final String fName) {
		// 防止不存在目录文件，自动创建
		createFolder(filePath);
		// 返回处理过后的File
		return getFile(filePath, fName);
	}

	/**
	 * 判断某个文件夹是否创建,未创建则创建(纯路径 - 无文件名)
	 * @param dirPath 文件夹路径 (无文件名字.后缀)
	 */
	public static boolean createFolder(final String dirPath) {
		return createFolder(getFileByPath(dirPath));
	}

	/**
	 * 判断某个文件夹是否创建,未创建则创建(纯路径 - 无文件名)
	 * @param file 文件夹路径 (无文件名字.后缀)
	 */
	public static boolean createFolder(final File file) {
		if (file != null) {
			try {
				// 当这个文件夹不存在的时候则创建文件夹
				if (!file.exists()) {
					// 允许创建多级目录
					return file.mkdirs();
					// 这个无法创建多级目录
					// rootFile.mkdir();
				}
				return true;
			} catch (Exception e) {
				JCLogUtils.eTag(TAG, e, "createFolder");
			}
		}
		return false;
	}

	/**
	 * 创建文件夹目录 - 可以传入文件名
	 * @param filePath 文件路径 + 文件名
	 * @return
	 */
	public static boolean createFolderByPath(final String filePath) {
		return createFolderByPath(getFileByPath(filePath));
	}

	/**
	 * 创建文件夹目录 - 可以传入文件名
	 * @param file
	 * @return
	 */
	public static boolean createFolderByPath(final File file) {
        // 创建文件夹 - 如果失败才创建
		if (file != null) {
		    if (file.exists()) {
		        return true;
            } else if (!file.getParentFile().mkdirs()) {
                return createFolder(file.getParent());
            }
		}
		return false;
	}

	/**
	 * 创建多个文件夹, 如果不存在则创建
	 * @param filePaths
	 */
	public static void createFolderByPaths(final String... filePaths) {
		if (filePaths != null && filePaths.length != 0) {
			for (int i = 0, len = filePaths.length; i < len; i++) {
				createFolder(filePaths[i]);
			}
		}
	}

	/**
	 * 创建多个文件夹, 如果不存在则创建
	 * @param files
	 */
	public static void createFolderByPaths(final File... files) {
		if (files != null && files.length != 0) {
			for (int i = 0, len = files.length; i < len; i++) {
				createFolder(files[i]);
			}
		}
	}

	// ==

	/**
	 * 判断目录是否存在，不存在则判断是否创建成功
	 * @param dirPath 目录路径
	 * @return true : 存在或创建成功, false : 不存在或创建失败
	 */
	public static boolean createOrExistsDir(final String dirPath) {
		return createOrExistsDir(getFileByPath(dirPath));
	}

	/**
	 * 判断目录是否存在，不存在则判断是否创建成功
	 * @param file 文件
	 * @return true : 存在或创建成功, false : 不存在或创建失败
	 */
	public static boolean createOrExistsDir(final File file) {
		// 如果存在，是目录则返回 true，是文件则返回 false，不存在则返回是否创建成功
		return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
	}

	/**
	 * 判断文件是否存在，不存在则判断是否创建成功
	 * @param filePath 文件路径
	 * @return true : 存在或创建成功, false : 不存在或创建失败
	 */
	public static boolean createOrExistsFile(final String filePath) {
		return createOrExistsFile(getFileByPath(filePath));
	}

	/**
	 * 判断文件是否存在，不存在则判断是否创建成功
	 * @param file 文件
	 * @return true : 存在或创建成功, false : 不存在或创建失败
	 */
	public static boolean createOrExistsFile(final File file) {
		if (file == null) return false;
		// 如果存在，是文件则返回 true，是目录则返回 false
		if (file.exists()) return file.isFile();
		// 判断文件是否存在, 不存在则直接返回
		if (!createOrExistsDir(file.getParentFile())) return false;
		try {
			// 存在, 则返回新的路径
			return file.createNewFile();
		} catch (IOException e) {
			JCLogUtils.eTag(TAG, e, "createOrExistsFile");
			return false;
		}
	}

	/**
	 * 判断文件是否存在，存在则在创建之前删除
	 * @param filePath
	 * @return true : 创建成功, false : 创建失败
	 */
	public static boolean createFileByDeleteOldFile(final String filePath) {
		return createFileByDeleteOldFile(getFileByPath(filePath));
	}

	/**
	 * 判断文件是否存在，存在则在创建之前删除
	 * @param file
	 * @return true : 创建成功, false : 创建失败
	 */
	public static boolean createFileByDeleteOldFile(final File file) {
		if (file == null) return false;
		// 文件存在并且删除失败返回 false
		if (file.exists() && !file.delete()) return false;
		// 创建目录失败返回 false
		if (!createOrExistsDir(file.getParentFile())) return false;
		try {
			return file.createNewFile();
		} catch (IOException e) {
			JCLogUtils.eTag(TAG, e, "createFileByDeleteOldFile");
			return false;
		}
	}

    /**
     * 获取文件路径
     * @param file
     * @return
     */
    public static String getPath(final File file) {
        return file != null ? file.getPath() : null;
    }

    /**
     * 获取文件绝对路径
     * @param file
     * @return
     */
    public static String getAbsolutePath(final File file) {
        return file != null ? file.getAbsolutePath() : null;
    }

    // ==

    /**
     * 获取文件名
     * @param file
     * @return
     */
    public static String getName(final File file) {
        return file != null ? file.getName() : null;
    }

    /**
     * 获取文件名
     * @param filePath 文件路径
     * @return
     */
    public static String getName(final String filePath) {
        return getName(filePath, "");
    }

    /**
     * 获取文件名
     * @param filePath 文件路径
     * @param dfStr
     * @return
     */
    public static String getName(final String filePath, final String dfStr) {
        if (!isSpace(filePath)) {
            return new File(filePath).getName();
        }
        return dfStr;
    }

    /**
     * 获取文件后缀名(无.,单独后缀)
     * @param file
     * @return
     */
    public static String getFileSuffix(final File file) {
        return getFileSuffix(getAbsolutePath(file));
    }

    /**
     * 获取文件后缀名(无.,单独后缀)
     * @param filePath 文件地址、文件名都行
     * @return
     */
    public static String getFileSuffix(final String filePath) {
        // 获取最后的索引
        int lastIndexOf = -1;
        // 判断是否存在
        if (filePath != null && (lastIndexOf = filePath.lastIndexOf('.')) != -1) {
            String result = filePath.substring(lastIndexOf);
            if (result.startsWith(".")) {
                return result.substring(1);
            }
            return result;
        }
        return null;
    }

    /**
     * 获取文件名(无后缀)
     * @param file
     * @return
     */
    public static String getFileNotSuffix(final File file) {
        return getFileNotSuffix(getName(file));
    }

    /**
     * 获取文件名(无后缀)
     * @param filePath
     * @return
     */
    public static String getFileNotSuffixToPath(final String filePath) {
        return getFileNotSuffix(getName(filePath));
    }

    /**
     * 获取文件名(无后缀)
     * @param fileName 文件名
     * @return
     */
    public static String getFileNotSuffix(final String fileName) {
        if (fileName != null) {
            if (fileName.lastIndexOf('.') != -1) {
                return fileName.substring(0, fileName.lastIndexOf('.'));
            } else {
                return fileName;
            }
        }
        return null;
    }

	/**
	 * 获取全路径中的不带拓展名的文件名
	 * @param file The file.
	 * @return 不带拓展名的文件名
	 */
	public static String getFileNameNoExtension(final File file) {
		if (file == null) return null;
		return getFileNameNoExtension(file.getPath());
	}

	/**
	 * 获取全路径中的不带拓展名的文件名
	 * @param filePath
	 * @return 不带拓展名的文件名
	 */
	public static String getFileNameNoExtension(final String filePath) {
		if (isSpace(filePath)) return filePath;
		int lastPoi = filePath.lastIndexOf('.');
		int lastSep = filePath.lastIndexOf(File.separator);
		if (lastSep == -1) {
			return (lastPoi == -1 ? filePath : filePath.substring(0, lastPoi));
		}
		if (lastPoi == -1 || lastSep > lastPoi) {
			return filePath.substring(lastSep + 1);
		}
		return filePath.substring(lastSep + 1, lastPoi);
	}

	/**
	 * 获取全路径中的文件拓展名
	 * @param file The file.
	 * @return 文件拓展名
	 */
	public static String getFileExtension(final File file) {
		if (file == null) return null;
		return getFileExtension(file.getPath());
	}

	/**
	 * 获取全路径中的文件拓展名
	 * @param filePath
	 * @return 文件拓展名
	 */
	public static String getFileExtension(final String filePath) {
		if (isSpace(filePath)) return filePath;
		int lastPoi = filePath.lastIndexOf('.');
		int lastSep = filePath.lastIndexOf(File.separator);
		if (lastPoi == -1 || lastSep >= lastPoi) return "";
		return filePath.substring(lastPoi + 1);
	}

    // ==

	/**
	 * 检查是否存在某个文件
	 * @param file 文件路径
	 * @return 是否存在文件
	 */
	public static boolean isFileExists(final File file) {
		return file != null && file.exists();
	}

	/**
	 * 检查是否存在某个文件
	 * @param filePath 文件路径
	 * @return 是否存在文件
	 */
	public static boolean isFileExists(final String filePath) {
		return isFileExists(getFileByPath(filePath));
	}

	/**
	 * 检查是否存在某个文件
	 * @param filePath 文件路径
	 * @param fName 文件名
	 * @return 是否存在文件
	 */
	public static boolean isFileExists(final String filePath, final String fName) {
		return filePath != null && fName != null && new File(filePath, fName).exists();
	}

	/**
	 * 判断是否文件
	 * @param filePath
	 * @return
	 */
	public static boolean isFile(final String filePath) {
		return isFile(getFileByPath(filePath));
	}

	/**
	 * 判断是否文件
	 * @param file
	 * @return
	 */
	public static boolean isFile(final File file) {
		return file != null && file.exists() && file.isFile();
	}

	/**
	 * 判断是否文件夹
	 * @param filePath
	 * @return
	 */
	public static boolean isDir(final String filePath) {
		return isDir(getFileByPath(filePath));
	}

	/**
	 * 判断是否文件夹
	 * @param file
	 * @return
	 */
	public static boolean isDir(final File file) {
		return file != null && file.exists() && file.isDirectory();
	}

	/**
	 * 判断是否隐藏文件
	 * @param filePath
	 * @return
	 */
	public static boolean isHide(final String filePath) {
		return isHide(getFileByPath(filePath));
	}

	/**
	 * 判断是否隐藏文件
	 * @param file
	 * @return
	 */
	public static boolean isHide(final File file) {
		return file != null && file.exists() && file.isHidden();
	}

    // ==

	/**
	 * 获取文件最后修改的毫秒时间戳
	 * @param filePath 文件路径
	 * @return 文件最后修改的毫秒时间戳
	 */

	public static long getFileLastModified(final String filePath) {
		return getFileLastModified(getFileByPath(filePath));
	}

	/**
	 * 获取文件最后修改的毫秒时间戳
	 * @param file 文件
	 * @return 文件最后修改的毫秒时间戳
	 */
	public static long getFileLastModified(final File file) {
		if (file == null) return 0l;
		return file.lastModified();
	}

	/**
	 * 简单获取文件编码格式
	 * @param filePath 文件路径
	 * @return 文件编码
	 */
	public static String getFileCharsetSimple(final String filePath) {
		return getFileCharsetSimple(getFileByPath(filePath));
	}

	/**
	 * 简单获取文件编码格式
	 * @param file 文件
	 * @return 文件编码
	 */
	public static String getFileCharsetSimple(final File file) {
		int p = 0;
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(file));
			p = (is.read() << 8) + is.read();
		} catch (IOException e) {
			JCLogUtils.eTag(TAG, e, "getFileCharsetSimple");
		} finally {
			CloseUtils.closeIO(is);
		}
		switch (p) {
			case 0xefbb:
				return "UTF-8";
			case 0xfffe:
				return "Unicode";
			case 0xfeff:
				return "UTF-16BE";
			default:
				return "GBK";
		}
	}

	/**
	 * 获取文件行数
	 * @param filePath
	 * @return 文件行数
	 */
	public static int getFileLines(final String filePath) {
		return getFileLines(getFileByPath(filePath));
	}

	/**
	 * 获取文件行数 => 比 readLine 要快很多
	 * @param file The file.
	 * @return 文件行数
	 */
	public static int getFileLines(final File file) {
		int count = 1;
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[1024];
			int readChars;
			if (NEW_LINE_STR.endsWith("\n")) {
				while ((readChars = is.read(buffer, 0, 1024)) != -1) {
					for (int i = 0; i < readChars; ++i) {
						if (buffer[i] == '\n') ++count;
					}
				}
			} else {
				while ((readChars = is.read(buffer, 0, 1024)) != -1) {
					for (int i = 0; i < readChars; ++i) {
						if (buffer[i] == '\r') ++count;
					}
				}
			}
		} catch (Exception e) {
			JCLogUtils.eTag(TAG, e, "getFileLines");
		} finally {
			CloseUtils.closeIO(is);
		}
		return count;
	}

	// ==

	/**
	 * 获取文件大小
	 * @param filePath
	 * @return 文件大小
	 */
	public static String getFileSize(final String filePath) {
		return getFileSize(getFileByPath(filePath));
	}

	/**
	 * 获取文件大小
	 * @param file The file.
	 * @return 文件大小
	 */
	public static String getFileSize(final File file) {
		return formatByteMemorySize(getFileLength(file));
	}

	/**
	 * 获取目录大小
	 * @param dirPath 目录路径
	 * @return 文件大小
	 */
	public static String getDirSize(final String dirPath) {
		return getDirSize(getFileByPath(dirPath));
	}

	/**
	 * 获取目录大小
	 * @param dir 目录
	 * @return 文件大小
	 */
	public static String getDirSize(final File dir) {
		return formatByteMemorySize(getDirLength(dir));
	}

	/**
	 * 获取文件大小
	 * @param filePath
	 * @return
	 */
	public static long getFileLength(final String filePath) {
		return getFileLength(getFileByPath(filePath));
	}

	/**
	 * 获取文件大小
	 * @param file
	 * @return
	 */
	public static long getFileLength(final File file) {
		return file != null ? file.length() : 0l;
	}

	/**
	 * 获取目录长度
	 * @param dirPath 目录路径
	 * @return 目录长度
	 */
	public static long getDirLength(final String dirPath) {
		return getDirLength(getFileByPath(dirPath));
	}

	/**
	 * 获取目录长度
	 * @param dir 目录
	 * @return 目录长度
	 */
	public static long getDirLength(final File dir) {
		if (!isDir(dir)) return 0;
		long len = 0;
		File[] files = dir.listFiles();
		if (files != null && files.length != 0) {
			for (File file : files) {
				if (file.isDirectory()) {
					len += getDirLength(file);
				} else {
					len += file.length();
				}
			}
		}
		return len;
	}

	/**
	 * 获取文件长度 - 网络资源
	 * @param httpUri
	 * @return 文件长度
	 */
	public static long getFileLengthNetwork(final String httpUri) {
		boolean isURL = httpUri.matches("[a-zA-z]+://[^\\s]*");
		if (isURL) {
			try {
				HttpURLConnection conn = (HttpURLConnection) new URL(httpUri).openConnection();
				conn.setRequestProperty("Accept-Encoding", "identity");
				conn.connect();
				if (conn.getResponseCode() == 200) {
					return conn.getContentLength();
				}
				return 0l;
			} catch (Exception e) {
				JCLogUtils.eTag(TAG, e, "getFileLengthNetwork");
			}
		}
		return 0l;
	}

	/**
	 * 获取全路径中的文件名
	 * @param file The file.
	 * @return 文件名
	 */
	public static String getFileName(final File file) {
		if (file == null) return null;
		return getFileName(file.getPath());
	}

	/**
	 * 获取全路径中的文件名
	 * @param filePath
	 * @return 文件名
	 */
	public static String getFileName(final String filePath) {
		if (isSpace(filePath)) return filePath;
		int lastSep = filePath.lastIndexOf(File.separator);
		return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);
	}

	/**
	 * 获取全路径中的最长目录
	 * @param file The file.
	 * @return filePath 最长目录
	 */
	public static String getDirName(final File file) {
		if (file == null) return null;
		return getDirName(file.getPath());
	}

	/**
	 * 获取全路径中的最长目录
	 * @param filePath
	 * @return filePath 最长目录
	 */
	public static String getDirName(final String filePath) {
		if (isSpace(filePath)) return filePath;
		int lastSep = filePath.lastIndexOf(File.separator);
		return lastSep == -1 ? "" : filePath.substring(0, lastSep + 1);
	}

	// ==

	/**
	 * 重命名文件 - 同个目录下, 修改文件名
	 * @param filePath 文件路径
	 * @param newName  新名称
	 * @return
	 */
	public static boolean rename(final String filePath, final String newName) {
		return rename(getFileByPath(filePath), newName);
	}

	/**
	 * 重命名文件 - 同个目录下, 修改文件名
	 * @param file 文件
	 * @param newName 新名称
	 * @return
	 */
	public static boolean rename(final File file, final String newName) {
		// 文件为空返回 false
		if (file == null) return false;
		// 文件不存在返回 false
		if (!file.exists()) return false;
		// 如果文件名没有改变返回 true
		if (newName.equals(file.getName())) return true;
		// 拼接新的文件路径
		File newFile = new File(file.getParent() + File.separator + newName);
		// 如果重命名的文件已存在返回 false
		return !newFile.exists() && file.renameTo(newFile);
	}

	// == 文件大小处理 ==

	/**
	 * 传入文件路径, 返回对应的文件大小
	 * @param filePath
	 * @return
	 */
	public static String formatFileSize(final String filePath) {
		File file = getFileByPath(filePath);
		return formatFileSize(file != null ? file.length() : 0);
	}

	/**
	 * 传入文件路径, 返回对应的文件大小
	 * @param file
	 * @return
	 */
	public static String formatFileSize(final File file) {
		return formatFileSize(file != null ? file.length() : 0);
	}

	/**
	 * 传入对应的文件大小double,返回转换后文件大小
	 * @param fileS 返回String文件大小
	 * @return
	 */
	public static String formatFileSize(final double fileS) {
		// 转换文件大小
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS <= 0) {
			fileSizeString = "0B";
		} else if (fileS < 1024) {
			fileSizeString = df.format(fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format(fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format(fileS / 1048576) + "MB";
		} else if (fileS < 1099511627776d) {
			fileSizeString = df.format(fileS / 1073741824) + "GB";
		} else {
			fileSizeString = df.format(fileS / 1099511627776d) + "TB";
		}
		return fileSizeString;
	}

	/**
	 * 字节数转合适内存大小 保留 3 位小数 (%.位数f)
	 * @param byteNum 字节数
	 * @return 合适内存大小
	 */
	public static String formatByteMemorySize(final double byteNum) {
		if (byteNum < 0) {
			return "0B";
		} else if (byteNum < 1024) {
			return String.format("%.3fB", (double) byteNum);
		} else if (byteNum < 1048576) {
			return String.format("%.3fKB", (double) byteNum / 1024);
		} else if (byteNum < 1073741824) {
			return String.format("%.3fMB", (double) byteNum / 1048576);
		} else if (byteNum < 1099511627776d) {
			return String.format("%.3fGB", (double) byteNum / 1073741824);
		} else {
			return String.format("%.3fTB", (double) byteNum / 1099511627776d);
		}
	}

	/**
	 * 字节数转合适内存大小 保留 number 位小数 (%.位数f)
	 * @param number 字节数
	 * @param byteNum 字节数
	 * @return 合适内存大小
	 */
	public static String formatByteMemorySize(final int number, final double byteNum) {
		if (byteNum < 0) {
			return "0B";
		} else if (byteNum < 1024) {
			return String.format("%." + number + "fB", (double) byteNum);
		} else if (byteNum < 1048576) {
			return String.format("%." + number + "fKB", (double) byteNum / 1024);
		} else if (byteNum < 1073741824) {
			return String.format("%." + number + "fMB", (double) byteNum / 1048576);
		} else if (byteNum < 1099511627776d) {
			return String.format("%." + number + "fGB", (double) byteNum / 1073741824);
		} else {
			return String.format("%." + number + "fTB", (double) byteNum / 1099511627776d);
		}
	}

	// == 获取文件MD5值 ==

	/**
	 * 获取文件的 MD5 校验码
	 * @param filePath 文件路径
	 * @return 文件的 MD5 校验码
	 */
	public static String getFileMD5ToString(final String filePath) {
		return getFileMD5ToString(getFileByPath(filePath));
	}

	/**
	 * 获取文件的 MD5 校验码
	 * @param file 文件
	 * @return 文件的 MD5 校验码
	 */
	public static String getFileMD5ToString(final File file) {
		try {
			return toHexString(getFileMD5(file), HEX_DIGITS);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 获取文件的 MD5 校验码
	 * @param filePath 文件路径
	 * @return 文件的 MD5 校验码
	 */
	public static byte[] getFileMD5(final String filePath) {
		return getFileMD5(getFileByPath(filePath));
	}

	/**
	 * 获取文件的 MD5 校验码
	 * @param file 文件
	 * @return 文件的 MD5 校验码
	 */
	public static byte[] getFileMD5(final File file) {
		if (file == null) return null;
		DigestInputStream dis = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			MessageDigest md = MessageDigest.getInstance("MD5");
			dis = new DigestInputStream(fis, md);
			byte[] buffer = new byte[1024 * 256];
			while (true) {
				if (!(dis.read(buffer) > 0)) break;
			}
			md = dis.getMessageDigest();
			return md.digest();
		} catch (NoSuchAlgorithmException | IOException e) {
			JCLogUtils.eTag(TAG, e, "getFileMD5");
		} finally {
			CloseUtils.closeIO(dis);
		}
		return null;
	}

	// ==

	/**
	 * 获取文件MD5值 - 小写
	 * @param filePath 文件地址
	 * @return
	 */
	public static String getFileMD5ToString2(final String filePath) {
		return getFileMD5ToString2(getFileByPath(filePath));
	}

	/**
	 * 获取文件MD5值 - 小写
	 * @param file 文件地址
	 * @return
	 */
	public static String getFileMD5ToString2(final File file) {
		try {
			InputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			int numRead = 0;
			while ((numRead = fis.read(buffer)) > 0) {
				md5.update(buffer, 0, numRead);
			}
			fis.close();
			return toHexString(md5.digest(), HEX_DIGITS);
		} catch (Exception e) {
			JCLogUtils.eTag(TAG, e, "getFileMD5ToString2");
		}
		return null;
	}

	// 小写
	private static final char HEX_DIGITS[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
//	// 大写
//	private static final char HEX_DIGITS_UPPER[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

	/**
	 * 进行转换
	 * @param data
	 * @param hexDigits
	 * @return
	 */
	private static String toHexString(final byte[] data, final char[] hexDigits) {
		if (data == null || hexDigits == null) return null;
		try {
			StringBuilder builder = new StringBuilder(data.length * 2);
			for (int i = 0, len = data.length; i < len; i++) {
				builder.append(hexDigits[(data[i] & 0xf0) >>> 4]);
				builder.append(hexDigits[data[i] & 0x0f]);
			}
			return builder.toString();
		} catch (Exception e) {
			JCLogUtils.eTag(TAG, e, "toHexString");
		}
		return null;
	}

	// ==============
	// == 文件操作 ==
	// ==============

	/**
	 * 删除文件
	 * @param filePath
	 * @return
	 */
	public static boolean deleteFile(final String filePath) {
		return deleteFile(getFileByPath(filePath));
	}
	
	/**
	 * 删除文件
	 * @param file
	 * @return
	 */
	public static boolean deleteFile(final File file) {
		// 文件存在，并且不是目录文件,则直接删除
		if (file != null && file.exists() && !file.isDirectory()) {
			return file.delete();
		}
		return false;
	}

	/**
	 * 删除多个文件
	 * @param filePaths
	 * @return
	 */
	public static void deleteFiles(final String... filePaths) {
		if (filePaths != null && filePaths.length != 0) {
			for (int i = 0, len = filePaths.length; i < len; i++) {
				deleteFile(filePaths[i]);
			}
		}
	}

	/**
	 * 删除多个文件
	 * @param files
	 * @return
	 */
	public static void deleteFiles(final File... files) {
		if (files != null && files.length != 0) {
			for (int i = 0, len = files.length; i < len; i++) {
				deleteFile(files[i]);
			}
		}
	}

	// =

	/**
	 * 删除文件夹
	 * @param filePath
	 * @return
	 */
	public static boolean deleteFolder(final String filePath) {
		return deleteFolder(getFileByPath(filePath));
	}

	/**
	 * 删除文件夹
	 * @param file
	 * @return
	 */
	public static boolean deleteFolder(final File file) {
		if (file != null) {
			try {
				// 文件存在，并且不是目录文件,则直接删除
				if (file.exists()) {
					if (file.isDirectory()) { // 属于文件目录
						File[] files = file.listFiles();
						if (null == files || files.length == 0) {
							file.delete();
						}
						for (File f : files) {
							deleteFolder(f.getPath());
						}
						return file.delete();
					} else { // 属于文件
						return deleteFile(file);
					}
				}
			} catch (Exception e) {
				JCLogUtils.eTag(TAG, e, "deleteFolder");
			}
		}
		return false;
	}

	/**
	 * 保存文件
	 * @param file
	 * @param bytes 保存内容
	 * @return 是否保存成功
	 */
	public static boolean saveFile(final File file, final byte[] bytes) {
		if (bytes != null && file != null) {
			try {
				// 防止文件没创建
				createFolder(getDirName(file));
				// 保存内容到一个文件
				FileOutputStream fos = new FileOutputStream(file);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				bos.write(bytes);
				bos.close();
				fos.close();
				return true;
			} catch (Exception e) {
				JCLogUtils.eTag(TAG, e, "saveFile");
			}
		}
		return false;
	}

	/**
	 * 保存文件
	 * @param filePath 保存路径
	 * @param fName 文件名.后缀
	 * @param bytes 保存内容
	 * @return 是否保存成功
	 */
	public static boolean saveFile(final String filePath, final String fName, final byte[] bytes) {
		if (bytes != null && filePath != null && fName != null) {
			try {
				// 防止文件没创建
				createFolder(filePath);
				// 保存路径
				File sFile = new File(filePath, fName);
				// 保存内容到一个文件
				FileOutputStream fos = new FileOutputStream(sFile);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				bos.write(bytes);
				bos.close();
				fos.close();
				return true;
			} catch (Exception e) {
				JCLogUtils.eTag(TAG, e, "saveFile");
			}
		}
		return false;
	}
	
	/**
	 * 保存文件
	 * @param filePath 保存路径
	 * @param fName 文件名.后缀
	 * @param content 保存内容
	 * @return 是否保存成功
	 */
	public static boolean saveFile(final String filePath, final String fName, final String content) {
		if (content != null && filePath != null && fName != null) {
			try {
				// 防止文件没创建
				createFolder(filePath);
				// 保存路径
				File sFile = new File(filePath, fName);
				// 保存内容到一个文件
				FileOutputStream fos = new FileOutputStream(sFile);
				fos.write(content.getBytes());
				fos.close();
				return true;
			} catch (Exception e) {
				JCLogUtils.eTag(TAG, e, "saveFile");
			}
		}
		return false;
	}
	
	/**
	 * 保存文件
	 * @param filePath 保存路径
	 * @param fName 文件名.后缀
	 * @param content 保存内容
	 * @param coding 编码
	 * @return 是否保存成功
	 */
	public static boolean saveFile(final String filePath, final String fName, final String content, final String coding) {
		if (content != null && filePath != null && fName != null) {
			try {
				// 防止文件没创建
				createFolder(filePath);
				// 保存路径
				File sFile = new File(filePath, fName);
				// 保存内容到一个文件
				FileOutputStream fos = new FileOutputStream(sFile);
				Writer out;
				if (coding != null) {
					out = new OutputStreamWriter(fos, coding);
				} else {
					out = new OutputStreamWriter(fos);
				}
				out.write(content);
				out.close();
				fos.close();
				return true;
			} catch (Exception e) {
				JCLogUtils.eTag(TAG, e, "saveFile");
			}
		}
		return false;
	}

	/**
	 * 追加文件：使用FileWriter
	 * @param filePath 文件地址
	 * @param content 追加内容
	 */
	public static void appendFile(final String filePath, final String content) {
		if (filePath == null || content == null) {
			return;
		}
		File file = new File(filePath);
		if (!file.exists()) { // 如果文件不存在,则跳过
			return;
		}
		FileWriter writer = null;
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			writer = new FileWriter(file, true);
			writer.write(content);
		} catch (Exception e) {
			JCLogUtils.eTag(TAG, e, "appendFile");
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
		}
	}

	// ==
	
	/**
	 * 读取文件
	 * @param filePath
	 * @return
	 */
    public static byte[] readFileBytes(final String filePath) {
        return readFileBytes(getFileByPath(filePath));
    }

	/**
	 * 读取文件
	 * @param file
	 * @return
	 */
	public static byte[] readFileBytes(final File file) {
		if (file != null && file.exists()) {
			try {
				FileInputStream fin = new FileInputStream(file);
				int length = fin.available();
				byte[] buffer = new byte[length];
				fin.read(buffer);
				fin.close();
				return buffer;
			} catch (Exception e) {
				JCLogUtils.eTag(TAG, e, "readFileBytes");
			}
		}
		return null;
	}
    
    /**
	 * 读取文件
	 * @return
	 */
    public static byte[] readFileBytes(final InputStream iStream) {
        if (iStream != null) {
			try {
				int length = iStream.available();
				byte[] buffer = new byte[length];
				iStream.read(buffer);
				iStream.close();
				return buffer;
			} catch (Exception e) {
				JCLogUtils.eTag(TAG, e, "readFileBytes");
			}
		}
        return null;
    }

	/**
	 * 读取文件
	 * @param filePath
	 * @return
	 */
	public static String readFile(final String filePath) {
		return readFile(getFileByPath(filePath));
	}

	/**
	 * 读取文件
	 * @param file
	 * @return
	 */
	public static String readFile(final File file) {
		if (file != null && file.exists()) {
			try {
				return readFile(new FileInputStream(file));
			} catch (Exception e) {
				JCLogUtils.eTag(TAG, e, "readFile");
			}
		}
		return null;
	}

	/**
	 * 读取文件
	 * @param iStream -> new FileInputStream(path)
	 * @return
	 */
	public static String readFile(final InputStream iStream) {
		if (iStream != null) {
			try {
				InputStreamReader isR = new InputStreamReader(iStream);
				BufferedReader br = new BufferedReader(isR);
				StringBuilder sBuilder = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sBuilder.append(line);
				}
				isR.close();
				br.close();
				return sBuilder.toString();
			} catch (Exception e) {
				JCLogUtils.eTag(TAG, e, "readFile");
			}
		}
		return null;
	}
    
    /**
	 * 读取文件
	 * @param iStream -> new FileInputStream(path)
	 * @param encode 编码
	 * @return
	 */
	public static String readFile(final InputStream iStream, final String encode) {
		if (iStream != null) {
			try {
				InputStreamReader isR = null;
				if (encode != null) {
					new InputStreamReader(iStream, encode);
				} else {
					new InputStreamReader(iStream);
				}
				BufferedReader br = new BufferedReader(isR);
				StringBuilder sBuilder = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sBuilder.append(line);
				}
				isR.close();
				br.close();
				return sBuilder.toString();
			} catch (Exception e) {
				JCLogUtils.eTag(TAG, e, "readFile");
			}
		}
		return null;
	}

	// ==

	/**
	 * 复制单个文件
	 * @param inputStream 文件流(被复制)
	 * @param destFilePath 目标文件地址
	 * @param overlay 如果目标文件存在，是否覆盖
	 * @return 如果复制成功返回true，否则返回false
	 */
	public static boolean copyFile(final InputStream inputStream, final String destFilePath, final boolean overlay) {
		if (inputStream == null || destFilePath == null) {
			return false;
		}
		// 判断目标文件是否存在
		File destFile = new File(destFilePath);
        // 如果属于文件夹则跳过
        if (destFile.isDirectory()) {
            return false;
        }
		if (destFile.exists()) {
			// 如果目标文件存在并允许覆盖
			if (overlay) {
				// 删除已经存在的目标文件，无论目标文件是目录还是单个文件
				destFile.delete();
			}
		} else {
			// 如果目标文件所在目录不存在，则创建目录
			if (!destFile.getParentFile().exists()) {
				// 目标文件所在目录不存在
				if (!destFile.getParentFile().mkdirs()) {
					// 复制文件失败：创建目标文件所在目录失败
					return false;
				}
			}
		}
		// 复制文件
		int byteread = 0; // 读取的字节数
		InputStream in = inputStream;
		OutputStream out = null;
		try {
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];
			while ((byteread = in.read(buffer)) != -1) {
				out.write(buffer, 0, byteread);
			}
			return true;
		} catch (Exception e) {
			JCLogUtils.eTag(TAG, e, "copyFile");
			return false;
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}
	}
	
	/**
	 * 复制单个文件
	 * @param srcFilePath 待复制的文件地址
	 * @param destFilePath 目标文件地址
	 * @param overlay 如果目标文件存在，是否覆盖
	 * @return 如果复制成功返回true，否则返回false
	 */
	public static boolean copyFile(final String srcFilePath, final String destFilePath, final boolean overlay) {
		if (srcFilePath == null || destFilePath == null) {
			return false;
		}
		File srcFile = new File(srcFilePath);
		// 判断源文件是否存在
		if (!srcFile.exists()) {
			return false;
		} else if (!srcFile.isFile()) { // srcFile.isDirectory();
			return false;
		}
		// 判断目标文件是否存在
		File destFile = new File(destFilePath);
        // 如果属于文件夹则跳过
        if (destFile.isDirectory()) {
            return false;
        }
		if (destFile.exists()) {
			// 如果目标文件存在并允许覆盖
			if (overlay) {
				// 删除已经存在的目标文件，无论目标文件是目录还是单个文件
				new File(destFilePath).delete();
			}
		} else {
			// 如果目标文件所在目录不存在，则创建目录
			if (!destFile.getParentFile().exists()) {
				// 目标文件所在目录不存在
				if (!destFile.getParentFile().mkdirs()) {
					// 复制文件失败：创建目标文件所在目录失败
					return false;
				}
			}
		}
		// 复制文件
		int byteread = 0; // 读取的字节数
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];
			while ((byteread = in.read(buffer)) != -1) {
				out.write(buffer, 0, byteread);
			}
			return true;
		} catch (Exception e) {
			JCLogUtils.eTag(TAG, e, "copyFile");
			return false;
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}
	}

    /**
     * 复制文件夹
     * @param srcFolderPath 待复制的文件夹地址
     * @param destFolderPath 目标文件夹地址
     * @param overlay 如果目标文件存在，是否覆盖
     * @return 如果复制成功返回true，否则返回false
     */
	public static boolean copyFolder(final String srcFolderPath, final String destFolderPath, final boolean overlay) {
        return copyFolder(srcFolderPath, destFolderPath, srcFolderPath, overlay);
    }

    /**
     * 复制文件夹
     * @param srcFolderPath 待复制的文件夹地址
     * @param destFolderPath 目标文件夹地址
     * @param sourcePath 源文件地址
     * @param overlay 如果目标文件存在，是否覆盖
     * @return 如果复制成功返回true，否则返回false
     */
    private static boolean copyFolder(final String srcFolderPath, final String destFolderPath, final String sourcePath, boolean overlay) {
        if (srcFolderPath == null || destFolderPath == null) {
            return false;
        }
        File srcFile = new File(srcFolderPath);
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            return false;
        } else if (!srcFile.isDirectory()) { // 不属于文件夹则跳过
            return false;
        }
        // 判断目标文件是否存在
        File destFile = new File(destFolderPath);
        // 如果文件夹没创建,则创建
        if (!destFile.exists()) {
            // 允许创建多级目录
            destFile.mkdirs();
        }
        // 判断是否属于文件夹
        if (!destFile.isDirectory()) { // 不属于文件夹则跳过
            return false;
        }
        // 判断是否存在
        if (srcFile.exists()) {
            // 获取文件路径
            File[] files = srcFile.listFiles();
            // 防止不存在文件
            if (files != null && files.length != 0) {
                // 进行遍历
                for (File file : files) {
                    // 文件存在,才进行处理
                    if (file.exists()) {
                        // 属于文件夹
                        if (file.isDirectory()) {
                            copyFolder(file.getAbsolutePath(), destFolderPath, sourcePath, overlay);
                        } else { // 属于文件
                            // 复制的文件地址
                            String filePath = file.getAbsolutePath();
                            // 获取源文件地址 - 并且进行判断
                            String dealSource = new File(sourcePath).getAbsolutePath();
                            // 属于最前才进行处理
                            if (filePath.indexOf(dealSource) == 0) {
                                // 获取处理后的地址
                                dealSource = filePath.substring(dealSource.length());
                                // 获取需要复制保存的地址
                                String savePath = new File(destFolderPath, dealSource).getAbsolutePath();
                                // 进行复制文件
                                boolean isResult = copyFile(filePath, savePath, overlay);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    // --

    /**
     * 移动(剪切)文件
     * @param srcFilePath
     * @param destFilePath
     * @param overlay
     * @return
     */
    public static boolean moveFile(final String srcFilePath, final String destFilePath, final boolean overlay) {
        // 复制文件
        if (copyFile(srcFilePath, destFilePath, overlay)) {
            // 删除文件
            return deleteFile(srcFilePath);
        }
        return false;
    }

    /**
     * 移动(剪切)文件夹
     * @param srcFilePath
     * @param destFilePath
     * @param overlay
     * @return
     */
    public static boolean moveFolder(final String srcFilePath, final String destFilePath, final boolean overlay) {
        // 复制文件夹
        if (copyFolder(srcFilePath, destFilePath, overlay)) {
            // 删除文件夹
            return deleteFolder(srcFilePath);
        }
        return false;
    }

	// ===============
	// == FileUtils ==
	// ===============

	/**
	 * 复制或移动目录
	 * @param srcDirPath 源目录路径
	 * @param destDirPath 目标目录路径
	 * @param listener 是否覆盖监听器
	 * @param isMove 是否移动
	 * @return true : 复制或移动成功, false :复制或移动失败
	 */
	private static boolean copyOrMoveDir(final String srcDirPath, final String destDirPath, final OnReplaceListener listener, final boolean isMove) {
		return copyOrMoveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), listener, isMove);
	}

	/**
	 * 复制或移动目录
	 * @param srcDir 源目录
	 * @param destDir 目标目录
	 * @param listener 是否覆盖监听器
	 * @param isMove 是否移动
	 * @return true : 复制或移动成功, false :复制或移动失败
	 */
	private static boolean copyOrMoveDir(final File srcDir, final File destDir, final OnReplaceListener listener, final boolean isMove) {
		if (srcDir == null || destDir == null) return false;
		// 如果目标目录在源目录中则返回 false，看不懂的话好好想想递归怎么结束
		// srcPath : F:\\MyGithub\\AndroidUtilCode\\utilcode\\src\\test\\res
		// destPath: F:\\MyGithub\\AndroidUtilCode\\utilcode\\src\\test\\res1
		// 为防止以上这种情况出现出现误判，须分别在后面加个路径分隔符
		String srcPath = srcDir.getPath() + File.separator;
		String destPath = destDir.getPath() + File.separator;
		if (destPath.contains(srcPath)) return false;
		// 源文件不存在或者不是目录则返回 false
		if (!srcDir.exists() || !srcDir.isDirectory()) return false;
		if (destDir.exists()) {
			if (listener.onReplace()) { // 需要覆盖则删除旧目录
				if (!deleteAllInDir(destDir)) { // 删除文件失败的话返回 false
					return false;
				}
			} else { // 不需要覆盖直接返回即可 true
				return true;
			}
		}
		// 目标目录不存在返回 false
		if (!createOrExistsDir(destDir)) return false;
		File[] files = srcDir.listFiles();
		for (File file : files) {
			File oneDestFile = new File(destPath + file.getName());
			if (file.isFile()) {
				// 如果操作失败返回 false
				if (!copyOrMoveFile(file, oneDestFile, listener, isMove)) return false;
			} else if (file.isDirectory()) {
				// 如果操作失败返回 false
				if (!copyOrMoveDir(file, oneDestFile, listener, isMove)) return false;
			}
		}
		return !isMove || deleteDir(srcDir);
	}

	/**
	 * 复制或移动文件
	 * @param srcFilePath 源文件路径
	 * @param destFilePath 目标文件路径
	 * @param listener 是否覆盖监听器
	 * @param isMove 是否移动
	 * @return true : 复制或移动成功, false :复制或移动失败
	 */
	private static boolean copyOrMoveFile(final String srcFilePath, final String destFilePath, final OnReplaceListener listener, final boolean isMove) {
		return copyOrMoveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), listener, isMove);
	}

	/**
	 * 复制或移动文件
	 * @param srcFile 源文件
	 * @param destFile 目标文件
	 * @param listener 是否覆盖监听器
	 * @param isMove 是否移动
	 * @return true : 复制或移动成功, false :复制或移动失败
	 */
	private static boolean copyOrMoveFile(final File srcFile, final File destFile, final OnReplaceListener listener, final boolean isMove) {
		if (srcFile == null || destFile == null) return false;
		// 如果源文件和目标文件相同则返回 false
		if (srcFile.equals(destFile)) return false;
		// 源文件不存在或者不是文件则返回 false
		if (!srcFile.exists() || !srcFile.isFile()) return false;
		if (destFile.exists()) { // 目标文件存在
			if (listener.onReplace()) { // 需要覆盖则删除旧文件
				if (!destFile.delete()) { // 删除文件失败的话返回 false
					return false;
				}
			} else { // 不需要覆盖直接返回即可 true
				return true;
			}
		}
		// 目标目录不存在返回 false
		if (!createOrExistsDir(destFile.getParentFile())) return false;
		try {
			return FileIOUtils.writeFileFromIS(destFile, new FileInputStream(srcFile), false) && !(isMove && !deleteFile(srcFile));
		} catch (FileNotFoundException e) {
			JCLogUtils.eTag(TAG, e, "copyOrMoveFile");
			return false;
		}
	}

	/**
	 * 复制目录
	 * @param srcDirPath 源目录路径
	 * @param destDirPath 目标目录路径
	 * @param listener 是否覆盖监听器
	 * @return true : 复制成功, false :复制失败
	 */
	public static boolean copyDir(final String srcDirPath, final String destDirPath, final OnReplaceListener listener) {
		return copyDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), listener);
	}

	/**
	 * 复制目录
	 * @param srcDir 源目录
	 * @param destDir 目标目录
	 * @param listener 是否覆盖监听器
	 * @return true : 复制成功, false :复制失败
	 */
	public static boolean copyDir(final File srcDir, final File destDir, final OnReplaceListener listener) {
		return copyOrMoveDir(srcDir, destDir, listener, false);
	}

	/**
	 * 复制文件
	 * @param srcFilePath 源文件路径
	 * @param destFilePath 目标文件路径
	 * @param listener 是否覆盖监听器
	 * @return true : 复制成功, false :复制失败
	 */
	public static boolean copyFile(final String srcFilePath, final String destFilePath, final OnReplaceListener listener) {
		return copyFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), listener);
	}

	/**
	 * 复制文件
	 * @param srcFile 源文件
	 * @param destFile 目标文件
	 * @param listener 是否覆盖监听器
	 * @return true : 复制成功, false :复制失败
	 */
	public static boolean copyFile(final File srcFile, final File destFile, final OnReplaceListener listener) {
		return copyOrMoveFile(srcFile, destFile, listener, false);
	}

	/**
	 * 移动目录
	 * @param srcDirPath 源目录路径
	 * @param destDirPath 目标目录路径
	 * @param listener 是否覆盖监听器
	 * @return true : 移动成功, false :移动失败
	 */
	public static boolean moveDir(final String srcDirPath, final String destDirPath, final OnReplaceListener listener) {
		return moveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), listener);
	}

	/**
	 * 移动目录
	 * @param srcDir 源目录
	 * @param destDir 目标目录
	 * @param listener 是否覆盖监听器
	 * @return true : 移动成功, false :移动失败
	 */
	public static boolean moveDir(final File srcDir, final File destDir, final OnReplaceListener listener) {
		return copyOrMoveDir(srcDir, destDir, listener, true);
	}

	/**
	 * 移动文件
	 * @param srcFilePath 源文件路径
	 * @param destFilePath 目标文件路径
	 * @param listener 是否覆盖监听器
	 * @return true : 移动成功, false :移动失败
	 */
	public static boolean moveFile(final String srcFilePath, final String destFilePath, final OnReplaceListener listener) {
		return moveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), listener);
	}

	/**
	 * 移动文件
	 * @param srcFile 源文件
	 * @param destFile 目标文件
	 * @param listener 是否覆盖监听器
	 * @return true : 移动成功, false :移动失败
	 */
	public static boolean moveFile(final File srcFile, final File destFile, final OnReplaceListener listener) {
		return copyOrMoveFile(srcFile, destFile, listener, true);
	}

	/**
	 * 删除目录
	 * @param dirPath 目录路径
	 * @return true : 删除成功, false :删除失败
	 */
	public static boolean deleteDir(final String dirPath) {
		return deleteDir(getFileByPath(dirPath));
	}

	/**
	 * 删除目录
	 * @param dir 目录
	 * @return true : 删除成功, false :删除失败
	 */
	public static boolean deleteDir(final File dir) {
		if (dir == null) return false;
		// dir doesn't exist then return true
		if (!dir.exists()) return true;
		// dir isn't a directory then return false
		if (!dir.isDirectory()) return false;
		File[] files = dir.listFiles();
		if (files != null && files.length != 0) {
			for (File file : files) {
				if (file.isFile()) {
					if (!file.delete()) return false;
				} else if (file.isDirectory()) {
					if (!deleteDir(file)) return false;
				}
			}
		}
		return dir.delete();
	}

	/**
	 * 删除目录下所有东西
	 * @param dirPath 目录路径
	 * @return true : 删除成功, false :删除失败
	 */
	public static boolean deleteAllInDir(final String dirPath) {
		return deleteAllInDir(getFileByPath(dirPath));
	}

	/**
	 * 删除目录下所有东西
	 * @param dir 目录
	 * @return true : 删除成功, false :删除失败
	 */
	public static boolean deleteAllInDir(final File dir) {
		return deleteFilesInDirWithFilter(dir, new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return true;
			}
		});
	}

	/**
	 * 删除目录下所有文件
	 * @param dirPath 目录路径
	 * @return true : 删除成功, false :删除失败
	 */
	public static boolean deleteFilesInDir(final String dirPath) {
		return deleteFilesInDir(getFileByPath(dirPath));
	}

	/**
	 * 删除目录下所有文件
	 * @param dir 目录
	 * @return true : 删除成功, false :删除失败
	 */
	public static boolean deleteFilesInDir(final File dir) {
		return deleteFilesInDirWithFilter(dir, new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isFile();
			}
		});
	}

	/**
	 * 删除目录下所有过滤的文件
	 * @param dirPath 目录路径
	 * @param filter 过滤器
	 * @return true : 删除成功, false :删除失败
	 */
	public static boolean deleteFilesInDirWithFilter(final String dirPath, final FileFilter filter) {
		return deleteFilesInDirWithFilter(getFileByPath(dirPath), filter);
	}

	/**
	 * 删除目录下所有过滤的文件
	 * @param dir 目录
	 * @param filter 过滤器
	 * @return true : 删除成功, false :删除失败
	 */
	public static boolean deleteFilesInDirWithFilter(final File dir, final FileFilter filter) {
		if (dir == null) return false;
		// dir doesn't exist then return true
		if (!dir.exists()) return true;
		// dir isn't a directory then return false
		if (!dir.isDirectory()) return false;
		File[] files = dir.listFiles();
		if (files != null && files.length != 0) {
			for (File file : files) {
				if (filter.accept(file)) {
					if (file.isFile()) {
						if (!file.delete()) return false;
					} else if (file.isDirectory()) {
						if (!deleteDir(file)) return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * 获取目录下所有文件 - 不递归进子目录
	 * @param dirPath 目录路径
	 * @return 文件链表
	 */
	public static List<File> listFilesInDir(final String dirPath) {
		return listFilesInDir(dirPath, false);
	}

	/**
	 * 获取目录下所有文件 - 不递归进子目录
	 * @param dir 目录
	 * @return 文件链表
	 */
	public static List<File> listFilesInDir(final File dir) {
		return listFilesInDir(dir, false);
	}

	/**
	 * 获取目录下所有文件
	 * @param dirPath 目录路径
	 * @param isRecursive 是否递归进子目录
	 * @return 文件链表
	 */
	public static List<File> listFilesInDir(final String dirPath, final boolean isRecursive) {
		return listFilesInDir(getFileByPath(dirPath), isRecursive);
	}

	/**
	 * 获取目录下所有文件
	 * @param dir 目录
	 * @param isRecursive 是否递归进子目录
	 * @return 文件链表
	 */
	public static List<File> listFilesInDir(final File dir, final boolean isRecursive) {
		return listFilesInDirWithFilter(dir, new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return true;
			}
		}, isRecursive);
	}

	/**
	 * 获取目录下所有过滤的文件 - 不递归进子目录
	 * @param dirPath 目录路径
	 * @param filter  过滤器
	 * @return 文件链表
	 */
	public static List<File> listFilesInDirWithFilter(final String dirPath, final FileFilter filter) {
		return listFilesInDirWithFilter(getFileByPath(dirPath), filter, false);
	}

	/**
	 * 获取目录下所有过滤的文件 - 不递归进子目录
	 * @param dir 目录
	 * @param filter 过滤器
	 * @return 文件链表
	 */
	public static List<File> listFilesInDirWithFilter(final File dir, final FileFilter filter) {
		return listFilesInDirWithFilter(dir, filter, false);
	}

	/**
	 * 获取目录下所有过滤的文件
	 * @param dirPath 目录路径
	 * @param filter 过滤器
	 * @param isRecursive 是否递归进子目录
	 * @return 文件链表
	 */
	public static List<File> listFilesInDirWithFilter(final String dirPath, final FileFilter filter, final boolean isRecursive) {
		return listFilesInDirWithFilter(getFileByPath(dirPath), filter, isRecursive);
	}

	/**
	 * 获取目录下所有过滤的文件
	 * @param dir 目录
	 * @param filter 过滤器
	 * @param isRecursive 是否递归进子目录
	 * @return 文件链表
	 */
	public static List<File> listFilesInDirWithFilter(final File dir, final FileFilter filter, final boolean isRecursive) {
		if (!isDir(dir)) return null;
		List<File> list = new ArrayList<>();
		File[] files = dir.listFiles();
		if (files != null && files.length != 0) {
			for (File file : files) {
				if (filter.accept(file)) {
					list.add(file);
				}
				if (isRecursive && file.isDirectory()) {
					// noinspection ConstantConditions
					list.addAll(listFilesInDirWithFilter(file, filter, true));
				}
			}
		}
		return list;
	}

	/**
	 * 覆盖/替换事件
	 */
    public interface OnReplaceListener {

		/**
		 * 是否覆盖/替换文件
		 * @return
		 */
		boolean onReplace();
    }
}
