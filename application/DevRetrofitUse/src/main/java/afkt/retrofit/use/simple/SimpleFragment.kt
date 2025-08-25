package afkt.retrofit.use.simple

import afkt.retrofit.use.BR
import afkt.retrofit.use.R
import afkt.retrofit.use.base.BaseFragment
import afkt.retrofit.use.databinding.FragmentSimpleBinding
import afkt.retrofit.use.helper.ResponseHelper
import androidx.databinding.Observable
import androidx.navigation.fragment.findNavController
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import dev.base.simple.extensions.asFragment

class SimpleFragment : BaseFragment<FragmentSimpleBinding, SimpleViewModel>(
    R.layout.fragment_simple, BR.viewModel, simple_Agile = { frg ->
        frg.asFragment<SimpleFragment> {
            binding.vidTitle.setOnTitleBarListener(object : OnTitleBarListener {
                override fun onLeftClick(titleBar: TitleBar) {
                    findNavController().popBackStack()
                }
            })
        }
    }
) {
    override fun initObserve() {
        super.initObserve()
        // 监听电影详情信息变更
        viewModel.movieDetailOB.addOnPropertyChangedCallback(
            object : Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(
                    sender: Observable?,
                    propertyId: Int
                ) {
                    val value = viewModel.movieDetailOB.get()
                    // 打印日志
                    ResponseHelper.log(
                        "movieDetailObservableField",
                        "设置 ObservableField<MovieDetailBean> 值：${value}"
                    )
                }
            }
        )
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