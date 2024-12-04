package afkt.demo.ui.fragment

import afkt.demo.R
import afkt.demo.databinding.FragmentParentDataBinding
import afkt.demo.model.FragmentViewModel
import afkt.demo.utils.ViewModelTempUtils
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import dev.base.expand.mvvm.DevBaseMVVMFragment
import dev.utils.DevFinal
import dev.utils.LogPrintUtils
import dev.utils.app.HandlerUtils
import dev.utils.common.RandomUtils

/**
 * detail: 测试 Parent Fragment MVVM Fragment
 * @author Ttt
 */
class FragmentParentMVVMFragment :
    DevBaseMVVMFragment<FragmentParentDataBinding, FragmentViewModel>() {

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
            HandlerUtils.postRunnable({
                viewModel.number.value = RandomUtils.nextInt()
            }, (position + 1) * 1000L + 2000L)
            // 判断是否达到最大值
            if (position >= max) return

            // 设置 Fragment
            commit(childFragmentManager, binding.vidFl.id, position + 1, max)
        }

        LogPrintUtils.dTag(
            LOG_TAG,
            "FragmentParentMVVMFragment => parentFragment: %s",
            parentFragment
        )
    }

    companion object {
        fun get(
            position: Int,
            max: Int
        ): DevBaseMVVMFragment<FragmentParentDataBinding, FragmentViewModel> {
            val fragment = FragmentParentMVVMFragment()
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
            val transaction = manager.beginTransaction()
            transaction.add(id, get(position, max))
            transaction.commit()
        }

        const val LOG_TAG = "FragmentParentMVVMFragment_TAG"
    }

    override fun initViewModel() {
        viewModel = if (parentFragment == null) {
            getFragmentViewModel(FragmentViewModel::class.java)!!
        } else {
            getFragmentViewModel(parentFragment, FragmentViewModel::class.java)!!
        }
    }
}