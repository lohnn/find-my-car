package se.designcoach.findmycar.model

import java.io.Serializable

/**
 * Created by lohnn-macPro on 01/04/16.
 */
class BluetoothDevice(name: String, id: String) : Serializable {
    var name = name
    var id = id
}