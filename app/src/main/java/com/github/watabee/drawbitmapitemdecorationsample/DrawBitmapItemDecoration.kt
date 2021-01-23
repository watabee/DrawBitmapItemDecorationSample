package com.github.watabee.drawbitmapitemdecorationsample

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class DrawBitmapItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val paint = Paint()
    private val textPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.BLACK
        isAntiAlias = true
        textSize = context.resources.getDimensionPixelSize(R.dimen.text_size).toFloat()
        textAlign = Paint.Align.CENTER
    }

    private val dstRect = Rect()

    private val imageWidth = context.resources.getDimensionPixelSize(R.dimen.image_width)
    private val imageHeight = context.resources.getDimensionPixelSize(R.dimen.image_height)
    private val spaceWidth = context.resources.getDimensionPixelSize(R.dimen.space)
    private val horizontalImageMargin = (spaceWidth - imageWidth).shr(1)

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val adapter = parent.adapter as? SampleAdapter ?: return

        for (i in 0 until parent.childCount) {
            val view = parent.getChildAt(i)
            val adapterPosition = parent.getChildAdapterPosition(view)
            if (adapterPosition < 0 || adapterPosition >= adapter.itemCount) {
                continue
            }
            val viewHolder = parent.findViewHolderForAdapterPosition(adapterPosition) as? SampleViewHolder ?: continue
            val data = adapter.getItem(adapterPosition)
            if (!data.imageUrl.isNullOrBlank()) {
                val imageTop = view.top + 16
                val imageBottom = imageTop + imageHeight

                viewHolder.bitmap?.let {
                    dstRect.set(horizontalImageMargin, imageTop, horizontalImageMargin + imageWidth, imageBottom)
                    c.drawBitmap(it, null, dstRect, paint)
                }

                val textY = imageBottom + 16 - textPaint.ascent()
                c.drawText("Group ${data.groupNo}", spaceWidth.shr(1).toFloat(), textY, textPaint)
            }
        }
    }
}
