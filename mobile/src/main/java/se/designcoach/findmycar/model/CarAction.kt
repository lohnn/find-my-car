package se.designcoach.findmycar.model

import android.graphics.drawable.Drawable

/**
 * Created by lohnn on 2016-04-04.
 */
class CarAction(name: String, icon: Drawable, itemClickListener: () -> Unit) {
    val name = name
    val icon = icon
    val itemClickListener = itemClickListener
}