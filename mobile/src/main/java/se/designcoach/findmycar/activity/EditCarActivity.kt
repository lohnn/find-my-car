package se.designcoach.findmycar.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import com.bumptech.glide.Glide
import se.designcoach.findmycar.R

/**
 * Created by lohnn-macPro on 08/04/16.
 */

class EditCarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_car)

        //Setup toolbar
        setSupportActionBar(findViewById(R.id.toolbar) as Toolbar)
        title = getString(R.string.title_activity_edit_car)
        val carImageView = findViewById(R.id.imageView) as ImageView
        Glide.with(applicationContext)
                .load(R.drawable.oldtimer)
                .centerCrop()
                .into(carImageView)
    }
}