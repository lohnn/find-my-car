package se.designcoach.findmycar.model

import java.io.Serializable

/**
 * Created by lohnn-macPro on 01/04/16.
 */

class Car(name: String, bluetoothDevices: Array<BluetoothDevice>) : Serializable {
    var name: String = name
    var bluetoothDevices = bluetoothDevices
    var lastSeen: LastSeenPosition? = null
    var carImage: String? = null
}