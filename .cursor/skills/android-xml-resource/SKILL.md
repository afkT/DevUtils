---
name: android-xml-resource
description: >-
  统一 Android XML 资源命名：layout、drawable、mipmap、color、values、id 的前缀与
  snake_case 规则；把颜色/圆角/描边/状态编码进文件名（bg_、border_、text_color_…_selector）。
  生成 selector 与 ColorStateList 时多状态写入单个文件、内联 shape、默认项放最后、按真实颜色
  hex 命名（如 text_color_333333_ffffff_selector）。在用户要求命名或重命名资源文件、生成
  selector / state color / shape / ripple / layer-list drawable、整理布局 id、
  规范 colors/dimens/styles 命名时使用。
---

# Android XML 资源命名规范（多项目通用）

让新建的 `layout`、`drawable`、`mipmap`、`color`、`values` 资源命名一致、可预测、可被工具校验。本 Skill 自包含，不依赖任何外部文档；全量表格、示例与反例见 [reference.md](reference.md)。

## 何时用本 Skill

- 新建或重命名 **资源文件**（layout / drawable / color / values 等），需要确定文件名与 `name`。
- 生成 **selector / ColorStateList / shape / ripple / layer-list / state color**。
- 整理布局内 `@+id/`、`colors`、`dimens`、`styles` 的命名。
- 判断「该不该新建 drawable/color 文件」。

## 硬约束（违反会编译失败）

1. 基于文件的资源名（`layout/`、`drawable/`、`color/`、`mipmap/`、`anim/`…目录下文件）**只能用小写 `a-z`、数字 `0-9`、下划线 `_`**；大写、空格、连字符、中文都非法。
2. 资源只能放进 **预定义类型目录**（`drawable/`、`mipmap/`、`layout/`、`values/`、`color/`、`anim/`、`menu/`、`xml/`、`raw/`、`font/`…），不能放 `res/` 根，也不能自造目录基名。
3. 配置限定符以 **短横线 `-`** 追加到目录基名（`drawable-night-hdpi`、`values-zh-rCN`、`layout-land`），并按官方优先级排序；目录不可嵌套。限定符顺序见 [reference.md](reference.md#配置限定符顺序)。

## 命名总则

1. **文件资源一律 `snake_case`**（全小写下划线）。
2. **`values/` 值名**（`name="..."`）统一 `snake_case`（`btn_submit`、`dp_12`、`color_0088ff`）；仅 `style` / `theme` 用 PascalCase + 点分（`Theme.App`、`Widget.App.Button.Primary`）。
3. **前缀表达「是什么」**：文件名以用途/控件类型前缀开头（`activity_`、`item_`、`ic_`、`bg_`…）。
4. **语义优先、缩写克制**；宁可长而清晰。
5. **模块化前缀**：若工程按模块拆资源目录（如 `res-<module>/`），文件名以模块/功能域起头（`main_tab_*`、`learning_word_*`）规避跨模块同名。

各类型的前缀表与 `name` 规则（layout / drawable 两族 / colors / dimens / styles / ids）见 [reference.md](reference.md)。

## drawable 命名两族（摘要）

按用途二选一，全表见 [reference.md § drawable 命名](reference.md#drawable-命名两族)：

- **图标 / 模块状态族**：`ic_*`、`btn_*`、`divider_*`、`<module>_..._selector`（内部用 `@mipmap/@drawable` 切换状态）。
- **值编码族**（通用背景/描边/文本色，把颜色·圆角·描边·状态编进文件名）：

```
bg_<color1>[_<color2>][_<alphaPercent>][_r<radius>][_border[_<borderColor>]][_w<width>]_<suffix>
border_<strokeColor>[_<alphaPercent>]_r<radius>[_w<width>]
text_color_<color1>_<color2>[_..]_selector
```

- 颜色 token = **6 位小写 hex 不带 `#`**（`ffffff`、`0088ff`）；按真实颜色取值，即使引用的是短别名（`@color/color_33` 仍写 `333333`）。第 1 个 = 默认/normal 态，第 2 个 = 激活态。
- alpha：`_<百分比>`（`_40` = 40% 不透明）。圆角：`r<dp>`，小数用 `_`（`r2_5` = 2.5dp，`r50` = 胶囊）。描边：`border`/`border_<色>`、线宽 `w<dp>`。后缀：`selector`/`shape`/`gradient`。

## 状态资源单文件整合（强约束 + 重点）

随状态变化的 **背景 / 文本色 / 图标** 必须用 **一个** `selector` 或 `ColorStateList` 文件表达全部 state。**禁止拆成** `xxx_normal.xml` + `xxx_pressed.xml` + `xxx_selector.xml`。

规则：

- 背景 selector：**每个 `<item>` 内联 `<shape>`**（`corners`/`solid`/`stroke`），不外链多个 shape 文件。
- 把更具体/优先的 state 写在前（`state_checked`/`state_pressed`/`state_selected`/`state_focused`…），**无 state 的默认项放最后**兜底。
- 按真实颜色 hex 命名：背景如 `bg_ffffff_0088ff_r5_selector`；文本色如 `text_color_333333_ffffff_selector`。

**背景 selector 范式**（单文件、内联 shape、默认项在最后）：

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">

    <item android:state_checked="true">
        <shape>
            <corners android:radius="@dimen/dp_5" />
            <solid android:color="@color/color_0088ff" />
        </shape>
    </item>

    <item android:state_selected="true">
        <shape>
            <corners android:radius="@dimen/dp_5" />
            <solid android:color="@color/color_0088ff" />
        </shape>
    </item>

    <item>
        <shape>
            <corners android:radius="@dimen/dp_5" />
            <solid android:color="@color/color_ffffff" />
        </shape>
    </item>
</selector>
```

**文本色 ColorStateList 范式**（`res/color/` 下，默认项在最后）：

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">

    <item android:color="@color/white" android:state_checked="true" />
    <item android:color="@color/white" android:state_pressed="true" />
    <item android:color="@color/white" android:state_focused="true" />
    <item android:color="@color/white" android:state_selected="true" />
    <item android:color="@color/color_33" />

</selector>
```

## 何时「不建 drawable / color 文件」（生成前判断）

能不建文件就不建，优先复用工程现成能力：

- **圆角 + 纯色 + 描边背景** → 优先项目内的圆角描边 View（如 DevWidget `Round*` 的 `app:dev_*`）；复杂多态/渐变才写 drawable。
- **阴影 / 渐变 / pressed / ripple / 子 View 裁剪** → 优先阴影容器（如 `ShadowLayout` 的 `app:hl_*`）。
- **文本/控件状态色** → 优先 StateList BindingAdapter 或 `ColorStateList`。
- **控件默认视觉** → 优先 ViewTheme 类封装。
- **dp/sp 字面量** → 用 `@dimen/dp_*`、`@dimen/sp_*`。

确需新建时，再按上文命名族与单文件整合规则落地。

## 自检清单（生成前）

- [ ] 文件名仅小写字母/数字/下划线，且有正确前缀。
- [ ] layout 用组件前缀；id 用「控件类型_语义」。
- [ ] drawable 选对命名族：图标/模块 vs 值编码（颜色/圆角/描边）。
- [ ] 颜色 `color_<hex6>[_<alphaPercent>]`；尺寸 `dp_*`/`sp_*`。
- [ ] 多 state 资源 **单文件**、背景内联 shape、默认项放最后。
- [ ] 颜色 hex 取自真实色值（短别名也写满 6 位）。
- [ ] 新建前已确认无法用现成 View 能力 / ColorStateList 复用。
