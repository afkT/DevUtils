# Shape 工具类文档

#### 使用演示类 [ShapeUse](https://github.com/afkT/DevUtils/blob/master/app/src/main/java/com/dev/utils/shape/ShapeUse.java) 介绍了配置参数及使用

> 1. https://blog.csdn.net/tanghongchang123/article/details/80283686
> 2. https://www.cnblogs.com/popfisher/p/5606690.html
> 3. https://www.cnblogs.com/dongdong230/p/4183079.html
> 4. https://www.cnblogs.com/zhongle/archive/2012/08/28/2659902.html
> 5. https://www.2cto.com/kf/201601/456024.html

#### 项目类结构

* Shape 工具类（[ShapeUtils.java](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/ShapeUtils.java)）：Shape 效果工具类, 动态使用(圆角、渐变、描边) 等

## API 文档

| 方法 | 注释 |
| :- | :- |
| getDrawable | 获取 GradientDrawable |
| setDrawable | 设置 Drawable 背景 |
| newBuilder | 创建新的 Shape Builder 对象 |
| newBuilderToLeft | 创建新的 Shape Builder 对象 |
| newBuilderToRight | 创建新的 Shape Builder 对象 |
| newBuilderToGradient | 创建渐变的 Shape Builder 对象 |
| build | 获取 Shape 工具类 |
| setRadius | 设置圆角 |
| setRadiusLeft | 设置圆角 |
| setRadiusRight | 设置圆角 |
| setCornerRadii | 内部处理方法 |
| setColor | 设置背景色(填充铺满) |
| setStroke | 设置边框颜色 |
| setSize | 设置大小 |

#### 使用方法
```java
Button vid_btn1 = null;

// 默认就设置背景色
ShapeUtils.Builder builder = new ShapeUtils.Builder();
builder.setRadiusLeft(10f).setColor(R.color.black);
BitmapUtils.setBackground(vid_btn1, builder.build().getDrawable());

// 设置点击效果
GradientDrawable drawable1 = ShapeUtils.newBuilder(10f, R.color.black).setStroke(5, R.color.green).build().getDrawable();
GradientDrawable drawable2 = ShapeUtils.newBuilder(10f, R.color.sky_blue).setStroke(5, R.color.grey).build().getDrawable();

BitmapUtils.setBackground(vid_btn1, StateListUtils.newSelector(drawable2, drawable1)); // 设置点击 View 背景变色, 不用写 shape xml 文件
vid_btn1.setTextColor(StateListUtils.createColorStateList(R.color.red, R.color.white)); // 设置点击字体变色

// 设置渐变
View vid_view1 = null;
// int[] colors = new int[]{ Color.RED, Color.BLUE, Color.GREEN };

int[] colors = new int[3];
colors[0] = ContextCompat.getColor(DevUtils.getContext(), R.color.black);
colors[1] = ContextCompat.getColor(DevUtils.getContext(), R.color.sky_blue);
colors[2] = ContextCompat.getColor(DevUtils.getContext(), R.color.orange);

// ShapeUtils.newBuilderToGradient(GradientDrawable.Orientation.BR_TL, colors).build().setDrawable(vid_view1);

GradientDrawable drawable = ShapeUtils.newBuilderToGradient(GradientDrawable.Orientation.BR_TL, colors).build().getDrawable();
// drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT); // 线性渐变，这是默认设置
// drawable.setGradientType(GradientDrawable.RADIAL_GRADIENT); // 放射性渐变，以开始色为中心。
drawable.setGradientType(GradientDrawable.SWEEP_GRADIENT); // 扫描线式的渐变。
BitmapUtils.setBackground(vid_view1, drawable);
```