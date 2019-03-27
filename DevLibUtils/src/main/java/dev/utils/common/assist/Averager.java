package dev.utils.common.assist;

import java.util.ArrayList;
import java.util.List;

/**
 * detail: 用以统计平均数
 * Created by Ttt
 */
public class Averager {

    private List<Number> mNumLists = new ArrayList<>();

    /**
     * 添加一个数字
     * @param num
     */
    public synchronized void add(Number num) {
        mNumLists.add(num);
    }

    /**
     * 清除全部
     */
    public void clear() {
        mNumLists.clear();
    }

    /**
     * 返回参与均值计算的数字个数
     * @return
     */
    public Number size() {
        return mNumLists.size();
    }

    /**
     * 获取平均数
     * @return
     */
    public Number getAverage() {
        if (mNumLists.size() == 0) {
            return 0;
        } else {
            Float sum = 0f;
            for (int i = 0, size = mNumLists.size(); i < size; i++) {
                sum = sum.floatValue() + mNumLists.get(i).floatValue();
            }
            return sum / mNumLists.size();
        }
    }

    /**
     * 打印数字列
     * @return
     */
    public String print() {
        return "printList(" + size() + "): " + mNumLists;
    }
}
