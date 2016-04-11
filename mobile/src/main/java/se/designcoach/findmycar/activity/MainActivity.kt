package se.designcoach.findmycar.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import se.designcoach.findmycar.DividerItemDecoration
import se.designcoach.findmycar.R
import se.designcoach.findmycar.adapter.MainCarAdapter
import se.designcoach.findmycar.dal.DataManager
import se.designcoach.findmycar.fragment.CarActionsFragment
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

    lateinit private var mainContent: View
    lateinit private var map: GoogleMap
    lateinit private var slidingUpPanel: SlidingUpPanelLayout
    lateinit private var fab: FloatingActionButton
    private var marker: Marker? = null
    private var carActionsFragment: CarActionsFragment? = null
    lateinit var dataManager: DataManager

    ////////
    //
    lateinit var cars: ArrayList<Car>
    //
    ////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataManager = DataManager(applicationContext)

        setContentView(R.layout.activity_main)
        //Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Add some static cars to the list
        cars = dataManager.loadCars()
        if (cars.isEmpty()) {
            Log.d(TAG, "No cars saved, creating one")
            val uppen = Car("Uppen", emptyArray())
            uppen.lastSeen = LastSeenPosition(LatLng(56.684238, 16.320195))
            cars.add(uppen)
        }
        //Toolbar
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        //SlidingUpPanel (car list)
        fab = findViewById(R.id.fab_create_car) as FloatingActionButton
        fab.setOnClickListener {
            //TODO: Add functionality for creating a car
            val editCarIntent = Intent(applicationContext, EditCarActivity::class.java)
            startActivity(editCarIntent)
            Log.d(TAG, "Create a new car now!")
        }

        slidingUpPanel = findViewById(R.id.sliding_layout) as SlidingUpPanelLayout
        slidingUpPanel.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {
            }

            override fun onPanelStateChanged(panel: View?, previousState: SlidingUpPanelLayout.PanelState?, newState: SlidingUpPanelLayout.PanelState?) {
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    fab.show()
                } else {
                    fab.hide()
                }
            }
        })

        mainContent = findViewById(R.id.main_content)!!
        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, null))
        recyclerView.adapter = MainCarAdapter(object : (Int) -> Unit {
            override fun invoke(carId: Int) {
                Log.d(TAG, "Clicked ${DataManager.cars[carId]}")

                if (carActionsFragment == null) {
                    carActionsFragment = CarActionsFragment.newInstance(carId)
                    val ft = supportFragmentManager.beginTransaction()
                    ft.add(mainContent.id, carActionsFragment).addToBackStack("fragment").commit()
                    fab.hide()
                }
            }
        })
    }

    fun carActionFind(car: Car) {
        if (car.lastSeen != null) {
            placeMarker(car.lastSeen!!.position, car.name)
        }
    }

    fun carActionPark(car: Car) {
        val currentPosition = getCurrentPosition()
        if (currentPosition != null) {
            car.lastSeen = LastSeenPosition(currentPosition)
            dataManager.saveCars(cars)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_settings -> {
                Log.d(TAG, "Want to open settings")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun placeMarker(position: LatLng, title: String) {
        if (marker == null) {
            marker = map.addMarker(MarkerOptions().position(position).title(title))
        }
        marker!!.position = position
        marker!!.title = title
        marker!!.showInfoWindow()
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 17.5f))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_FINE_LOCATION -> {
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    //TODO: Implement something here to tell user they need it
                    Log.d(TAG, "Too bad, you really need location to use this app!")
                } else {
                    map.isMyLocationEnabled = true
                }
            }
        }
    }

    override fun onBackPressed() {
        if (carActionsFragment != null) {
            closeFragment()
            return
        }
        if (slidingUpPanel.panelState == SlidingUpPanelLayout.PanelState.EXPANDED) {
            slidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            return
        }
        super.onBackPressed()
    }

    private fun getCurrentPosition(): LatLng? {
        val locationManager = (getSystemService(Context.LOCATION_SERVICE) as LocationManager).
                getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (locationManager != null)
            return LatLng(locationManager.latitude, locationManager.longitude)
        return null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val fineLoactionPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
        if (fineLoactionPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    Array(1, { Manifest.permission.ACCESS_FINE_LOCATION }),
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        } else {
            map.isMyLocationEnabled = true
        }
        // Move camera to your position
        map.mapType = GoogleMap.MAP_TYPE_HYBRID
        val currentPosition = getCurrentPosition()
        if (currentPosition != null)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 17.5f))
    }

    fun closeFragment() {
        val manager = supportFragmentManager;
        val trans = manager.beginTransaction();
        trans.remove(carActionsFragment);
        trans.commit();
        manager.popBackStack();
        carActionsFragment = null
        if (slidingUpPanel.panelState == SlidingUpPanelLayout.PanelState.EXPANDED)
            fab.show()
    }
}