package se.designcoach.findmycar

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBarActivity
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import se.designcoach.findmycar.adapter.MainCarAdapter
import se.designcoach.findmycar.fragment.CarActionsFragment
import se.designcoach.findmycar.model.BluetoothDevice
import se.designcoach.findmycar.model.Car
import se.designcoach.findmycar.model.LastSeenPosition
import java.util.*

/**
 * Created by lohnn-macPro on 31/03/16.
 */

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object {
        val TAG = "MainActivity"
        val ARG_CAR_POSITION = "CarPosition"
        val MY_PERMISSIONS_REQUEST_FINE_LOCATION = 101
    }

    private var mMap: GoogleMap? = null
    private var carActionsFragment: CarActionsFragment? = null
    private var slidingUpPanel: SlidingUpPanelLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        //Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Add some static cars to the list
        val carPosition = LatLng(56.684238, 16.320195)
        val golfen = Car("Golfen", emptyArray<BluetoothDevice>())
        val uppen = Car("Uppen", emptyArray())
        uppen.lastSeen = LastSeenPosition(Date(), carPosition!!)
        val cars = arrayOf(golfen, uppen)

        //Toolbar
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        //SlidingUpPanel (car list)
        slidingUpPanel = findViewById(R.id.sliding_layout) as SlidingUpPanelLayout?

        val mainContent = findViewById(R.id.main_content)
        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MainCarAdapter(cars, object : MainCarAdapter.CarClickListener {
            override fun carClicked(car: Car) {
                Log.d(TAG, "Clicked ${car.name}")

                carActionsFragment = CarActionsFragment.newInstance(car)
                val ft = supportFragmentManager.beginTransaction()
                ft.add(mainContent!!.id, carActionsFragment).addToBackStack(null).commit()

                //                if(car.lastSeen != null){
                //                    mMap!!.addMarker(MarkerOptions().position(car.lastSeen!!.position).title(getString(R.string.heres_your_car)))
                //                    mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(car.lastSeen!!.position, 17.5f))
                //                }
            }
        })
        recyclerView.addItemDecoration(DividerItemDecoration(this, null))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_settings -> {
                Log.d(TAG, "Want to open settings")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_FINE_LOCATION -> {
                mMap!!.isMyLocationEnabled = true
            }
        }
    }

    override fun onBackPressed() {
        if (slidingUpPanel?.panelState == SlidingUpPanelLayout.PanelState.EXPANDED) {
            slidingUpPanel!!.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            return
        }
        super.onBackPressed()
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
        // Move camera to your position
        mMap!!.mapType = GoogleMap.MAP_TYPE_HYBRID
        val locationManager = (getSystemService(Context.LOCATION_SERVICE) as LocationManager).
                getLastKnownLocation(LocationManager.GPS_PROVIDER)
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(
                LatLng(locationManager.latitude, locationManager.longitude), 17.5f))
    }
}