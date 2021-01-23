package com.github.watabee.drawbitmapitemdecorationsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)

        val adapter = SampleAdapter()
        val items = (0 until 200)
            .map {
                val groupNo = it / 7 + 1
                val imageUrl = if (it % 7 == 0) {
                    "https://via.placeholder.com/208x294?text=$groupNo"
                } else {
                    null
                }

                SampleData(it, title = "title-$it", groupNo = groupNo, imageUrl = imageUrl)
            }
        adapter.submitList(items)

        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = object : LinearLayoutManager(this) {
            override fun calculateExtraLayoutSpace(
                state: RecyclerView.State,
                extraLayoutSpace: IntArray
            ) {
                extraLayoutSpace[0] = resources.getDimensionPixelSize(R.dimen.item_height)
                extraLayoutSpace[1] = 0
            }
        }
        recyclerView.addItemDecoration(DrawBitmapItemDecoration(this))
    }
}
