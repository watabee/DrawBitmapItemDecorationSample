package com.github.watabee.drawbitmapitemdecorationsample

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class SampleViewHolder(
    parent: ViewGroup,
    invalidateItemDecorations: () -> Unit
) : RecyclerView.ViewHolder(inflate(parent)) {

    var bitmap: Bitmap? = null
        private set

    private val titleTextView: TextView = itemView.findViewById(R.id.text)

    private val customTarget = object: CustomTarget<Bitmap>() {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            Log.e("SampleItem", "onResourceReady")
            bitmap = resource

            invalidateItemDecorations()
        }

        override fun onLoadStarted(placeholder: Drawable?) {
            super.onLoadStarted(placeholder)
            Log.e("SampleItem", "onLoadStarted")
            if (placeholder is BitmapDrawable) {
                bitmap = placeholder.bitmap
                invalidateItemDecorations()
            }
        }

        override fun onLoadFailed(errorDrawable: Drawable?) {
            super.onLoadFailed(errorDrawable)
            Log.e("SampleItem", "onLoadFailed")
            if (errorDrawable is BitmapDrawable) {
                bitmap = errorDrawable.bitmap
                invalidateItemDecorations()
            }
        }

        override fun onLoadCleared(placeholder: Drawable?) {
            Log.e("SampleItem", "onLoadCleared")
        }
    }

    fun bind(data: SampleData) {
        titleTextView.text = data.title

        clear(itemView)
        data.imageUrl?.let { imageUrl ->
            val url =
                GlideUrl(
                    imageUrl,
                    LazyHeaders.Builder().addHeader("User-Agent", WebSettings.getDefaultUserAgent(itemView.context)).build()
                )

            Glide.with(itemView)
                .asBitmap()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .load(url)
                .into(customTarget)
        }
    }

    fun unbind() {
        clear(itemView)
    }

    private fun clear(itemView: View) {
        bitmap = null
        Glide.with(itemView).clear(customTarget)
    }

    companion object {
        fun inflate(parent: ViewGroup): View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_sample, parent, false)
    }
}
