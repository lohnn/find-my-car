package se.designcoach.findmycar

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Created by lohnn-macPro on 31/03/16.
 */

class MainActivity : FragmentActivity(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(56.684238, 16.320195)
        mMap!!.mapType = GoogleMap.MAP_TYPE_HYBRID
        mMap!!.addMarker(MarkerOptions().position(sydney).title(""))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 18f))
    }
}