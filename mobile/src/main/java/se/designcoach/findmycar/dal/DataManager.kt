package se.designcoach.findmycar.dal

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import se.designcoach.findmycar.model.Car

/**
 * Created by lohnn-macPro on 05/04/16.
 */

class DataManager constructor(context: Context) {
    val context = context

    companion object {
        val TAG = "DataManager"
        val KEY_CARS = "Cars"
    }

    fun saveCars(cars: Array<Car>) {
        val sharedPref = context.getSharedPreferences(KEY_CARS, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val carsJsonString = Gson().toJson(cars).toString()
        editor.putString(KEY_CARS, carsJsonString)
        editor.commit()
        Log.d(TAG, carsJsonString)
    }

    fun loadCars(): Array<Car> {
        val sharedPref = context.getSharedPreferences(KEY_CARS, Context.MODE_PRIVATE)
        val jsonString = sharedPref.getString(KEY_CARS, "")
        return Gson().fromJson(jsonString, Array<Car>::class.java) ?: emptyArray()
    }
}