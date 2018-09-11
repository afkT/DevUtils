package dev.utils.common.assist;

import java.util.ArrayList;

/**
 * detail: 用以统计平均数
 * @author MaTianyu
 */
public class Averager {

    private ArrayList<Number> numList = new ArrayList<Number>();

    /**
     * 添加一个数字
     * @param num
     */
    public synchronized void add(Number num) {
        numList.add(num);
    }

    /**
     * 清除全部
     */
    public void clear() {
        numList.clear();
    }

    /**
     * 返回参与均值计算的数字个数
     * @return
     */
    public Number size() {
        return numList.size();
    }

    /**
     * 获取平均数
     * @return
     */
    public Number getAverage() {
        if (numList.size() == 0) {
            return 0;
        } else {
            Float sum = 0f;
            for (int i = 0, size = numList.size(); i < size; i++) {
                sum = sum.floatValue() + numList.get(i).floatValue();
            }
            return sum / numList.size();
        }
    }

    /**
     * 打印数字列
     * @return
     */
    public String print() {
        return "PrintList(" + size() + "): " + numList;
    }

}
