package dev.utils.common.comparator.sort;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * detail: Windows 目录资源文件名排序比较器
 * @author Ttt
 * <pre>
 *     非完全符合 Windows 目录页排序结果 ( 一定程度上相似 )
 *     用于目录页对比排序, 而非全部子目录完整路径对比
 *     <p></p>
 *     代码来源
 *     @see <a href="https://stackoverflow.com/questions/23205020/java-sort-strings-like-windows-explorer"/>
 * </pre>
 */
public class WindowsExplorerStringSimpleComparator
        implements Comparator<String> {

    @Override
    public int compare(
            String o1,
            String o2
    ) {
        if (o1 == null || o2 == null) {
            return -1;
        }
        return innerCompare(o1, o2);
    }

    // ==========
    // = 具体实现 =
    // ==========

    private final Pattern splitPattern = Pattern.compile("^(.*?)(\\d*)(?:\\.([^.]*))?$");

    private int innerCompare(
            String str1,
            String str2
    ) {
        SplitFileName data1 = getSplitFileName(str1);
        SplitFileName data2 = getSplitFileName(str2);

        // Compare the name part case insensitive.
        int result = data1.name.compareToIgnoreCase(data2.name);
        // If name is equal, then compare by number
        if (result == 0) {
            result = data1.number.compareTo(data2.number);
        }
        // If numbers are equal then compare by length text of number. This
        // is valid because it differs only by heading zeros. Longer comes first.
        if (result == 0) {
            result = -Integer.compare(data1.numberText.length(), data2.numberText.length());
        }
        // If all above is equal, compare by ext.
        if (result == 0) {
            result = data1.ext.compareTo(data2.ext);
        }
        return result;
    }

    private SplitFileName getSplitFileName(String fileName) {
        Matcher matcher = splitPattern.matcher(fileName);
        if (matcher.matches()) {
            return new SplitFileName(matcher.group(1), matcher.group(2), matcher.group(3));
        } else {
            return new SplitFileName(fileName, "", "");
        }
    }

    private static class SplitFileName {

        String name;
        Long   number;
        String numberText;
        String ext;

        public SplitFileName(
                String name,
                String numberText,
                String ext
        ) {
            this.name = name;
            if ("".equals(numberText)) {
                this.number = -1L;
            } else {
                this.number = Long.valueOf(numberText);
            }
            this.numberText = numberText;
            this.ext        = ext;
        }
    }
}