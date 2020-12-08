package dev.standard.sort;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * detail: DevFinal 常量单词排序
 * @author Ttt
 */
public class SortMain {

    public static void main(String[] args) {
        String              FORMAT = "public static final String %s = \"%s\";";
        List<String>        lists  = new ArrayList<>();
        Map<String, String> maps   = new HashMap<>();
        Field[]             fields = SortMain.class.getDeclaredFields(); // DevFinal
        for (Field field : fields) {
            String descriptor = Modifier.toString(field.getModifiers());
            descriptor = descriptor.equals("") == true ? "" : descriptor + " ";
            maps.put(field.getName(), descriptor);

            lists.add(field.getName());
        }
        // Collator 类是用来执行区分语言环境的 String 比较的, 这里选择使用 CHINA
        Comparator comparator = Collator.getInstance(java.util.Locale.CHINA);
        // 使根据指定比较器产生的顺序对指定对象数组进行排序
        Collections.sort(lists, comparator);

        for (String key : lists) {
            System.out.println(String.format(FORMAT, key.toUpperCase(), key.toLowerCase()));
        }
    }
}