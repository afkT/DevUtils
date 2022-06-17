package afkt.demo.ui.fragment

import afkt.demo.R
import afkt.demo.databinding.FragmentParentDataBinding
import afkt.demo.model.ActivityViewModel
import afkt.demo.utils.ViewModelTempUtils
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import dev.base.expand.mvvm.DevBaseMVVMFragment
import dev.base.utils.ViewModelUtils
import dev.utils.DevFinal
import dev.utils.LogPrintUtils
import dev.utils.common.RandomUtils

/**
 * detail: 测试 MVVM Utils Fragment
 * @author Ttt
 */
class MVVMUtilsFragment :
    DevBaseMVVMFragment<FragmentParentDataBinding, ActivityViewModel>() {

    override fun baseContentId(): Int {
        return R.layout.fragment_parent_data
    }

    override fun baseContentView(): View? {
        return null
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        arguments?.let {
            val position = it.getInt(DevFinal.STR.POSITION)
            val max = it.getInt(DevFinal.STR.MAX)

            val positionStr = (position + 1).toString()

            // 设置索引文案
            binding.position = positionStr

            // 进行 ViewModel 绑定
            ViewModelTempUtils.observe(TAG + positionStr, this, viewModel)
            // 临时改变值
            Handler().postDelayed({
                viewModel.number.value = RandomUtils.nextInt()
            }, (position + 1) * 1000L + 2000L)
            // 判断是否达到最大值
            if (position >= max) return

            // 设置 Fragment
            commit(childFragmentManager, binding.vidFl.id, position + 1, max)
        }

        LogPrintUtils.dTag(LOG_TAG, "MVVMUtilsFragment => parentFragment: %s", parentFragment)
    }

    companion object {
        fun get(
            position: Int,
            max: Int
        ): DevBaseMVVMFragment<FragmentParentDataBinding, ActivityViewModel> {
            val fragment = MVVMUtilsFragment()
            val bundle = Bundle()
            bundle.putInt(DevFinal.STR.POSITION, position)
            bundle.putInt(DevFinal.STR.MAX, max)
            fragment.arguments = bundle
            return fragment
        }

        fun commit(
            manager: FragmentManager,
            id: Int,
            position: Int,
            max: Int
        ) {
            val transaction: FragmentTransaction = manager.beginTransaction()
            transaction.add(id, get(position, max))
            transaction.commit()
        }

        const val LOG_TAG = "MVVMUtilsFragment_TAG"
    }

    override fun initViewModel() {
//        viewModel = ViewModelUtils.getActivityViewModel(
//            viewModelAssist,
//            activity,
//            ActivityViewModel::class.java
//        )!!

//        viewModel = ViewModelUtils.getActivityViewModel(activity, ActivityViewModel::class.java)!!

//        viewModel = if (parentFragment == null) {
//            ViewModelUtils.getFragmentViewModel(this, ActivityViewModel::class.java)!!
//        } else {
//            ViewModelUtils.getFragmentViewModel(parentFragment, ActivityViewModel::class.java)!!
//        }

        viewModel = if (parentFragment == null) {
            ViewModelUtils.getFragmentViewModel(
                viewModelAssist,
                this,
                ActivityViewModel::class.java
            )!!
        } else {
            ViewModelUtils.getFragmentViewModel(
                viewModelAssist,
                parentFragment,
                ActivityViewModel::class.java
            )!!
        }
    }
}