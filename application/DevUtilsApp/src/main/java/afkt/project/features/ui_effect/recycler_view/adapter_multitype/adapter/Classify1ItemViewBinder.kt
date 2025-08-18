package afkt.project.features.ui_effect.recycler_view.adapter_multitype.adapter

import afkt.project.R
import afkt.project.databinding.AdapterConcatClassifyBinding
import afkt.project.features.ui_effect.recycler_view.adapter_concat.ClassifyBeanItem1
import android.view.LayoutInflater
import android.view.ViewGroup
import com.drakeet.multitype.ItemViewBinder
import dev.base.utils.adapter.DevBaseViewBindingVH
import dev.base.utils.adapter.newBindingViewHolder
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.quick.QuickHelper

/**
 * detail: 一级分类 Adapter
 * @author Ttt
 */
class Classify1ItemViewBinder :
    ItemViewBinder<ClassifyBeanItem1, DevBaseViewBindingVH<AdapterConcatClassifyBinding>>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): DevBaseViewBindingVH<AdapterConcatClassifyBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_concat_classify)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterConcatClassifyBinding>,
        item: ClassifyBeanItem1
    ) {
        val itemObj = item.obj

        QuickHelper.get(holder.binding.vidTitleTv)
            .setText(itemObj.name)
            .setBackgroundColor(itemObj.background)
            .setPaddingLeft(ResourceUtils.getDimensionInt(R.dimen.dp_20))
    }
}