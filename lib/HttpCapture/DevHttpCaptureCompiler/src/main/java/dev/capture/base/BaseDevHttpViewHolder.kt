package dev.capture.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * detail: DevHttpCapture Base ViewHolder ViewBinding
 * @author Ttt
 */
internal class BaseDevHttpViewHolder<VB : ViewBinding>(
    val binding: VB
) : RecyclerView.ViewHolder(binding.root)