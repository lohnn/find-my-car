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
    }

    fun saveCars(cars: Array<Car>) {
        //        val sharedPref = context.getSharedPreferences("", Context.MODE_PRIVATE);
        //        val editor = sharedPref.edit();
        //        editor.putInt("", cars);
        //        editor.commit();
        val json = Gson().toJson(cars)
        Log.d(TAG, json.toString())
    }

    fun loadCars(): Array<Car> {
        return emptyArray()
    }
}