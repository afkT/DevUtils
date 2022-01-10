package dev.utils.common.comparator.sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
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
public class WindowsExplorerStringSimpleComparator2
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

    private final Pattern splitPattern = Pattern.compile("\\d+|\\.|\\s");

    private int innerCompare(
            String str1,
            String str2
    ) {
        Iterator<String> i1 = splitStringPreserveDelimiter(str1).iterator();
        Iterator<String> i2 = splitStringPreserveDelimiter(str2).iterator();
        while (true) {
            // Til here all is equal.
            if (!i1.hasNext() && !i2.hasNext()) {
                return 0;
            }
            // first has no more parts -> comes first
            if (!i1.hasNext() && i2.hasNext()) {
                return -1;
            }
            // first has more parts than i2 -> comes after
            if (i1.hasNext() && !i2.hasNext()) {
                return 1;
            }

            String data1 = i1.next();
            String data2 = i2.next();
            int    result;
            try {
                // If both datas are numbers, then compare numbers
                result = Long.compare(Long.parseLong(data1), Long.parseLong(data2));
                // If numbers are equal than longer comes first
                if (result == 0) {
                    result = -Integer.compare(data1.length(), data2.length());
                }
            } catch (NumberFormatException ex) {
                // compare text case insensitive
                result = data1.compareToIgnoreCase(data2);
            }

            if (result != 0) {
                return result;
            }
        }
    }

    private List<String> splitStringPreserveDelimiter(String str) {
        Matcher      matcher = splitPattern.matcher(str);
        List<String> list    = new ArrayList<>();
        int          pos     = 0;
        while (matcher.find()) {
            list.add(str.substring(pos, matcher.start()));
            list.add(matcher.group());
            pos = matcher.end();
        }
        list.add(str.substring(pos));
        return list;
    }
}