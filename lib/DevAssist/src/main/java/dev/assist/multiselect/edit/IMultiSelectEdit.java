package dev.assist.multiselect.edit;

/**
 * detail: 多选编辑接口
 * @author Ttt
 * <pre>
 *     主要用于 Adapter 实现该接口, 对外支持快捷操作方法
 *     内部通过 MultiSelectListAssist、MultiSelectMapAssist 实现多选操作功能
 * </pre>
 */
public interface IMultiSelectEdit {

    // ============
    // = 编辑状态 =
    // ============

    /**
     * 是否编辑状态
     * @return {@code true} yes, {@code false} no
     */
    boolean isEditStatus();

    /**
     * 设置编辑状态
     * @param isEdit {@code true} yes, {@code false} no
     */
    void setEditStatus(boolean isEdit);

    /**
     * 切换编辑状态
     */
    void toggleEditStatus();

    // ============
    // = 选择操作 =
    // ============

    /**
     * 全选
     */
    void selectAll();

    /**
     * 清空全选 ( 非全选 )
     */
    void clearSelectAll();

    /**
     * 反选
     */
    void inverseSelect();

    // ============
    // = 判断处理 =
    // ============

    /**
     * 判断是否全选
     * @return {@code true} yes, {@code false} no
     */
    boolean isSelectAll();

    /**
     * 判断是否存在选中的数据
     * @return {@code true} yes, {@code false} no
     */
    boolean isSelect();

    /**
     * 判断是否不存在选中的数据
     * @return {@code true} yes, {@code false} no
     */
    boolean isNotSelect();
}