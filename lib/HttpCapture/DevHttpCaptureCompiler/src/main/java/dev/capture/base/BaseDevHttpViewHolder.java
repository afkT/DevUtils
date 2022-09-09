package dev.capture.base;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

/**
 * detail: DevHttpCapture Base ViewHolder ViewBinding
 * @author Ttt
 */
public class BaseDevHttpViewHolder<VB extends ViewBinding>
        extends RecyclerView.ViewHolder {

    public VB binding;

    public BaseDevHttpViewHolder(View itemView) {
        super(itemView);
    }

    public BaseDevHttpViewHolder(final VB binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}