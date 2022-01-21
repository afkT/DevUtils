package afkt.project.ui.adapter.multitype

import afkt.project.R
import afkt.project.databinding.AdapterConcatClassifyBinding
import afkt.project.model.bean.ClassifyBeanItem3
import android.view.LayoutInflater
import android.view.ViewGroup
import com.drakeet.multitype.ItemViewBinder
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.quick.QuickHelper

/**
 * detail: 三级分类 Adapter
 * @author Ttt
 */
class Classify3ItemViewBinder : ItemViewBinder<ClassifyBeanItem3, DevBaseViewBindingVH<AdapterConcatClassifyBinding>>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): DevBaseViewBindingVH<AdapterConcatClassifyBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_concat_classify)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterConcatClassifyBinding>,
        item: ClassifyBeanItem3
    ) {
        val itemObj = item.obj

        QuickHelper.get(holder.binding.vidTitleTv)
            .setText(itemObj.name)
            .setBackgroundColor(itemObj.background)
            .setPaddingLeft(ResourceUtils.getDimensionInt(R.dimen.dp_60))
    }
}