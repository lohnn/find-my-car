package se.designcoach.findmycar

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import se.designcoach.findmycar.adapter.MainCarAdapter
import se.designcoach.findmycar.model.BluetoothDevice
import se.designcoach.findmycar.model.Car

/**
 * Created by lohnn-macPro on 31/03/16.
 */

class MainActivity : FragmentActivity(), OnMapReadyCallback {
    companion object {
        val ARG_CAR_POSITION = "CarPosition"
        val MY_PERMISSIONS_REQUEST_FINE_LOCATION = 101
    }

    private var mMap: GoogleMap? = null
    private var carPosition: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = intent.extras
        carPosition = args?.getParcelable<LatLng?>(ARG_CAR_POSITION)
        if (carPosition == null) carPosition = LatLng(56.684238, 16.320195)
        setContentView(R.layout.activity_main)
        //         Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val car = Car("Golfen", emptyArray<BluetoothDevice>())
        val cars = Array<Car>(1, { car })
        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MainCarAdapter(cars)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_FINE_LOCATION -> {
                mMap!!.isMyLocationEnabled = true
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap

        val fineLoactionPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
        if (fineLoactionPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    Array(1, { Manifest.permission.ACCESS_FINE_LOCATION }),
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        } else {
            mMap!!.isMyLocationEnabled = true
        }
        // Add a marker at car position and move the camera
        mMap!!.mapType = GoogleMap.MAP_TYPE_HYBRID
        mMap!!.addMarker(MarkerOptions().position(carPosition).title(getString(R.string.heres_your_car)))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(carPosition, 17.5f))
    }
}