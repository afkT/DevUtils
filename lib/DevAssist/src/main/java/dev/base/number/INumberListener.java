package dev.base.number;

/**
 * detail: 数量监听事件接口
 * @author Ttt
 */
public interface INumberListener {

    /**
     * 数量准备变化通知
     * @param isAdd       是否增加
     * @param curNumber   当前数量
     * @param afterNumber 处理之后的数量
     * @return {@code true} allow, {@code false} prohibit
     */
    boolean onPrepareChanged(
            boolean isAdd,
            int curNumber,
            int afterNumber
    );

    /**
     * 数量变化通知
     * @param isAdd     是否增加
     * @param curNumber 当前数量
     */
    void onNumberChanged(
            boolean isAdd,
            int curNumber
    );
}