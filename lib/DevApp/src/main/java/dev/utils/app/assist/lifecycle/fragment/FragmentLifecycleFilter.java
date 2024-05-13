package dev.utils.app.assist.lifecycle.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * detail: Fragment 生命周期 过滤判断接口
 * @author Ttt
 */
public interface FragmentLifecycleFilter {

    /**
     * 判断是否过滤该类 ( 不进行添加等操作 )
     * @param manager  FragmentManager
     * @param fragment Fragment
     * @return {@code true} yes, {@code false} no
     */
    boolean filter(
            FragmentManager manager,
            Fragment fragment
    );
}