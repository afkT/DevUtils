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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId
    ) {
        return add(manager, add, containerId, null, false, false);
    }

    /**
     * 添加 Fragment
     * @param manager     FragmentManager
     * @param add         待添加 Fragment
     * @param containerId 容器 id
     * @param isHide      {@code true} hide, {@code false} otherwise
     * @return {@code true} success, {@code false} fail
     */
    public static boolean add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final boolean isHide
    ) {
        return add(manager, add, containerId, null, isHide, false);
    }

    /**
     * 添加 Fragment
     * @param manager     FragmentManager
     * @param add         待添加 Fragment
     * @param containerId 容器 id
     * @param isHide      {@code true} hide, {@code false} otherwise
     * @param isAddStack  {@code true} add fragment in stack, {@code false} otherwise
     * @return {@code true} success, {@code false} fail
     */
    public static boolean add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final boolean isHide,
            final boolean isAddStack
    ) {
        return add(manager, add, containerId, null, isHide, isAddStack);
    }

    /**
     * 添加 Fragment
     * @param manager     FragmentManager
     * @param add         待添加 Fragment
     * @param containerId 容器 id
     * @param enterAnim   进入、添加动画
     * @param exitAnim    退出、移除动画
     * @return {@code true} success, {@code false} fail
     */
    public static boolean add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim
    ) {
        return add(
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final boolean isAddStack,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim
    ) {
        return add(
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        return add(
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final boolean isAddStack,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        return add(
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final View... sharedElements
    ) {
        return add(manager, add, containerId, null, false, sharedElements);
    }

    /**
     * 添加 Fragment
     * @param manager        FragmentManager
     * @param add            待添加 Fragment
     * @param containerId    容器 id
     * @param isAddStack     {@code true} add fragment in stack, {@code false} otherwise
     * @param sharedElements A View in a disappearing Fragment to match with a View in an
     *                       appearing Fragment
     * @return {@code true} success, {@code false} fail
     */
    public static boolean add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final boolean isAddStack,
            final View... sharedElements
    ) {
        return add(manager, add, containerId, null, isAddStack, sharedElements);
    }

    /**
     * 添加 Fragment
     * @param manager     FragmentManager
     * @param adds        待添加 Fragment List
     * @param containerId 容器 id
     * @param showIndex   待显示的 Fragment 索引
     * @return {@code true} success, {@code false} fail
     */
    public static boolean add(
            final FragmentManager manager,
            final List<Fragment> adds,
            @IdRes final int containerId,
            final int showIndex
    ) {
        if (adds == null) return false;
        return add(
                manager, adds.toArray(new Fragment[0]),
                containerId, null, showIndex
        );
    }

    /**
     * 添加 Fragment
     * @param manager     FragmentManager
     * @param adds        待添加 Fragments
     * @param containerId 容器 id
     * @param showIndex   待显示的 Fragment 索引
     * @return {@code true} success, {@code false} fail
     */
    public static boolean add(
            final FragmentManager manager,
            final Fragment[] adds,
            @IdRes final int containerId,
            final int showIndex
    ) {
        return add(manager, adds, containerId, null, showIndex);
    }

    /**
     * 添加 Fragment
     * @param manager     FragmentManager
     * @param add         待添加 Fragment
     * @param containerId 容器 id
     * @param destTag     fragment tag
     * @return {@code true} success, {@code false} fail
     */
    public static boolean add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final String destTag
    ) {
        return add(manager, add, containerId, destTag, false, false);
    }

    /**
     * 添加 Fragment
     * @param manager     FragmentManager
     * @param add         待添加 Fragment
     * @param containerId 容器 id
     * @param destTag     fragment 标记 tag
     * @param isHide      {@code true} hide, {@code false} otherwise
     * @return {@code true} success, {@code false} fail
     */
    public static boolean add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final String destTag,
            final boolean isHide
    ) {
        return add(manager, add, containerId, destTag, isHide, false);
    }

    /**
     * 添加 Fragment
     * @param manager     FragmentManager
     * @param add         待添加 Fragment
     * @param containerId 容器 id
     * @param destTag     fragment 标记 tag
     * @param isHide      {@code true} hide, {@code false} otherwise
     * @param isAddStack  {@code true} add fragment in stack, {@code false} otherwise
     * @return {@code true} success, {@code false} fail
     */
    public static boolean add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final String destTag,
            final boolean isHide,
            final boolean isAddStack
    ) {
        if (manager == null || add == null) return false;
        putArgs(add, new Args(containerId, destTag, isHide, isAddStack));
        return operateNoAnim(TYPE_ADD_FRAGMENT, manager, null, add);
    }

    /**
     * 添加 Fragment
     * @param manager     FragmentManager
     * @param add         待添加 Fragment
     * @param containerId 容器 id
     * @param destTag     fragment 标记 tag
     * @param enterAnim   进入、添加动画
     * @param exitAnim    退出、移除动画
     * @return {@code true} success, {@code false} fail
     */
    public static boolean add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final String destTag,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim
    ) {
        return add(
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final String destTag,
            final boolean isAddStack,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim
    ) {
        return add(
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final String destTag,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        return add(
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean add(
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
        if (manager == null || add == null) return false;
        FragmentTransaction ft = manager.beginTransaction();
        putArgs(add, new Args(containerId, destTag, false, isAddStack));
        addAnim(ft, enterAnim, exitAnim, popEnterAnim, popExitAnim);
        return operate(TYPE_ADD_FRAGMENT, manager, ft, null, add);
    }

    /**
     * 添加 Fragment
     * @param manager        FragmentManager
     * @param add            待添加 Fragment
     * @param containerId    容器 id
     * @param destTag        fragment 标记 tag
     * @param sharedElements A View in a disappearing Fragment to match with a View in an
     *                       appearing Fragment
     * @return {@code true} success, {@code false} fail
     */
    public static boolean add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final String destTag,
            final View... sharedElements
    ) {
        return add(manager, add, containerId, destTag, false, sharedElements);
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean add(
            final FragmentManager manager,
            final Fragment add,
            @IdRes final int containerId,
            final String destTag,
            final boolean isAddStack,
            final View... sharedElements
    ) {
        if (manager == null || add == null) return false;
        FragmentTransaction ft = manager.beginTransaction();
        putArgs(add, new Args(containerId, destTag, false, isAddStack));
        addSharedElement(ft, sharedElements);
        return operate(TYPE_ADD_FRAGMENT, manager, ft, null, add);
    }

    /**
     * 添加 Fragment
     * @param manager     FragmentManager
     * @param adds        待添加 Fragment List
     * @param containerId 容器 id
     * @param destTags    fragment 标记 tag
     * @param showIndex   待显示的 Fragment 索引
     * @return {@code true} success, {@code false} fail
     */
    public static boolean add(
            final FragmentManager manager,
            final List<Fragment> adds,
            @IdRes final int containerId,
            final String[] destTags,
            final int showIndex
    ) {
        if (manager == null || adds == null) return false;
        return add(
                manager, adds.toArray(new Fragment[0]),
                containerId, destTags, showIndex
        );
    }

    /**
     * 添加 Fragment
     * @param manager     FragmentManager
     * @param adds        待添加 Fragments
     * @param containerId 容器 id
     * @param destTags    fragment 标记 tag
     * @param showIndex   待显示的 Fragment 索引
     * @return {@code true} success, {@code false} fail
     */
    public static boolean add(
            final FragmentManager manager,
            final Fragment[] adds,
            @IdRes final int containerId,
            final String[] destTags,
            final int showIndex
    ) {
        if (manager == null || adds == null) return false;
        if (destTags == null) {
            for (int i = 0, len = adds.length; i < len; ++i) {
                putArgs(adds[i], new Args(containerId, null,
                        showIndex != i, false));
            }
        } else {
            if (adds.length != destTags.length) return false;
            for (int i = 0, len = adds.length; i < len; ++i) {
                putArgs(adds[i], new Args(containerId, destTags[i],
                        showIndex != i, false));
            }
        }
        return operateNoAnim(TYPE_ADD_FRAGMENT, manager, null, adds);
    }

    // ========
    // = show =
    // ========

    /**
     * 显示 Fragment
     * @param show 待显示 Fragment
     * @return {@code true} success, {@code false} fail
     */
    public static boolean show(final Fragment show) {
        if (show == null) return false;
        putArgs(show, false);
        return operateNoAnim(
                TYPE_SHOW_FRAGMENT, show.getFragmentManager(),
                null, show
        );
    }

    /**
     * 显示 Fragment
     * @param manager FragmentManager
     * @return {@code true} success, {@code false} fail
     */
    public static boolean show(final FragmentManager manager) {
        List<Fragment> fragments = getFragments(manager);
        for (Fragment show : fragments) {
            putArgs(show, false);
        }
        return operateNoAnim(
                TYPE_SHOW_FRAGMENT, manager, null,
                fragments.toArray(new Fragment[0])
        );
    }

    // ========
    // = hide =
    // ========

    /**
     * Hide fragment
     * @param hide 待隐藏 Fragment
     * @return {@code true} success, {@code false} fail
     */
    public static boolean hide(final Fragment hide) {
        if (hide == null) return false;
        putArgs(hide, true);
        return operateNoAnim(
                TYPE_HIDE_FRAGMENT, hide.getFragmentManager(),
                null, hide
        );
    }

    /**
     * Hide fragment
     * @param manager FragmentManager
     * @return {@code true} success, {@code false} fail
     */
    public static boolean hide(final FragmentManager manager) {
        List<Fragment> fragments = getFragments(manager);
        for (Fragment hide : fragments) {
            putArgs(hide, true);
        }
        return operateNoAnim(
                TYPE_HIDE_FRAGMENT, manager,
                null, fragments.toArray(new Fragment[0])
        );
    }

    // ============
    // = showHide =
    // ============

    /**
     * 显示 Fragment 并隐藏其他 Fragment
     * @param show 待显示 Fragment
     * @param hide 待隐藏 Fragment
     * @return {@code true} success, {@code false} fail
     */
    public static boolean showHide(
            final Fragment show,
            final Fragment hide
    ) {
        return showHide(show, Collections.singletonList(hide));
    }

    /**
     * 显示 Fragment 并隐藏其他 Fragment
     * @param showIndex 待显示的 Fragment 索引
     * @param fragments 待隐藏 Fragment
     * @return {@code true} success, {@code false} fail
     */
    public static boolean showHide(
            final int showIndex,
            final Fragment... fragments
    ) {
        if (fragments == null || fragments.length <= showIndex) return false;
        return showHide(fragments[showIndex], fragments);
    }

    /**
     * 显示 Fragment 并隐藏其他 Fragment
     * @param show 待显示 Fragment
     * @param hide 待隐藏 Fragment
     * @return {@code true} success, {@code false} fail
     */
    public static boolean showHide(
            final Fragment show,
            final Fragment... hide
    ) {
        if (hide == null) return false;
        return showHide(show, Arrays.asList(hide));
    }

    /**
     * 显示 Fragment 并隐藏其他 Fragment
     * @param showIndex 待显示的 Fragment 索引
     * @param fragments 待隐藏 Fragment List
     * @return {@code true} success, {@code false} fail
     */
    public static boolean showHide(
            final int showIndex,
            final List<Fragment> fragments
    ) {
        if (fragments == null || fragments.size() <= showIndex) return false;
        return showHide(fragments.get(showIndex), fragments);
    }

    /**
     * 显示 Fragment 并隐藏其他 Fragment
     * @param show 待显示 Fragment
     * @param hide 待隐藏 Fragment List
     * @return {@code true} success, {@code false} fail
     */
    public static boolean showHide(
            final Fragment show,
            final List<Fragment> hide
    ) {
        if (show == null || hide == null) return false;
        for (Fragment fragment : hide) {
            putArgs(fragment, fragment != show);
        }
        return operateNoAnim(
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean showHide(
            final Fragment show,
            final Fragment hide,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        if (show == null || hide == null) return false;
        return showHide(
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean showHide(
            final int showIndex,
            final List<Fragment> fragments,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        if (fragments == null || fragments.size() <= showIndex) return false;
        return showHide(
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean showHide(
            final Fragment show,
            final List<Fragment> hide,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        if (show == null || hide == null) return false;
        for (Fragment fragment : hide) {
            putArgs(fragment, fragment != show);
        }
        FragmentManager manager = show.getFragmentManager();
        if (manager == null) return false;
        FragmentTransaction ft = manager.beginTransaction();
        addAnim(ft, enterAnim, exitAnim, popEnterAnim, popExitAnim);
        return operate(
                TYPE_SHOW_HIDE_FRAGMENT, manager, ft,
                show, hide.toArray(new Fragment[0])
        );
    }

    // ===========
    // = replace =
    // ===========

    /**
     * 替换 Fragment
     * @param srcFragment  The source of fragment
     * @param destFragment The destination of fragment
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final Fragment srcFragment,
            final Fragment destFragment
    ) {
        return replace(srcFragment, destFragment, null, false);
    }

    /**
     * 替换 Fragment
     * @param srcFragment  The source of fragment
     * @param destFragment The destination of fragment
     * @param isAddStack   {@code true} add fragment in stack, {@code false} otherwise
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            final boolean isAddStack
    ) {
        return replace(srcFragment, destFragment, null, isAddStack);
    }

    /**
     * 替换 Fragment
     * @param srcFragment  The source of fragment
     * @param destFragment The destination of fragment
     * @param enterAnim    进入、添加动画
     * @param exitAnim     退出、移除动画
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim
    ) {
        return replace(
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            final boolean isAddStack,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim
    ) {
        return replace(
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        return replace(
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            final boolean isAddStack,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        return replace(
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            final View... sharedElements
    ) {
        return replace(
                srcFragment, destFragment, null,
                false, sharedElements
        );
    }

    /**
     * 替换 Fragment
     * @param srcFragment    The source of fragment
     * @param destFragment   The destination of fragment
     * @param isAddStack     {@code true} add fragment in stack, {@code false} otherwise
     * @param sharedElements A View in a disappearing Fragment to match with a View in an
     *                       appearing Fragment
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            final boolean isAddStack,
            final View... sharedElements
    ) {
        return replace(
                srcFragment, destFragment, null,
                isAddStack, sharedElements
        );
    }

    /**
     * 替换 Fragment
     * @param manager     FragmentManager
     * @param fragment    待替换 Fragment
     * @param containerId 容器 id
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId
    ) {
        return replace(manager, fragment, containerId, null, false);
    }

    /**
     * 替换 Fragment
     * @param manager     FragmentManager
     * @param fragment    待替换 Fragment
     * @param containerId 容器 id
     * @param isAddStack  {@code true} add fragment in stack, {@code false} otherwise
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            final boolean isAddStack
    ) {
        return replace(manager, fragment, containerId, null, isAddStack);
    }

    /**
     * 替换 Fragment
     * @param manager     FragmentManager
     * @param fragment    待替换 Fragment
     * @param containerId 容器 id
     * @param enterAnim   进入、添加动画
     * @param exitAnim    退出、移除动画
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim
    ) {
        return replace(
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            final boolean isAddStack,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim
    ) {
        return replace(
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        return replace(
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            final boolean isAddStack,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        return replace(
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            final View... sharedElements
    ) {
        return replace(
                manager, fragment, containerId,
                null, false, sharedElements
        );
    }

    /**
     * 替换 Fragment
     * @param manager        FragmentManager
     * @param fragment       待替换 Fragment
     * @param containerId    容器 id
     * @param isAddStack     {@code true} add fragment in stack, {@code false} otherwise
     * @param sharedElements A View in a disappearing Fragment to match with a View in an
     *                       appearing Fragment
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            final boolean isAddStack,
            final View... sharedElements
    ) {
        return replace(
                manager, fragment, containerId,
                null, isAddStack, sharedElements
        );
    }

    /**
     * 替换 Fragment
     * @param srcFragment  The source of fragment
     * @param destFragment The destination of fragment
     * @param destTag      fragment 标记 tag
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            final String destTag
    ) {
        return replace(srcFragment, destFragment, destTag, false);
    }

    /**
     * 替换 Fragment
     * @param srcFragment  The source of fragment
     * @param destFragment The destination of fragment
     * @param destTag      fragment 标记 tag
     * @param isAddStack   {@code true} add fragment in stack, {@code false} otherwise
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            final String destTag,
            final boolean isAddStack
    ) {
        if (srcFragment == null) return false;
        FragmentManager manager = srcFragment.getFragmentManager();
        if (manager == null) return false;
        Args args = getArgs(srcFragment);
        return replace(manager, destFragment, args.id, destTag, isAddStack);
    }

    /**
     * 替换 Fragment
     * @param srcFragment  The source of fragment
     * @param destFragment The destination of fragment
     * @param destTag      fragment 标记 tag
     * @param enterAnim    进入、添加动画
     * @param exitAnim     退出、移除动画
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            final String destTag,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim
    ) {
        return replace(
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            final String destTag,
            final boolean isAddStack,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim
    ) {
        return replace(
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            final String destTag,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        return replace(
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            final String destTag,
            final boolean isAddStack,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        if (srcFragment == null) return false;
        FragmentManager manager = srcFragment.getFragmentManager();
        if (manager == null) return false;
        Args args = getArgs(srcFragment);
        return replace(
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            final String destTag,
            final View... sharedElements
    ) {
        return replace(
                srcFragment, destFragment, destTag,
                false, sharedElements
        );
    }

    /**
     * 替换 Fragment
     * @param srcFragment    The source of fragment
     * @param destFragment   The destination of fragment
     * @param destTag        fragment 标记 tag
     * @param isAddStack     {@code true} add fragment in stack, {@code false} otherwise
     * @param sharedElements A View in a disappearing Fragment to match with a View in an
     *                       appearing Fragment
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final Fragment srcFragment,
            final Fragment destFragment,
            final String destTag,
            final boolean isAddStack,
            final View... sharedElements
    ) {
        if (srcFragment == null) return false;
        FragmentManager manager = srcFragment.getFragmentManager();
        if (manager == null) return false;
        Args args = getArgs(srcFragment);
        return replace(
                manager, destFragment, args.id,
                destTag, isAddStack, sharedElements
        );
    }

    /**
     * 替换 Fragment
     * @param manager     FragmentManager
     * @param fragment    待替换 Fragment
     * @param containerId 容器 id
     * @param destTag     fragment 标记 tag
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            final String destTag
    ) {
        return replace(manager, fragment, containerId, destTag, false);
    }

    /**
     * 替换 Fragment
     * @param manager     FragmentManager
     * @param fragment    待替换 Fragment
     * @param containerId 容器 id
     * @param destTag     fragment 标记 tag
     * @param isAddStack  {@code true} add fragment in stack, {@code false} otherwise
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            final String destTag,
            final boolean isAddStack
    ) {
        if (manager == null || fragment == null) return false;
        FragmentTransaction ft = manager.beginTransaction();
        putArgs(fragment, new Args(containerId, destTag, false, isAddStack));
        return operate(
                TYPE_REPLACE_FRAGMENT, manager,
                ft, null, fragment
        );
    }

    /**
     * 替换 Fragment
     * @param manager     FragmentManager
     * @param fragment    待替换 Fragment
     * @param containerId 容器 id
     * @param destTag     fragment 标记 tag
     * @param enterAnim   进入、添加动画
     * @param exitAnim    退出、移除动画
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            final String destTag,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim
    ) {
        return replace(
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            final String destTag,
            final boolean isAddStack,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim
    ) {
        return replace(
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            final String destTag,
            @AnimatorRes @AnimRes final int enterAnim,
            @AnimatorRes @AnimRes final int exitAnim,
            @AnimatorRes @AnimRes final int popEnterAnim,
            @AnimatorRes @AnimRes final int popExitAnim
    ) {
        return replace(
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
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
        if (manager == null || fragment == null) return false;
        FragmentTransaction ft = manager.beginTransaction();
        putArgs(fragment, new Args(containerId, destTag, false, isAddStack));
        addAnim(ft, enterAnim, exitAnim, popEnterAnim, popExitAnim);
        return operate(TYPE_REPLACE_FRAGMENT, manager, ft, null, fragment);
    }

    /**
     * 替换 Fragment
     * @param manager        FragmentManager
     * @param fragment       待替换 Fragment
     * @param containerId    容器 id
     * @param destTag        fragment 标记 tag
     * @param sharedElements A View in a disappearing Fragment to match with a View in an
     *                       appearing Fragment
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            final String destTag,
            final View... sharedElements
    ) {
        return replace(
                manager, fragment, containerId,
                destTag, false, sharedElements
        );
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean replace(
            final FragmentManager manager,
            final Fragment fragment,
            @IdRes final int containerId,
            final String destTag,
            final boolean isAddStack,
            final View... sharedElements
    ) {
        if (manager == null || fragment == null) return false;
        FragmentTransaction ft = manager.beginTransaction();
        putArgs(fragment, new Args(containerId, destTag, false, isAddStack));
        addSharedElement(ft, sharedElements);
        return operate(TYPE_REPLACE_FRAGMENT, manager, ft, null, fragment);
    }

    // =======
    // = pop =
    // =======

    /**
     * 回退 Fragment
     * @param manager FragmentManager
     * @return {@code true} success, {@code false} fail
     */
    public static boolean pop(final FragmentManager manager) {
        return pop(manager, true);
    }

    /**
     * 回退 Fragment
     * @param manager     FragmentManager
     * @param isImmediate 是否立即操作
     * @return {@code true} success, {@code false} fail
     */
    public static boolean pop(
            final FragmentManager manager,
            final boolean isImmediate
    ) {
        if (manager == null) return false;
        try {
            if (isImmediate) {
                manager.popBackStackImmediate();
            } else {
                manager.popBackStack();
            }
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "pop");
        }
        return false;
    }

    /**
     * 回退到指定 Fragment
     * @param manager       FragmentManager
     * @param popClazz      待回退 Fragment
     * @param isIncludeSelf 是否包含自身
     * @return {@code true} success, {@code false} fail
     */
    public static boolean popTo(
            final FragmentManager manager,
            final Class<? extends Fragment> popClazz,
            final boolean isIncludeSelf
    ) {
        return popTo(manager, popClazz, isIncludeSelf, true);
    }

    /**
     * 回退到指定 Fragment
     * @param manager       FragmentManager
     * @param popClazz      待回退 Fragment
     * @param isIncludeSelf 是否包含自身
     * @param isImmediate   是否立即操作
     * @return {@code true} success, {@code false} fail
     */
    public static boolean popTo(
            final FragmentManager manager,
            final Class<? extends Fragment> popClazz,
            final boolean isIncludeSelf,
            final boolean isImmediate
    ) {
        if (manager == null || popClazz == null) return false;
        try {
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
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "popTo");
        }
        return false;
    }

    /**
     * 回退所有 Fragment
     * @param manager FragmentManager
     * @return {@code true} success, {@code false} fail
     */
    public static boolean popAll(final FragmentManager manager) {
        return popAll(manager, true);
    }

    /**
     * 回退所有 Fragment
     * @param manager     FragmentManager
     * @param isImmediate 是否立即操作
     * @return {@code true} success, {@code false} fail
     */
    public static boolean popAll(
            final FragmentManager manager,
            final boolean isImmediate
    ) {
        if (manager == null || manager.getBackStackEntryCount() <= 0) return false;
        try {
            FragmentManager.BackStackEntry entry = manager.getBackStackEntryAt(0);
            if (isImmediate) {
                manager.popBackStackImmediate(
                        entry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE
                );
            } else {
                manager.popBackStack(
                        entry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE
                );
            }
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "popAll");
        }
        return false;
    }

    // ==========
    // = remove =
    // ==========

    /**
     * 移除 Fragment
     * @param remove 待移除 Fragment
     * @return {@code true} success, {@code false} fail
     */
    public static boolean remove(final Fragment remove) {
        if (remove == null) return false;
        return operateNoAnim(
                TYPE_REMOVE_FRAGMENT, remove.getFragmentManager(),
                null, remove
        );
    }

    /**
     * 移除 Fragment
     * @param removeTo      待移除 Fragment
     * @param isIncludeSelf 是否包含自身
     * @return {@code true} success, {@code false} fail
     */
    public static boolean removeTo(
            final Fragment removeTo,
            final boolean isIncludeSelf
    ) {
        if (removeTo == null) return false;
        return operateNoAnim(
                TYPE_REMOVE_TO_FRAGMENT, removeTo.getFragmentManager(),
                isIncludeSelf ? removeTo : null, removeTo
        );
    }

    /**
     * 移除全部 Fragment
     * @param manager FragmentManager
     * @return {@code true} success, {@code false} fail
     */
    public static boolean removeAll(final FragmentManager manager) {
        List<Fragment> fragments = getFragments(manager);
        return operateNoAnim(
                TYPE_REMOVE_FRAGMENT, manager, null,
                fragments.toArray(new Fragment[0])
        );
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
        if (manager == null || clazz == null) return null;
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
        if (manager == null || destTag == null) return null;
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
        try {
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
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getTopIsInStack");
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
        try {
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
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getTopShowIsInStack");
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
        if (manager == null) return Collections.emptyList();
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
        List<Fragment> result    = new ArrayList<>();
        List<Fragment> fragments = getFragments(manager);
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
        if (manager == null) return result;
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
        if (manager == null) return result;
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
     * @return {@code true} 消费, {@code false} otherwise
     */
    public static boolean dispatchBackPress(final Fragment fragment) {
        try {
            return fragment != null && fragment.isResumed()
                    && fragment.isVisible()
                    && fragment.getUserVisibleHint()
                    && fragment instanceof OnBackClickListener
                    && ((OnBackClickListener) fragment).onBackClick();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "dispatchBackPress - fragment");
        }
        return false;
    }

    /**
     * 调用 Fragment OnBackClickListener 校验是否进行消费
     * @param manager FragmentManager
     * @return {@code true} 消费, {@code false} otherwise
     */
    public static boolean dispatchBackPress(final FragmentManager manager) {
        if (manager == null) return false;
        List<Fragment> fragments = getFragments(manager);
        if (fragments.isEmpty()) return false;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (dispatchBackPress(fragment)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置 Fragment View 背景
     * @param fragment Fragment
     * @param color    背景颜色
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setBackgroundColor(
            final Fragment fragment,
            @ColorInt final int color
    ) {
        if (fragment == null) return false;
        View view = fragment.getView();
        if (view == null) return false;
        view.setBackgroundColor(color);
        return true;
    }

    /**
     * 设置 Fragment View 背景
     * @param fragment Fragment
     * @param resId    资源 id
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setBackgroundResource(
            final Fragment fragment,
            @DrawableRes final int resId
    ) {
        if (fragment == null) return false;
        View view = fragment.getView();
        if (view == null) return false;
        view.setBackgroundResource(resId);
        return true;
    }

    /**
     * 设置 Fragment View 背景
     * @param fragment   Fragment
     * @param background 背景
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setBackground(
            final Fragment fragment,
            final Drawable background
    ) {
        if (fragment == null) return false;
        View view = fragment.getView();
        if (view == null) return false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
        }
        return true;
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
        if (fragment == null) return;
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
     * @return {@code true} success, {@code false} fail
     */
    private static boolean operateNoAnim(
            final int type,
            final FragmentManager manager,
            final Fragment srcFragment,
            final Fragment... destFragments
    ) {
        if (manager == null) return false;
        FragmentTransaction ft = manager.beginTransaction();
        return operate(type, manager, ft, srcFragment, destFragments);
    }

    /**
     * 执行具体操作
     * @param type          操作类型
     * @param manager       FragmentManager
     * @param ft            FragmentTransaction
     * @param srcFragment   The source of fragment
     * @param destFragments The destination of fragment
     * @return {@code true} success, {@code false} fail
     */
    private static boolean operate(
            final int type,
            final FragmentManager manager,
            final FragmentTransaction ft,
            final Fragment srcFragment,
            final Fragment... destFragments
    ) {
        try {
            if (srcFragment != null && srcFragment.isRemoving()) {
                LogPrintUtils.eTag(
                        TAG, "operate - " + srcFragment.getClass().getName()
                                + " is isRemoving"
                );
                return false;
            }
            String name;
            Bundle args;
            switch (type) {
                case TYPE_ADD_FRAGMENT:
                    for (Fragment fragment : destFragments) {
                        args = fragment.getArguments();
                        if (args == null) return false;
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
                    if (srcFragment != null) {
                        ft.show(srcFragment);
                    }
                    for (Fragment fragment : destFragments) {
                        if (fragment != srcFragment) {
                            ft.hide(fragment);
                        }
                    }
                    break;
                case TYPE_REPLACE_FRAGMENT:
                    args = destFragments[0].getArguments();
                    if (args == null) return false;
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
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "operate");
        }
        return false;
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
            if (views == null) return;
            for (View view : views) {
                if (view != null) {
                    try {
                        ft.addSharedElement(view, view.getTransitionName());
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "addSharedElement");
                    }
                }
            }
        }
    }
}