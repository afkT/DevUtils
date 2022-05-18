package afkt.project.feature.lib_frame.green_dao

import afkt.project.R
import afkt.project.database.green.module.note.bean.Note
import afkt.project.database.green.module.note.bean.NotePicture
import afkt.project.database.green.module.note.bean.NoteType
import afkt.project.databinding.AdapterDatabaseBinding
import afkt.project.databinding.AdapterDatabaseImageBinding
import dev.kotlin.engine.image.display
import dev.kotlin.utils.toSource
import android.view.ViewGroup
import dev.adapter.DevDataAdapter
import dev.adapter.DevDataAdapterExt
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.utils.DevFinal
import dev.utils.app.ViewUtils
import dev.utils.app.helper.view.ViewHelper
import dev.utils.common.DateUtils

/**
 * detail: GreenDao 适配器
 * @author Ttt
 */
class GreenDaoAdapter : DevDataAdapterExt<Note, DevBaseViewBindingVH<AdapterDatabaseBinding>>() {

    init {
        setPage(0, 8)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<AdapterDatabaseBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_database)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterDatabaseBinding>,
        position: Int
    ) {
        val note = getDataItem(position)
        ViewHelper.get()
            .setText(note.text, holder.binding.vidTitleTv)
            .setText(note.comment, holder.binding.vidContentTv)
            .setText(
                DateUtils.formatDate(note.date, DevFinal.TIME.yyyyMMdd_POINT),
                holder.binding.vidTimeTv
            )
            .setVisibilitys(note.type != NoteType.PICTURE, holder.binding.vidContentTv)
            .setVisibilitys(note.type != NoteType.TEXT, holder.binding.vidRv)
        val imgRecy = holder.binding.vidRv
        if (ViewUtils.isVisibility(imgRecy)) {
            ImageAdapter(note.pictures).bindAdapter(imgRecy)
        }
    }

    internal inner class ImageAdapter(data: List<NotePicture>) : DevDataAdapter<NotePicture, DevBaseViewBindingVH<AdapterDatabaseImageBinding>>() {

        init {
            setDataList(data, false)
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): DevBaseViewBindingVH<AdapterDatabaseImageBinding> {
            return newBindingViewHolder(parent, R.layout.adapter_database_image)
        }

        override fun onBindViewHolder(
            holder: DevBaseViewBindingVH<AdapterDatabaseImageBinding>,
            position: Int
        ) {
            holder.binding.vidIv.display(
                source = getDataItem(position).picture.toSource()
            )
        }
    }
}