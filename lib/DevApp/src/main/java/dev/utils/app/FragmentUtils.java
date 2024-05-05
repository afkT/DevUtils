package dev.utils.app;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import dev.utils.LogPrintUtils;

/**
 * detail: Fragment 工具类
 * @author Ttt
 * <pre>
 *     Navigation API
 *     @see <a href="https://developer.android.com/guide/navigation?hl=zh-cn"/>
 * </pre>
 */
public final class FragmentUtils {

    private FragmentUtils() {
    }

    // 日志 TAG
    private static final String TAG = FragmentUtils.class.getSimpleName();

    // 内部标记操作类型
    private static final int TYPE_ADD_FRAGMENT       = 0x01;
    private static final int TYPE_SHOW_FRAGMENT      = 0x01 << 1;
    private static final int TYPE_HIDE_FRAGMENT      = 0x01 << 2;
    private static final int TYPE_SHOW_HIDE_FRAGMENT = 0x01 << 3;
    private static final int TYPE_REPLACE_FRAGMENT   = 0x01 << 4;
    private static final int TYPE_REMOVE_FRAGMENT    = 0x01 << 5;
    private static final int TYPE_REMOVE_TO_FRAGMENT = 0x01 << 6;

    // 内部传参 Tag
    private static final String ARGS_ID           = "args_id";
    private static final String ARGS_IS_HIDE      = "args_is_hide";
    private static final String ARGS_IS_ADD_STACK = "args_is_add_stack";
    private static final String ARGS_TAG          = "args_tag";

    // =======
    // = add =
    // =======

    /**
     * 添加 Fragment
     * @param manager     FragmentManager
     * @param add         待添加 Fragment
     * @param containerId 容器 id
     */
    public static void add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId
    ) {
        add(manager, add, containerId, null, false, false);
    }

    /**
     * 添加 Fragment
     * @param manager     FragmentManager
     * @param add         待添加 Fragment
     * @param containerId 容器 id
     * @param isHide      {@code true} hide, {@code false} otherwise
     */
    public static void add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final boolean isHide
    ) {
        add(manager, add, containerId, null, isHide, false);
    }

    /**
     * 添加 Fragment
     * @param manager     FragmentManager
     * @param add         待添加 Fragment
     * @param containerId 容器 id
     * @param isHide      {@code true} hide, {@code false} otherwise
     * @param isAddStack  {@code true} add fragment in stack, {@code false} otherwise
     */
    public static void add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final boolean isHide,
            final boolean isAddStack
    ) {
        add(manager, add, containerId, null, isHide, isAddStack);
    }

    /**
     * 添加 Fragment
     * @param manager     FragmentManager
     * @param add         待添加 Fragment
     * @param containerId 容器 id
     * @param enterAnim   进入、添加动画
     * @param exitAnim    退出、移除动画
     */
    public static void add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim
    ) {
        add(
                manager, add, containerId, null, false,
                enterAnim, exitAnim, 0, 0
        );
    }

    /**
     * 添加 Fragment
     * @param manager     FragmentManager
     * @param add         待添加 Fragment
     * @param containerId 容器 id
     * @param isAddStack  {@code true} add fragment in stack, {@code false} otherwise
     * @param enterAnim   进入、添加动画
     * @param exitAnim    退出、移除动画
     */
    public static void add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final boolean isAddStack,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim
    ) {
        add(
                manager, add, containerId, null, isAddStack,
                enterAnim, exitAnim, 0, 0
        );
    }

    /**
     * 添加 Fragment
     * @param manager      FragmentManager
     * @param add          待添加 Fragment
     * @param containerId  容器 id
     * @param enterAnim    进入、添加动画
     * @param exitAnim     退出、移除动画
     * @param popEnterAnim 回退栈进入、添加动画
     * @param popExitAnim  回退栈退出、移除动画
     */
    public static void add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        add(
                manager, add, containerId, null, false,
                enterAnim, exitAnim, popEnterAnim, popExitAnim
        );
    }

    /**
     * 添加 Fragment
     * @param manager      FragmentManager
     * @param add          待添加 Fragment
     * @param containerId  容器 id
     * @param isAddStack   {@code true} add fragment in stack, {@code false} otherwise
     * @param enterAnim    进入、添加动画
     * @param exitAnim     退出、移除动画
     * @param popEnterAnim 回退栈进入、添加动画
     * @param popExitAnim  回退栈退出、移除动画
     */
    public static void add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final boolean isAddStack,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        add(
                manager, add, containerId, null, isAddStack,
                enterAnim, exitAnim, popEnterAnim, popExitAnim
        );
    }

    /**
     * 添加 Fragment
     * @param manager        FragmentManager
     * @param add            待添加 Fragment
     * @param containerId    容器 id
     * @param sharedElements A View in a disappearing Fragment to match with a View in an
     *                       appearing Fragment
     */
    public static void add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final View... sharedElements
    ) {
        add(manager, add, containerId, null, false, sharedElements);
    }

    /**
     * 添加 Fragment
     * @param manager        FragmentManager
     * @param add            待添加 Fragment
     * @param containerId    容器 id
     * @param isAddStack     {@code true} add fragment in stack, {@code false} otherwise
     * @param sharedElements A View in a disappearing Fragment to match with a View in an
     *                       appearing Fragment
     */
    public static void add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final boolean isAddStack,
            final View... sharedElements
    ) {
        add(manager, add, containerId, null, isAddStack, sharedElements);
    }

    /**
     * 添加 Fragment
     * @param manager     FragmentManager
     * @param adds        待添加 Fragment List
     * @param containerId 容器 id
     * @param showIndex   待显示的 Fragment 索引
     */
    public static void add(
            final FragmentManager manager,
            final List<Fragment> adds,
            @IdRes final int containerId,
            final int showIndex
    ) {
        add(manager, adds.toArray(new Fragment[0]), containerId, null, showIndex);
    }

    /**
     * 添加 Fragment
     * @param manager     FragmentManager
     * @param adds        待添加 Fragments
     * @param containerId 容器 id
     * @param showIndex   待显示的 Fragment 索引
     */
    public static void add(
            final FragmentManager manager,
            final Fragment[] adds,
            @IdRes final int containerId,
            final int showIndex
    ) {
        add(manager, adds, containerId, null, showIndex);
    }

    /**
     * 添加 Fragment
     * @param manager     FragmentManager
     * @param add         待添加 Fragment
     * @param containerId 容器 id
     * @param destTag     fragment tag
     */
    public static void add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final String destTag
    ) {
        add(manager, add, containerId, destTag, false, false);
    }

    /**
     * 添加 Fragment
     * @param manager     FragmentManager
     * @param add         待添加 Fragment
     * @param containerId 容器 id
     * @param destTag     fragment 标记 tag
     * @param isHide      {@code true} hide, {@code false} otherwise
     */
    public static void add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final String destTag,
            final boolean isHide
    ) {
        add(manager, add, containerId, destTag, isHide, false);
    }

    /**
     * 添加 Fragment
     * @param manager     FragmentManager
     * @param add         待添加 Fragment
     * @param containerId 容器 id
     * @param destTag     fragment 标记 tag
     * @param isHide      {@code true} hide, {@code false} otherwise
     * @param isAddStack  {@code true} add fragment in stack, {@code false} otherwise
     */
    public static void add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final String destTag,
            final boolean isHide,
            final boolean isAddStack
    ) {
        putArgs(add, new Args(containerId, destTag, isHide, isAddStack));
        operateNoAnim(TYPE_ADD_FRAGMENT, manager, null, add);
    }

    /**
     * 添加 Fragment
     * @param manager     FragmentManager
     * @param add         待添加 Fragment
     * @param containerId 容器 id
     * @param destTag     fragment 标记 tag
     * @param enterAnim   进入、添加动画
     * @param exitAnim    退出、移除动画
     */
    public static void add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final String destTag,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim
    ) {
        add(
                manager, add, containerId, destTag, false,
                enterAnim, exitAnim, 0, 0
        );
    }

    /**
     * 添加 Fragment
     * @param manager     FragmentManager
     * @param add         待添加 Fragment
     * @param containerId 容器 id
     * @param destTag     fragment 标记 tag
     * @param isAddStack  {@code true} add fragment in stack, {@code false} otherwise
     * @param enterAnim   进入、添加动画
     * @param exitAnim    退出、移除动画
     */
    public static void add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final String destTag,
            final boolean isAddStack,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim
    ) {
        add(
                manager, add, containerId, destTag, isAddStack,
                enterAnim, exitAnim, 0, 0
        );
    }

    /**
     * 添加 Fragment
     * @param manager      FragmentManager
     * @param add          待添加 Fragment
     * @param containerId  容器 id
     * @param destTag      fragment 标记 tag
     * @param enterAnim    进入、添加动画
     * @param exitAnim     退出、移除动画
     * @param popEnterAnim 回退栈进入、添加动画
     * @param popExitAnim  回退栈退出、移除动画
     */
    public static void add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final String destTag,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        add(
                manager, add, containerId, destTag, false,
                enterAnim, exitAnim, popEnterAnim, popExitAnim
        );
    }

    /**
     * 添加 Fragment
     * @param manager      FragmentManager
     * @param add          待添加 Fragment
     * @param containerId  容器 id
     * @param destTag      fragment 标记 tag
     * @param isAddStack   {@code true} add fragment in stack, {@code false} otherwise
     * @param enterAnim    进入、添加动画
     * @param exitAnim     退出、移除动画
     * @param popEnterAnim 回退栈进入、添加动画
     * @param popExitAnim  回退栈退出、移除动画
     */
    public static void add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final String destTag,
            final boolean isAddStack,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        FragmentTransaction ft = manager.beginTransaction();
        putArgs(add, new Args(containerId, destTag, false, isAddStack));
        addAnim(ft, enterAnim, exitAnim, popEnterAnim, popExitAnim);
        operate(TYPE_ADD_FRAGMENT, manager, ft, null, add);
    }

    /**
     * 添加 Fragment
     * @param manager        FragmentManager
     * @param add            待添加 Fragment
     * @param containerId    容器 id
     * @param destTag        fragment 标记 tag
     * @param sharedElements A View in a disappearing Fragment to match with a View in an
     *                       appearing Fragment
     */
    public static void add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final String destTag,
            final View... sharedElements
    ) {
        add(manager, add, containerId, destTag, false, sharedElements);
    }

    /**
     * 添加 Fragment
     * @param manager        FragmentManager
     * @param add            待添加 Fragment
     * @param containerId    容器 id
     * @param destTag        fragment 标记 tag
     * @param isAddStack     {@code true} add fragment in stack, {@code false} otherwise
     * @param sharedElements A View in a disappearing Fragment to match with a View in an
     *                       appearing Fragment
     */
    public static void add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final String destTag,
            final boolean isAddStack,
            final View... sharedElements
    ) {
        FragmentTransaction ft = manager.beginTransaction();
        putArgs(add, new Args(containerId, destTag, false, isAddStack));
        addSharedElement(ft, sharedElements);
        operate(TYPE_ADD_FRAGMENT, manager, ft, null, add);
    }

    /**
     * 添加 Fragment
     * @param manager     FragmentManager
     * @param adds        待添加 Fragment List
     * @param containerId 容器 id
     * @param destTags    fragment 标记 tag
     * @param showIndex   待显示的 Fragment 索引
     */
    public static void add(
            final FragmentManager manager,
            final List<Fragment> adds,
            @IdRes final int containerId,
            final String[] destTags,
            final int showIndex
    ) {
        add(manager, adds.toArray(new Fragment[0]), containerId, destTags, showIndex);
    }

    /**
     * 添加 Fragment
     * @param manager     FragmentManager
     * @param adds        待添加 Fragments
     * @param containerId 容器 id
     * @param destTags    fragment 标记 tag
     * @param showIndex   待显示的 Fragment 索引
     */
    public static void add(
            final FragmentManager manager,
            final Fragment[] adds,
            @IdRes final int containerId,
            final String[] destTags,
            final int showIndex
    ) {
        if (destTags == null) {
            for (int i = 0, len = adds.length; i < len; ++i) {
                putArgs(adds[i], new Args(containerId, null,
                        showIndex != i, false));
            }
        } else {
            for (int i = 0, len = adds.length; i < len; ++i) {
                putArgs(adds[i], new Args(containerId, destTags[i],
                        showIndex != i, false));
            }
        }
        operateNoAnim(TYPE_ADD_FRAGMENT, manager, null, adds);
    }

    // ========
    // = show =
    // ========

    /**
     * 显示 Fragment
     * @param show 待显示 Fragment
     */
    public static void show(final Fragment show) {
        putArgs(show, false);
        operateNoAnim(TYPE_SHOW_FRAGMENT, show.getFragmentManager(), null, show);
    }

    /**
     * 显示 Fragment
     * @param manager FragmentManager
     */
    public static void show(final FragmentManager manager) {
        List<Fragment> fragments = getFragments(manager);
        for (Fragment show : fragments) {
            putArgs(show, false);
        }
        operateNoAnim(TYPE_SHOW_FRAGMENT, manager, null, fragments.toArray(new Fragment[0]));
    }

    // ========
    // = hide =
    // ========

    /**
     * Hide fragment
     * @param hide 待隐藏 Fragment
     */
    public static void hide(final Fragment hide) {
        putArgs(hide, true);
        operateNoAnim(TYPE_HIDE_FRAGMENT, hide.getFragmentManager(), null, hide);
    }

    /**
     * Hide fragment
     * @param manager FragmentManager
     */
    public static void hide(final FragmentManager manager) {
        List<Fragment> fragments = getFragments(manager);
        for (Fragment hide : fragments) {
            putArgs(hide, true);
        }
        operateNoAnim(TYPE_HIDE_FRAGMENT, manager, null, fragments.toArray(new Fragment[0]));
    }

    // ============
    // = showHide =
    // ============

    /**
     * 显示 Fragment 并隐藏其他 Fragment
     * @param show 待显示 Fragment
     * @param hide 待隐藏 Fragment
     */
    public static void showHide(
            final Fragment show,
            final Fragment hide
    ) {
        showHide(show, Collections.singletonList(hide));
    }

    /**
     * 显示 Fragment 并隐藏其他 Fragment
     * @param showIndex 待显示的 Fragment 索引
     * @param fragments 待隐藏 Fragment
     */
    public static void showHide(
            final int showIndex,
            final Fragment... fragments
    ) {
        showHide(fragments[showIndex], fragments);
    }

    /**
     * 显示 Fragment 并隐藏其他 Fragment
     * @param show 待显示 Fragment
     * @param hide 待隐藏 Fragment
     */
    public static void showHide(
            final Fragment show,
            final Fragment... hide
    ) {
        showHide(show, Arrays.asList(hide));
    }

    /**
     * 显示 Fragment 并隐藏其他 Fragment
     * @param showIndex 待显示的 Fragment 索引
     * @param fragments 待隐藏 Fragment List
     */
    public static void showHide(
            final int showIndex,
            final List<Fragment> fragments
    ) {
        showHide(fragments.get(showIndex), fragments);
    }

    /**
     * 显示 Fragment 并隐藏其他 Fragment
     * @param show 待显示 Fragment
     * @param hide 待隐藏 Fragment List
     */
    public static void showHide(
            final Fragment show,
            final List<Fragment> hide
    ) {
        for (Fragment fragment : hide) {
            putArgs(fragment, fragment != show);
        }
        operateNoAnim(
                TYPE_SHOW_HIDE_FRAGMENT, show.getFragmentManager(),
                show, hide.toArray(new Fragment[0])
        );
    }

    /**
     * 显示 Fragment 并隐藏其他 Fragment
     * @param show         待显示 Fragment
     * @param hide         待隐藏 Fragment
     * @param enterAnim    进入、添加动画
     * @param exitAnim     退出、移除动画
     * @param popEnterAnim 回退栈进入、添加动画
     * @param popExitAnim  回退栈退出、移除动画
     */
    public static void showHide(
            final Fragment show,
            final Fragment hide,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        showHide(
                show, Collections.singletonList(hide),
                enterAnim, exitAnim, popEnterAnim, popExitAnim
        );
    }

    /**
     * 显示 Fragment 并隐藏其他 Fragment
     * @param showIndex    待显示的 Fragment 索引
     * @param fragments    待隐藏 Fragment List
     * @param enterAnim    进入、添加动画
     * @param exitAnim     退出、移除动画
     * @param popEnterAnim 回退栈进入、添加动画
     * @param popExitAnim  回退栈退出、移除动画
     */
    public static void showHide(
            final int showIndex,
            final List<Fragment> fragments,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        showHide(
                fragments.get(showIndex), fragments,
                enterAnim, exitAnim, popEnterAnim, popExitAnim
        );
    }

    /**
     * 显示 Fragment 并隐藏其他 Fragment
     * @param show         待显示 Fragment
     * @param hide         待隐藏 Fragment List
     * @param enterAnim    进入、添加动画
     * @param exitAnim     退出、移除动画
     * @param popEnterAnim 回退栈进入、添加动画
     * @param popExitAnim  回退栈退出、移除动画
     */
    public static void showHide(
            final Fragment show,
            final List<Fragment> hide,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        for (Fragment fragment : hide) {
            putArgs(fragment, fragment != show);
        }
        FragmentManager manager = show.getFragmentManager();
        if (manager != null) {
            FragmentTransaction ft = manager.beginTransaction();
            addAnim(ft, enterAnim, exitAnim, popEnterAnim, popExitAnim);
            operate(TYPE_SHOW_HIDE_FRAGMENT, manager, ft, show, hide.toArray(new Fragment[0]));
        }
    }

    // ===========
    // = replace =
    // ===========

    /**
     * 替换 Fragment
     * @param srcFragment  The source of fragment
     * @param destFragment The destination of fragment
     */
    public static void replace(
            final Fragment srcFragment,
            final Fragment destFragment
    ) {
        replace(srcFragment, destFragment, null, false);
    }

    /**
     * 替换 Fragment
     * @param srcFragment  The source of fragment
     * @param destFragment The destination of fragment
     * @param isAddStack   {@code true} add fragment in stack, {@code false} otherwise
     */
    public static void replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            final boolean isAddStack
    ) {
        replace(srcFragment, destFragment, null, isAddStack);
    }

    /**
     * 替换 Fragment
     * @param srcFragment  The source of fragment
     * @param destFragment The destination of fragment
     * @param enterAnim    进入、添加动画
     * @param exitAnim     退出、移除动画
     */
    public static void replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim
    ) {
        replace(
                srcFragment, destFragment, null, false,
                enterAnim, exitAnim, 0, 0
        );
    }

    /**
     * 替换 Fragment
     * @param srcFragment  The source of fragment
     * @param destFragment The destination of fragment
     * @param isAddStack   {@code true} add fragment in stack, {@code false} otherwise
     * @param enterAnim    进入、添加动画
     * @param exitAnim     退出、移除动画
     */
    public static void replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            final boolean isAddStack,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim
    ) {
        replace(
                srcFragment, destFragment, null, isAddStack,
                enterAnim, exitAnim, 0, 0
        );
    }

    /**
     * 替换 Fragment
     * @param srcFragment  The source of fragment
     * @param destFragment The destination of fragment
     * @param enterAnim    进入、添加动画
     * @param exitAnim     退出、移除动画
     * @param popEnterAnim 回退栈进入、添加动画
     * @param popExitAnim  回退栈退出、移除动画
     */
    public static void replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        replace(
                srcFragment, destFragment, null, false,
                enterAnim, exitAnim, popEnterAnim, popExitAnim
        );
    }

    /**
     * 替换 Fragment
     * @param srcFragment  The source of fragment
     * @param destFragment The destination of fragment
     * @param isAddStack   {@code true} add fragment in stack, {@code false} otherwise
     * @param enterAnim    进入、添加动画
     * @param exitAnim     退出、移除动画
     * @param popEnterAnim 回退栈进入、添加动画
     * @param popExitAnim  回退栈退出、移除动画
     */
    public static void replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            final boolean isAddStack,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        replace(
                srcFragment, destFragment, null, isAddStack,
                enterAnim, exitAnim, popEnterAnim, popExitAnim
        );
    }

    /**
     * 替换 Fragment
     * @param srcFragment    The source of fragment
     * @param destFragment   The destination of fragment
     * @param sharedElements A View in a disappearing Fragment to match with a View in an
     *                       appearing Fragment
     */
    public static void replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            final View... sharedElements
    ) {
        replace(srcFragment, destFragment, null, false, sharedElements);
    }

    /**
     * 替换 Fragment
     * @param srcFragment    The source of fragment
     * @param destFragment   The destination of fragment
     * @param isAddStack     {@code true} add fragment in stack, {@code false} otherwise
     * @param sharedElements A View in a disappearing Fragment to match with a View in an
     *                       appearing Fragment
     */
    public static void replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            final boolean isAddStack,
            final View... sharedElements
    ) {
        replace(srcFragment, destFragment, null, isAddStack, sharedElements);
    }

    /**
     * 替换 Fragment
     * @param manager     FragmentManager
     * @param fragment    待替换 Fragment
     * @param containerId 容器 id
     */
    public static void replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId
    ) {
        replace(manager, fragment, containerId, null, false);
    }

    /**
     * 替换 Fragment
     * @param manager     FragmentManager
     * @param fragment    待替换 Fragment
     * @param containerId 容器 id
     * @param isAddStack  {@code true} add fragment in stack, {@code false} otherwise
     */
    public static void replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            final boolean isAddStack
    ) {
        replace(manager, fragment, containerId, null, isAddStack);
    }

    /**
     * 替换 Fragment
     * @param manager     FragmentManager
     * @param fragment    待替换 Fragment
     * @param containerId 容器 id
     * @param enterAnim   进入、添加动画
     * @param exitAnim    退出、移除动画
     */
    public static void replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim
    ) {
        replace(
                manager, fragment, containerId, null, false,
                enterAnim, exitAnim, 0, 0
        );
    }

    /**
     * 替换 Fragment
     * @param manager     FragmentManager
     * @param fragment    待替换 Fragment
     * @param containerId 容器 id
     * @param isAddStack  {@code true} add fragment in stack, {@code false} otherwise
     * @param enterAnim   进入、添加动画
     * @param exitAnim    退出、移除动画
     */
    public static void replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            final boolean isAddStack,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim
    ) {
        replace(
                manager, fragment, containerId, null, isAddStack,
                enterAnim, exitAnim, 0, 0
        );
    }

    /**
     * 替换 Fragment
     * @param manager      FragmentManager
     * @param fragment     待替换 Fragment
     * @param containerId  容器 id
     * @param enterAnim    进入、添加动画
     * @param exitAnim     退出、移除动画
     * @param popEnterAnim 回退栈进入、添加动画
     * @param popExitAnim  回退栈退出、移除动画
     */
    public static void replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        replace(
                manager, fragment, containerId, null, false,
                enterAnim, exitAnim, popEnterAnim, popExitAnim
        );
    }

    /**
     * 替换 Fragment
     * @param manager      FragmentManager
     * @param fragment     待替换 Fragment
     * @param containerId  容器 id
     * @param isAddStack   {@code true} add fragment in stack, {@code false} otherwise
     * @param enterAnim    进入、添加动画
     * @param exitAnim     退出、移除动画
     * @param popEnterAnim 回退栈进入、添加动画
     * @param popExitAnim  回退栈退出、移除动画
     */
    public static void replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            final boolean isAddStack,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        replace(
                manager, fragment, containerId, null, isAddStack,
                enterAnim, exitAnim, popEnterAnim, popExitAnim
        );
    }

    /**
     * 替换 Fragment
     * @param manager        FragmentManager
     * @param fragment       待替换 Fragment
     * @param containerId    容器 id
     * @param sharedElements A View in a disappearing Fragment to match with a View in an
     *                       appearing Fragment
     */
    public static void replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            final View... sharedElements
    ) {
        replace(manager, fragment, containerId, null, false, sharedElements);
    }

    /**
     * 替换 Fragment
     * @param manager        FragmentManager
     * @param fragment       待替换 Fragment
     * @param containerId    容器 id
     * @param isAddStack     {@code true} add fragment in stack, {@code false} otherwise
     * @param sharedElements A View in a disappearing Fragment to match with a View in an
     *                       appearing Fragment
     */
    public static void replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            final boolean isAddStack,
            final View... sharedElements
    ) {
        replace(manager, fragment, containerId, null, isAddStack, sharedElements);
    }

    /**
     * 替换 Fragment
     * @param srcFragment  The source of fragment
     * @param destFragment The destination of fragment
     * @param destTag      fragment 标记 tag
     */
    public static void replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            final String destTag
    ) {
        replace(srcFragment, destFragment, destTag, false);
    }

    /**
     * 替换 Fragment
     * @param srcFragment  The source of fragment
     * @param destFragment The destination of fragment
     * @param destTag      fragment 标记 tag
     * @param isAddStack   {@code true} add fragment in stack, {@code false} otherwise
     */
    public static void replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            final String destTag,
            final boolean isAddStack
    ) {
        FragmentManager manager = srcFragment.getFragmentManager();
        if (manager == null) return;
        Args args = getArgs(srcFragment);
        replace(manager, destFragment, args.id, destTag, isAddStack);
    }

    /**
     * 替换 Fragment
     * @param srcFragment  The source of fragment
     * @param destFragment The destination of fragment
     * @param destTag      fragment 标记 tag
     * @param enterAnim    进入、添加动画
     * @param exitAnim     退出、移除动画
     */
    public static void replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            final String destTag,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim
    ) {
        replace(
                srcFragment, destFragment, destTag, false,
                enterAnim, exitAnim, 0, 0
        );
    }

    /**
     * 替换 Fragment
     * @param srcFragment  The source of fragment
     * @param destFragment The destination of fragment
     * @param destTag      fragment 标记 tag
     * @param isAddStack   {@code true} add fragment in stack, {@code false} otherwise
     * @param enterAnim    进入、添加动画
     * @param exitAnim     退出、移除动画
     */
    public static void replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            final String destTag,
            final boolean isAddStack,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim
    ) {
        replace(
                srcFragment, destFragment, destTag, isAddStack,
                enterAnim, exitAnim, 0, 0
        );
    }

    /**
     * 替换 Fragment
     * @param srcFragment  The source of fragment
     * @param destFragment The destination of fragment
     * @param destTag      fragment 标记 tag
     * @param enterAnim    进入、添加动画
     * @param exitAnim     退出、移除动画
     * @param popEnterAnim 回退栈进入、添加动画
     * @param popExitAnim  回退栈退出、移除动画
     */
    public static void replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            final String destTag,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        replace(
                srcFragment, destFragment, destTag, false,
                enterAnim, exitAnim, popEnterAnim, popExitAnim
        );
    }

    /**
     * 替换 Fragment
     * @param srcFragment  The source of fragment
     * @param destFragment The destination of fragment
     * @param destTag      fragment 标记 tag
     * @param isAddStack   {@code true} add fragment in stack, {@code false} otherwise
     * @param enterAnim    进入、添加动画
     * @param exitAnim     退出、移除动画
     * @param popEnterAnim 回退栈进入、添加动画
     * @param popExitAnim  回退栈退出、移除动画
     */
    public static void replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            final String destTag,
            final boolean isAddStack,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        FragmentManager manager = srcFragment.getFragmentManager();
        if (manager == null) return;
        Args args = getArgs(srcFragment);
        replace(
                manager, destFragment, args.id, destTag, isAddStack,
                enterAnim, exitAnim, popEnterAnim, popExitAnim
        );
    }

    /**
     * 替换 Fragment
     * @param srcFragment    The source of fragment
     * @param destFragment   The destination of fragment
     * @param destTag        fragment 标记 tag
     * @param sharedElements A View in a disappearing Fragment to match with a View in an
     *                       appearing Fragment
     */
    public static void replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            final String destTag,
            final View... sharedElements
    ) {
        replace(srcFragment, destFragment, destTag, false, sharedElements);
    }

    /**
     * 替换 Fragment
     * @param srcFragment    The source of fragment
     * @param destFragment   The destination of fragment
     * @param destTag        fragment 标记 tag
     * @param isAddStack     {@code true} add fragment in stack, {@code false} otherwise
     * @param sharedElements A View in a disappearing Fragment to match with a View in an
     *                       appearing Fragment
     */
    public static void replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            final String destTag,
            final boolean isAddStack,
            final View... sharedElements
    ) {
        FragmentManager manager = srcFragment.getFragmentManager();
        if (manager == null) return;
        Args args = getArgs(srcFragment);
        replace(manager, destFragment, args.id, destTag, isAddStack, sharedElements);
    }

    /**
     * 替换 Fragment
     * @param manager     FragmentManager
     * @param fragment    待替换 Fragment
     * @param containerId 容器 id
     * @param destTag     fragment 标记 tag
     */
    public static void replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            final String destTag
    ) {
        replace(manager, fragment, containerId, destTag, false);
    }

    /**
     * 替换 Fragment
     * @param manager     FragmentManager
     * @param fragment    待替换 Fragment
     * @param containerId 容器 id
     * @param destTag     fragment 标记 tag
     * @param isAddStack  {@code true} add fragment in stack, {@code false} otherwise
     */
    public static void replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            final String destTag,
            final boolean isAddStack
    ) {
        FragmentTransaction ft = manager.beginTransaction();
        putArgs(fragment, new Args(containerId, destTag, false, isAddStack));
        operate(TYPE_REPLACE_FRAGMENT, manager, ft, null, fragment);
    }

    /**
     * 替换 Fragment
     * @param manager     FragmentManager
     * @param fragment    待替换 Fragment
     * @param containerId 容器 id
     * @param destTag     fragment 标记 tag
     * @param enterAnim   进入、添加动画
     * @param exitAnim    退出、移除动画
     */
    public static void replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            final String destTag,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim
    ) {
        replace(
                manager, fragment, containerId, destTag, false,
                enterAnim, exitAnim, 0, 0
        );
    }

    /**
     * 替换 Fragment
     * @param manager     FragmentManager
     * @param fragment    待替换 Fragment
     * @param containerId 容器 id
     * @param destTag     fragment 标记 tag
     * @param isAddStack  {@code true} add fragment in stack, {@code false} otherwise
     * @param enterAnim   进入、添加动画
     * @param exitAnim    退出、移除动画
     */
    public static void replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            final String destTag,
            final boolean isAddStack,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim
    ) {
        replace(
                manager, fragment, containerId, destTag, isAddStack,
                enterAnim, exitAnim, 0, 0
        );
    }

    /**
     * 替换 Fragment
     * @param manager      FragmentManager
     * @param fragment     待替换 Fragment
     * @param containerId  容器 id
     * @param destTag      fragment 标记 tag
     * @param enterAnim    进入、添加动画
     * @param exitAnim     退出、移除动画
     * @param popEnterAnim 回退栈进入、添加动画
     * @param popExitAnim  回退栈退出、移除动画
     */
    public static void replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            final String destTag,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        replace(
                manager, fragment, containerId, destTag, false,
                enterAnim, exitAnim, popEnterAnim, popExitAnim
        );
    }

    /**
     * 替换 Fragment
     * @param manager      FragmentManager
     * @param fragment     待替换 Fragment
     * @param containerId  容器 id
     * @param destTag      fragment 标记 tag
     * @param isAddStack   {@code true} add fragment in stack, {@code false} otherwise
     * @param enterAnim    进入、添加动画
     * @param exitAnim     退出、移除动画
     * @param popEnterAnim 回退栈进入、添加动画
     * @param popExitAnim  回退栈退出、移除动画
     */
    public static void replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            final String destTag,
            final boolean isAddStack,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        FragmentTransaction ft = manager.beginTransaction();
        putArgs(fragment, new Args(containerId, destTag, false, isAddStack));
        addAnim(ft, enterAnim, exitAnim, popEnterAnim, popExitAnim);
        operate(TYPE_REPLACE_FRAGMENT, manager, ft, null, fragment);
    }

    /**
     * 替换 Fragment
     * @param manager        FragmentManager
     * @param fragment       待替换 Fragment
     * @param containerId    容器 id
     * @param destTag        fragment 标记 tag
     * @param sharedElements A View in a disappearing Fragment to match with a View in an
     *                       appearing Fragment
     */
    public static void replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            final String destTag,
            final View... sharedElements
    ) {
        replace(manager, fragment, containerId, destTag, false, sharedElements);
    }

    /**
     * 替换 Fragment
     * @param manager        FragmentManager
     * @param fragment       待替换 Fragment
     * @param containerId    容器 id
     * @param destTag        fragment 标记 tag
     * @param isAddStack     {@code true} add fragment in stack, {@code false} otherwise
     * @param sharedElements A View in a disappearing Fragment to match with a View in an
     *                       appearing Fragment
     */
    public static void replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            final String destTag,
            final boolean isAddStack,
            final View... sharedElements
    ) {
        FragmentTransaction ft = manager.beginTransaction();
        putArgs(fragment, new Args(containerId, destTag, false, isAddStack));
        addSharedElement(ft, sharedElements);
        operate(TYPE_REPLACE_FRAGMENT, manager, ft, null, fragment);
    }

    // =======
    // = pop =
    // =======

    /**
     * 回退 Fragment
     * @param manager FragmentManager
     */
    public static void pop(final FragmentManager manager) {
        pop(manager, true);
    }

    /**
     * 回退 Fragment
     * @param manager     FragmentManager
     * @param isImmediate 是否立即操作
     */
    public static void pop(
            final FragmentManager manager,
            final boolean isImmediate
    ) {
        if (isImmediate) {
            manager.popBackStackImmediate();
        } else {
            manager.popBackStack();
        }
    }

    /**
     * 回退到指定 Fragment
     * @param manager       FragmentManager
     * @param popClazz      待回退 Fragment
     * @param isIncludeSelf 是否包含自身
     */
    public static void popTo(
            final FragmentManager manager,
            final Class<? extends Fragment> popClazz,
            final boolean isIncludeSelf
    ) {
        popTo(manager, popClazz, isIncludeSelf, true);
    }

    /**
     * 回退到指定 Fragment
     * @param manager       FragmentManager
     * @param popClazz      待回退 Fragment
     * @param isIncludeSelf 是否包含自身
     * @param isImmediate   是否立即操作
     */
    public static void popTo(
            final FragmentManager manager,
            final Class<? extends Fragment> popClazz,
            final boolean isIncludeSelf,
            final boolean isImmediate
    ) {
        if (isImmediate) {
            manager.popBackStackImmediate(
                    popClazz.getName(),
                    isIncludeSelf ? FragmentManager.POP_BACK_STACK_INCLUSIVE : 0
            );
        } else {
            manager.popBackStack(
                    popClazz.getName(),
                    isIncludeSelf ? FragmentManager.POP_BACK_STACK_INCLUSIVE : 0
            );
        }
    }

    /**
     * 回退所有 Fragment
     * @param manager FragmentManager
     */
    public static void popAll(final FragmentManager manager) {
        popAll(manager, true);
    }

    /**
     * 回退所有 Fragment
     * @param manager     FragmentManager
     * @param isImmediate 是否立即操作
     */
    public static void popAll(
            final FragmentManager manager,
            final boolean isImmediate
    ) {
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry entry = manager.getBackStackEntryAt(0);
            if (isImmediate) {
                manager.popBackStackImmediate(entry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } else {
                manager.popBackStack(entry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
    }

    // ==========
    // = remove =
    // ==========

    /**
     * 移除 Fragment
     * @param remove 待移除 Fragment
     */
    public static void remove(final Fragment remove) {
        operateNoAnim(TYPE_REMOVE_FRAGMENT, remove.getFragmentManager(), null, remove);
    }

    /**
     * 移除 Fragment
     * @param removeTo      待移除 Fragment
     * @param isIncludeSelf 是否包含自身
     */
    public static void removeTo(
            final Fragment removeTo,
            final boolean isIncludeSelf
    ) {
        operateNoAnim(
                TYPE_REMOVE_TO_FRAGMENT, removeTo.getFragmentManager(),
                isIncludeSelf ? removeTo : null, removeTo
        );
    }

    /**
     * 移除全部 Fragment
     * @param manager FragmentManager
     */
    public static void removeAll(final FragmentManager manager) {
        List<Fragment> fragments = getFragments(manager);
        operateNoAnim(TYPE_REMOVE_FRAGMENT, manager, null, fragments.toArray(new Fragment[0]));
    }

    // ========
    // = find =
    // ========

    /**
     * 查找 Fragment
     * @param manager FragmentManager
     * @param clazz   待查找的 Fragment class
     * @return Fragment
     */
    public static Fragment findFragment(
            final FragmentManager manager,
            final Class<? extends Fragment> clazz
    ) {
        return manager.findFragmentByTag(clazz.getName());
    }

    /**
     * 查找 Fragment
     * @param manager FragmentManager
     * @param destTag fragment 标记 tag
     * @return Fragment
     */
    public static Fragment findFragment(
            final FragmentManager manager,
            final String destTag
    ) {
        return manager.findFragmentByTag(destTag);
    }

    // =======
    // = get =
    // =======

    /**
     * 获取顶部 Fragment
     * @param manager FragmentManager
     * @return 顶部 Fragment
     */
    public static Fragment getTop(final FragmentManager manager) {
        return getTopIsInStack(manager, null, false);
    }

    /**
     * 获取栈顶 Fragment
     * @param manager FragmentManager
     * @return 获取栈顶 Fragment
     */
    public static Fragment getTopInStack(final FragmentManager manager) {
        return getTopIsInStack(manager, null, true);
    }

    /**
     * 获取顶部 Fragment
     * @param manager        FragmentManager
     * @param parentFragment 父 Fragment
     * @param isInStack      是否栈内
     * @return 顶部 Fragment
     */
    private static Fragment getTopIsInStack(
            final FragmentManager manager,
            final Fragment parentFragment,
            final boolean isInStack
    ) {
        List<Fragment> fragments = getFragments(manager);
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                if (isInStack) {
                    Bundle args = fragment.getArguments();
                    if (args != null && args.getBoolean(ARGS_IS_ADD_STACK)) {
                        return getTopIsInStack(
                                fragment.getChildFragmentManager(),
                                fragment, true
                        );
                    }
                } else {
                    return getTopIsInStack(
                            fragment.getChildFragmentManager(),
                            fragment, false
                    );
                }
            }
        }
        return parentFragment;
    }

    /**
     * 获取顶部显示的 Fragment
     * @param manager FragmentManager
     * @return 顶部显示的 Fragment
     */
    public static Fragment getTopShow(final FragmentManager manager) {
        return getTopShowIsInStack(manager, null, false);
    }

    /**
     * 获取栈顶显示的 Fragment
     * @param manager FragmentManager
     * @return 栈顶显示的 Fragment
     */
    public static Fragment getTopShowInStack(final FragmentManager manager) {
        return getTopShowIsInStack(manager, null, true);
    }

    /**
     * 获取顶部显示的 Fragment
     * @param manager        FragmentManager
     * @param parentFragment 父 Fragment
     * @param isInStack      是否栈内
     * @return 顶部显示的 Fragment
     */
    private static Fragment getTopShowIsInStack(
            final FragmentManager manager,
            final Fragment parentFragment,
            final boolean isInStack
    ) {
        List<Fragment> fragments = getFragments(manager);
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null && fragment.isResumed()
                    && fragment.isVisible()
                    && fragment.getUserVisibleHint()) {
                if (isInStack) {
                    Bundle args = fragment.getArguments();
                    if (args != null && args.getBoolean(ARGS_IS_ADD_STACK)) {
                        return getTopShowIsInStack(
                                fragment.getChildFragmentManager(),
                                fragment, true
                        );
                    }
                } else {
                    return getTopShowIsInStack(
                            fragment.getChildFragmentManager(),
                            fragment, false
                    );
                }
            }
        }
        return parentFragment;
    }

    // =

    /**
     * 获取 FragmentManager 全部 Fragment
     * @param manager FragmentManager
     * @return FragmentManager 全部 Fragment
     */
    public static List<Fragment> getFragments(final FragmentManager manager) {
        List<Fragment> fragments = manager.getFragments();
        if (fragments.isEmpty()) return Collections.emptyList();
        return fragments;
    }

    /**
     * 获取 FragmentManager 全部栈顶 Fragment
     * @param manager FragmentManager
     * @return FragmentManager 全部栈顶 Fragment
     */
    public static List<Fragment> getFragmentsInStack(final FragmentManager manager) {
        List<Fragment> fragments = getFragments(manager);
        List<Fragment> result    = new ArrayList<>();
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                Bundle args = fragment.getArguments();
                if (args != null && args.getBoolean(ARGS_IS_ADD_STACK)) {
                    result.add(fragment);
                }
            }
        }
        return result;
    }

    /**
     * 获取 FragmentManager 全部 Fragment
     * @param manager FragmentManager
     * @return FragmentManager 全部 Fragment
     */
    public static List<FragmentNode> getAllFragments(final FragmentManager manager) {
        return getAllFragments(manager, new ArrayList<>());
    }

    /**
     * 获取 FragmentManager 全部 Fragment
     * @param manager FragmentManager
     * @param result  存储集合
     * @return FragmentManager 全部 Fragment
     */
    private static List<FragmentNode> getAllFragments(
            final FragmentManager manager,
            final List<FragmentNode> result
    ) {
        List<Fragment> fragments = getFragments(manager);
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                result.add(new FragmentNode(fragment,
                        getAllFragments(
                                fragment.getChildFragmentManager(),
                                new ArrayList<>()))
                );
            }
        }
        return result;
    }

    /**
     * 获取 FragmentManager 全部栈顶 Fragment
     * @param manager FragmentManager
     * @return FragmentManager 全部栈顶 Fragment
     */
    public static List<FragmentNode> getAllFragmentsInStack(final FragmentManager manager) {
        return getAllFragmentsInStack(manager, new ArrayList<>());
    }

    /**
     * 获取 FragmentManager 全部栈顶 Fragment
     * @param manager FragmentManager
     * @param result  存储集合
     * @return FragmentManager 全部栈顶 Fragment
     */
    private static List<FragmentNode> getAllFragmentsInStack(
            final FragmentManager manager,
            final List<FragmentNode> result
    ) {
        List<Fragment> fragments = getFragments(manager);
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                Bundle args = fragment.getArguments();
                if (args != null && args.getBoolean(ARGS_IS_ADD_STACK)) {
                    result.add(new FragmentNode(fragment,
                            getAllFragmentsInStack(
                                    fragment.getChildFragmentManager(),
                                    new ArrayList<>()))
                    );
                }
            }
        }
        return result;
    }

    // =

    /**
     * 获取 Fragment SimpleName
     * @param fragment Fragment
     * @return Fragment SimpleName
     */
    public static String getSimpleName(final Fragment fragment) {
        return fragment == null ? "null" : fragment.getClass().getSimpleName();
    }

    /**
     * 调用 Fragment OnBackClickListener 校验是否进行消费
     * @param fragment Fragment
     * @return {@code true} 消费, {@code false} 不消费
     */
    public static boolean dispatchBackPress(final Fragment fragment) {
        return fragment.isResumed() && fragment.isVisible()
                && fragment.getUserVisibleHint()
                && fragment instanceof OnBackClickListener
                && ((OnBackClickListener) fragment).onBackClick();
    }

    /**
     * 调用 Fragment OnBackClickListener 校验是否进行消费
     * @param manager FragmentManager
     * @return {@code true} 消费, {@code false} 不消费
     */
    public static boolean dispatchBackPress(final FragmentManager manager) {
        List<Fragment> fragments = getFragments(manager);
        if (fragments.isEmpty()) return false;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null && fragment.isResumed()
                    && fragment.isVisible()
                    && fragment.getUserVisibleHint()
                    && fragment instanceof OnBackClickListener
                    && ((OnBackClickListener) fragment).onBackClick()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置 Fragment View 背景
     * @param fragment Fragment
     * @param color    背景颜色
     */
    public static void setBackgroundColor(
            final Fragment fragment,
            @ColorInt final int color
    ) {
        View view = fragment.getView();
        if (view != null) {
            view.setBackgroundColor(color);
        }
    }

    /**
     * 设置 Fragment View 背景
     * @param fragment Fragment
     * @param resId    资源 id
     */
    public static void setBackgroundResource(
            final Fragment fragment,
            @DrawableRes final int resId
    ) {
        View view = fragment.getView();
        if (view != null) {
            view.setBackgroundResource(resId);
        }
    }

    /**
     * 设置 Fragment View 背景
     * @param fragment   Fragment
     * @param background 背景
     */
    public static void setBackground(
            final Fragment fragment,
            final Drawable background
    ) {
        View view = fragment.getView();
        if (view == null) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
        }
    }

    // ===============
    // = 对外公开包装类 =
    // ===============

    /**
     * detail: Fragment 节点封装类
     * @author Ttt
     */
    public static class FragmentNode {

        private final Fragment           fragment;
        private final List<FragmentNode> next;

        public FragmentNode(
                final Fragment fragment,
                final List<FragmentNode> next
        ) {
            this.fragment = fragment;
            this.next     = next;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public List<FragmentNode> getNext() {
            return next;
        }

        @NonNull
        @Override
        public String toString() {
            String childStr = ((next == null || next.isEmpty()) ? "no child" : next.toString());
            return fragment.getClass().getSimpleName() + "->" + childStr;
        }
    }

    /**
     * detail: 回退事件
     * @author Ttt
     */
    public interface OnBackClickListener {

        /**
         * 是否允许处理返回键
         * @return {@code true} yes, {@code false} no
         */
        boolean onBackClick();
    }

    // =========
    // = 内部类 =
    // =========

    /**
     * detail: 传参包装类
     * @author Ttt
     */
    private static class Args {

        final int     id;
        final String  tag;
        final boolean isHide;
        final boolean isAddStack;

        Args(
                final int id,
                final boolean isHide,
                final boolean isAddStack
        ) {
            this(id, null, isHide, isAddStack);
        }

        Args(
                final int id,
                final String tag,
                final boolean isHide,
                final boolean isAddStack
        ) {
            this.id         = id;
            this.tag        = tag;
            this.isHide     = isHide;
            this.isAddStack = isAddStack;
        }
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 添加 Bundle 传参值
     * @param fragment 待传参 Fragment
     * @param args     传参包装类
     */
    private static void putArgs(
            final Fragment fragment,
            final Args args
    ) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
            fragment.setArguments(bundle);
        }
        bundle.putInt(ARGS_ID, args.id);
        bundle.putBoolean(ARGS_IS_HIDE, args.isHide);
        bundle.putBoolean(ARGS_IS_ADD_STACK, args.isAddStack);
        bundle.putString(ARGS_TAG, args.tag);
    }

    /**
     * 添加 Bundle 传参值
     * @param fragment 待传参 Fragment
     * @param isHide   是否隐藏
     */
    private static void putArgs(
            final Fragment fragment,
            final boolean isHide
    ) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
            fragment.setArguments(bundle);
        }
        bundle.putBoolean(ARGS_IS_HIDE, isHide);
    }

    /**
     * 解析 Bundle 传参值
     * @param fragment 待解析 Fragment
     * @return 传参包装类
     */
    private static Args getArgs(final Fragment fragment) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null) bundle = Bundle.EMPTY;
        return new Args(
                bundle.getInt(ARGS_ID, fragment.getId()),
                bundle.getBoolean(ARGS_IS_HIDE),
                bundle.getBoolean(ARGS_IS_ADD_STACK)
        );
    }

    /**
     * 执行具体操作 ( 无动画 )
     * @param type          操作类型
     * @param manager       FragmentManager
     * @param srcFragment   The source of fragment
     * @param destFragments The destination of fragment
     */
    private static void operateNoAnim(
            final int type,
            final FragmentManager manager,
            final Fragment srcFragment,
            final Fragment... destFragments
    ) {
        if (manager == null) return;
        FragmentTransaction ft = manager.beginTransaction();
        operate(type, manager, ft, srcFragment, destFragments);
    }

    /**
     * 执行具体操作
     * @param type          操作类型
     * @param manager       FragmentManager
     * @param ft            FragmentTransaction
     * @param srcFragment   The source of fragment
     * @param destFragments The destination of fragment
     */
    private static void operate(
            final int type,
            final FragmentManager manager,
            final FragmentTransaction ft,
            final Fragment srcFragment,
            final Fragment... destFragments
    ) {
        if (srcFragment != null && srcFragment.isRemoving()) {
            LogPrintUtils.eTag(TAG, srcFragment.getClass().getName() + " is isRemoving");
            return;
        }
        String name;
        Bundle args;
        switch (type) {
            case TYPE_ADD_FRAGMENT:
                for (Fragment fragment : destFragments) {
                    args = fragment.getArguments();
                    if (args == null) return;
                    name = args.getString(ARGS_TAG, fragment.getClass().getName());
                    Fragment fragmentByTag = manager.findFragmentByTag(name);
                    if (fragmentByTag != null && fragmentByTag.isAdded()) {
                        ft.remove(fragmentByTag);
                    }
                    ft.add(args.getInt(ARGS_ID), fragment, name);
                    if (args.getBoolean(ARGS_IS_HIDE)) ft.hide(fragment);
                    if (args.getBoolean(ARGS_IS_ADD_STACK)) ft.addToBackStack(name);
                }
                break;
            case TYPE_HIDE_FRAGMENT:
                for (Fragment fragment : destFragments) {
                    ft.hide(fragment);
                }
                break;
            case TYPE_SHOW_FRAGMENT:
                for (Fragment fragment : destFragments) {
                    ft.show(fragment);
                }
                break;
            case TYPE_SHOW_HIDE_FRAGMENT:
                ft.show(srcFragment);
                for (Fragment fragment : destFragments) {
                    if (fragment != srcFragment) {
                        ft.hide(fragment);
                    }
                }
                break;
            case TYPE_REPLACE_FRAGMENT:
                args = destFragments[0].getArguments();
                if (args == null) return;
                name = args.getString(ARGS_TAG, destFragments[0].getClass().getName());
                ft.replace(args.getInt(ARGS_ID), destFragments[0], name);
                if (args.getBoolean(ARGS_IS_ADD_STACK)) ft.addToBackStack(name);
                break;
            case TYPE_REMOVE_FRAGMENT:
                for (Fragment fragment : destFragments) {
                    if (fragment != srcFragment) {
                        ft.remove(fragment);
                    }
                }
                break;
            case TYPE_REMOVE_TO_FRAGMENT:
                for (int i = destFragments.length - 1; i >= 0; --i) {
                    Fragment fragment = destFragments[i];
                    if (fragment == destFragments[0]) {
                        if (srcFragment != null) ft.remove(fragment);
                        break;
                    }
                    ft.remove(fragment);
                }
                break;
        }
        ft.commitAllowingStateLoss();
        manager.executePendingTransactions();
    }

    /**
     * 添加动画
     * @param ft           FragmentTransaction
     * @param enterAnim    进入、添加动画
     * @param exitAnim     退出、移除动画
     * @param popEnterAnim 回退栈进入、添加动画
     * @param popExitAnim  回退栈退出、移除动画
     */
    private static void addAnim(
            final FragmentTransaction ft,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim);
    }

    /**
     * Used with custom Transitions to map a View from a removed or hidden
     * Fragment to a View from a shown or added Fragment
     * @param ft    FragmentTransaction
     * @param views A View in a disappearing Fragment to match with a View in an
     *              appearing Fragment
     */
    private static void addSharedElement(
            final FragmentTransaction ft,
            final View... views
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (View view : views) {
                ft.addSharedElement(view, view.getTransitionName());
            }
        }
    }
}