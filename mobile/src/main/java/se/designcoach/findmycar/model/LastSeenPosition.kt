package se.designcoach.findmycar.model

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable
import java.util.*

/**
 * Created by lohnn on 2016-04-02.
 */

class LastSeenPosition(position: LatLng, time: Date = Date()) : Serializable {
    val time = time
    val position = position

    fun getTimeFormatted(): String {
        return "A nice time"
    }
}