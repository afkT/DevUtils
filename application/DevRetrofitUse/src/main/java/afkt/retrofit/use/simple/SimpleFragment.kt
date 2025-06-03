package afkt.retrofit.use.simple

import afkt.retrofit.use.BR
import afkt.retrofit.use.R
import afkt.retrofit.use.base.AppApplication
import afkt.retrofit.use.base.BaseFragment
import afkt.retrofit.use.databinding.FragmentSimpleBinding
import afkt.retrofit.use.helper.ResponseHelper
import androidx.databinding.Observable
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar

class SimpleFragment : BaseFragment<FragmentSimpleBinding, SimpleViewModel>(
    R.layout.fragment_simple, BR.viewModel, simple_Agile = { frg ->
        if (frg is SimpleFragment) {
            frg.apply {
                binding.vidTitle.setOnTitleBarListener(object : OnTitleBarListener {
                    override fun onLeftClick(titleBar: TitleBar) {
                        AppApplication.viewModel()?.postBackPressed()
                    }
                })
            }
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