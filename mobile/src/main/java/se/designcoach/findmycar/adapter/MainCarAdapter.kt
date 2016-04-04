package se.designcoach.findmycar.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import se.designcoach.findmycar.R
import se.designcoach.findmycar.model.Car

/**
 * Created by lohnn-macPro on 02/04/16.
 */

class MainCarAdapter(cars: Array<Car>, carClickListener: (Car)->Unit) : RecyclerView.Adapter<MainCarAdapter.Companion.CarViewHolder>() {
    private var cars = cars
    private var carClickListener = carClickListener

    companion object {
        val TAG = "MainCarAdapter"

        class CarViewHolder(itemView: View?, carClickListener: (Car)->Unit) : RecyclerView.ViewHolder(itemView) {
            var carName = itemView!!.findViewById(R.id.car_name) as TextView
            var lastSeen = itemView!!.findViewById(R.id.text_last_seen) as TextView
            var car: Car? = null

            init {
                itemView!!.setOnClickListener({
                    carClickListener.invoke(car!!)
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CarViewHolder? {
        val v = LayoutInflater.from(parent!!.context).
                inflate(R.layout.main_car_view, parent, false)
        val viewHolder = CarViewHolder(v, carClickListener)
        return viewHolder
    }

    override fun onBindViewHolder(holder: CarViewHolder?, position: Int) {
        holder!!.car = cars[position]
        holder.carName.text = cars[position].name
        holder.lastSeen.text = cars[position].lastSeen?.getTimeFormatted() ?: ""
    }

    override fun getItemCount(): Int {
        return cars.size
    }
}