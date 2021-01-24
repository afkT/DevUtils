package afkt.project.model.item;

/**
 * detail: Tab 模型类
 * @author Ttt
 */
public class TabItem<T> {

    // 类型
    private final int    type;
    // 标题
    private final String title;
    // 对象参数
    private       T      object;

    public TabItem(
            String title,
            int type
    ) {
        this.title = title;
        this.type = type;
    }

    public TabItem(
            String title,
            int type,
            T object
    ) {
        this.title = title;
        this.type = type;
        this.object = object;
    }

    public String getTitle() {
        return title;
    }

    public int getType() {
        return type;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T obj) {
        this.object = obj;
    }
}