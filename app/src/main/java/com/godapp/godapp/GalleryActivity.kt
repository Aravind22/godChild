package com.godapp.godapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.godapp.godapp.adapter.GalleryImageAdapter
import com.godapp.godapp.adapter.GalleryImageClickListener
import com.godapp.godapp.adapter.Image
import kotlinx.android.synthetic.main.activity_main.*

class GalleryActivity : AppCompatActivity(), GalleryImageClickListener {

    // gallery column count
    private val SPAN_COUNT = 3

    private val imageList = ArrayList<Image>()
    lateinit var galleryAdapter: GalleryImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init adapter
        galleryAdapter = GalleryImageAdapter(imageList)
        galleryAdapter.listener = this

        // init recyclerview
        recyclerView.layoutManager = GridLayoutManager(this, SPAN_COUNT)
        recyclerView.adapter = galleryAdapter

        // load images
        loadImages()
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = baseContext.getString(R.string.english_photos)
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun loadImages() {

        imageList.add(Image(R.drawable.yogi_1, "Yogi"))
        imageList.add(Image(R.drawable.yogi_2, "Yogi"))
        imageList.add(Image(R.drawable.yogi_3, "Yogi"))
        imageList.add(Image(R.drawable.yogi_4, "Yogi"))
        imageList.add(Image(R.drawable.yogi_5, "Yogi"))
        imageList.add(Image(R.drawable.yogi_6, "Yogi"))
        imageList.add(Image(R.drawable.yogi_7, "Yogi"))



        galleryAdapter.notifyDataSetChanged()
    }

    override fun onClick(position: Int) {
        // handle click of
        val bundle = Bundle()
        bundle.putSerializable("images", imageList)
        bundle.putInt("position", position)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val galleryFragment = GalleryFullscreenFragment()
        galleryFragment.setArguments(bundle)
        galleryFragment.show(fragmentTransaction, "gallery")
    }
}