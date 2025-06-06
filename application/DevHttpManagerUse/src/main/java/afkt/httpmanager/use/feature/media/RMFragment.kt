package afkt.httpmanager.use.feature.media

import afkt.httpmanager.use.BR
import afkt.httpmanager.use.R
import afkt.httpmanager.use.base.BaseFragment
import afkt.httpmanager.use.databinding.FragmentRmBinding
import afkt.httpmanager.use.network.helper.ResponseHelper
import androidx.navigation.fragment.findNavController
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar

class RMFragment : BaseFragment<FragmentRmBinding, RMViewModel>(
    R.layout.fragment_rm, BR.viewModel, simple_Agile = { frg ->
        if (frg is RMFragment) {
            frg.apply {
                binding.vidTitle.setOnTitleBarListener(object : OnTitleBarListener {
                    override fun onLeftClick(titleBar: TitleBar) {
                        findNavController().popBackStack()
                    }
                })
            }
        }
    }
) {
    override fun initObserve() {
        super.initObserve()
        // 监听摄影图片列表信息变更
        viewModel.photoList.observe(this) { value ->
            // 打印日志
            ResponseHelper.log(
                "photoListLiveData",
                "设置 MutableLiveData<List<PhotoBean>> 值：${value}"
            )
        }
        // 监听电影详情信息变更
        viewModel.movieDetail.observe(this) { value ->
            // 打印日志
            ResponseHelper.log(
                "movieDetailLiveData",
                "设置 MutableLiveData<MovieDetailBean> 值：${value}"
            )
        }
    }
}