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
import dev.base.DevPage
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.engine.image.DevImageEngine
import dev.utils.app.ViewUtils
import dev.utils.app.helper.ViewHelper
import dev.utils.common.DateUtils

/**
 * detail: GreenDao 适配器
 * @author Ttt
 */
class GreenDaoAdapter : DevDataAdapterExt<Note, DevBaseViewBindingVH<AdapterDatabaseBinding>>() {

    init {
        page = DevPage<Note>(0, 8)
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
            .setText(holder.binding.vidAdbTitleTv, note.text)
            .setText(holder.binding.vidAdbContentTv, note.comment)
            .setText(
                holder.binding.vidAdbTimeTv,
                DateUtils.formatDate(note.date, "yyyy.MM.dd")
            )
            .setVisibility(note.type != NoteType.PICTURE, holder.binding.vidAdbContentTv)
            .setVisibility(note.type != NoteType.TEXT, holder.binding.vidAdbRecy)
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
            DevImageEngine.getEngine().display(
                holder.binding.vidAdbiIgview,
                getDataItem(position).picture
            )
        }
    }
}