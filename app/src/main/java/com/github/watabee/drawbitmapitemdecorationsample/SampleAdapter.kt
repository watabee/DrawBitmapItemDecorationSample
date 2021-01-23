package com.github.watabee.drawbitmapitemdecorationsample

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class SampleAdapter : ListAdapter<SampleData, SampleViewHolder>(createDiffUtilItemCallback()) {
    private var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        return SampleViewHolder(parent) {
            recyclerView?.apply {
                if (!isComputingLayout) {
                    invalidateItemDecorations()
                }
            }
        }
    }

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewRecycled(holder: SampleViewHolder) {
        holder.unbind()
    }

    public override fun getItem(position: Int): SampleData {
        return super.getItem(position)
    }

    companion object {
        fun createDiffUtilItemCallback() = object: DiffUtil.ItemCallback<SampleData>() {
            override fun areItemsTheSame(oldItem: SampleData, newItem: SampleData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SampleData, newItem: SampleData): Boolean {
                return oldItem == newItem
            }
        }
    }
}
