package afkt.project.ui.adapter

import afkt.project.R
import afkt.project.database.green.module.note.bean.Note
import afkt.project.database.green.module.note.bean.NotePicture
import afkt.project.database.green.module.note.bean.NoteType
import afkt.project.databinding.AdapterDatabaseBinding
import afkt.project.databinding.AdapterDatabaseImageBinding
import android.view.ViewGroup
import dev.adapter.DevDataAdapter
import dev.adapter.DevDataAdapterExt
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.engine.DevEngine
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
            .setText(note.text, holder.binding.vidAdbTitleTv)
            .setText(note.comment, holder.binding.vidAdbContentTv)
            .setText(
                DateUtils.formatDate(note.date, DevFinal.yyyyMMdd5),
                holder.binding.vidAdbTimeTv
            )
            .setVisibilitys(note.type != NoteType.PICTURE, holder.binding.vidAdbContentTv)
            .setVisibilitys(note.type != NoteType.TEXT, holder.binding.vidAdbRecy)
        val imgRecy = holder.binding.vidAdbRecy
        if (ViewUtils.isVisibility(imgRecy)) {
            imgRecy.adapter = ImageAdapter(note.pictures)
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
            DevEngine.getImage()?.display(
                holder.binding.vidAdbiIgview,
                getDataItem(position).picture
            )
        }
    }
}