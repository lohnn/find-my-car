package se.designcoach.findmycar.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import android.support.v7.widget.Toolbar
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import se.designcoach.findmycar.R

/**
 * Created by lohnn-macPro on 08/04/16.
 */

class EditCarActivity : AppCompatActivity() {
    lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_car)

        //Setup toolbar
        editText = findViewById(R.id.car_name) as EditText
        setSupportActionBar(findViewById(R.id.toolbar) as Toolbar)
        supportActionBar?.title = ""
        val collapsingToolbar = findViewById(R.id.carActions_collapsing_toolbar) as CollapsingToolbarLayout
        val carImageView = findViewById(R.id.imageView) as ImageView
        Glide.with(applicationContext)
                .load(R.drawable.oldtimer)
                .asBitmap()
                .centerCrop()
                .listener(object : RequestListener<Int, Bitmap> {
                    override fun onResourceReady(resource: Bitmap?, model: Int?, target: Target<Bitmap>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        if (resource != null)
                            Palette.Builder(resource).maximumColorCount(30).generate { palette ->
                                val swatch = palette.vibrantSwatch ?: palette.mutedSwatch
                                val contentScrimColor = swatch?.rgb ?: ContextCompat.getColor(applicationContext, android.R.color.holo_purple)
                                collapsingToolbar.setBackgroundColor(contentScrimColor)
                                collapsingToolbar.setStatusBarScrimColor(contentScrimColor)
                                collapsingToolbar.setContentScrimColor(contentScrimColor)
//                                if (swatch != null) {
//                                    editText.setTextColor(swatch.bodyTextColor)
//                                }
                            }
                        return false
                    }

                    override fun onException(e: Exception?, model: Int?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                        collapsingToolbar.setContentScrimColor(android.R.color.holo_purple)
                        return false
                    }
                })
                .into(carImageView)
        Glide.with(applicationContext)
                .load(R.drawable.oldtimer)
                .centerCrop()
                .into(carImageView)
    }
}