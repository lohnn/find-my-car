package se.designcoach.findmycar.dal

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import se.designcoach.findmycar.model.Car
import java.util.*

/**
 * Created by lohnn-macPro on 05/04/16.
 */

class DataManager constructor(context: Context) {
    val context = context

    companion object {
        val TAG = "DataManager"
        val KEY_CARS = "Cars"
        var cars = ArrayList<Car>()
    }

    fun saveCars(cars: ArrayList<Car>) {
        val sharedPref = context.getSharedPreferences(KEY_CARS, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val carsJsonString = Gson().toJson(cars).toString()
        editor.putString(KEY_CARS, carsJsonString)
        editor.commit()
        Log.d(TAG, carsJsonString)
    }

    fun loadCars(): ArrayList<Car> {
        val sharedPref = context.getSharedPreferences(KEY_CARS, Context.MODE_PRIVATE)
        val jsonString = sharedPref.getString(KEY_CARS, "")
        Log.d(TAG, "Opening: $jsonString")
        val type = object: TypeToken<List<Car>>(){}.type
        cars = Gson().fromJson(jsonString, type) ?: ArrayList()
        return cars
    }
}