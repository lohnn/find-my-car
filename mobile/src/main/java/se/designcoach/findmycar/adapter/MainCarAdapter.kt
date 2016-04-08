package se.designcoach.findmycar.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import se.designcoach.findmycar.R
import se.designcoach.findmycar.dal.DataManager

/**
 * Created by lohnn-macPro on 02/04/16.
 */

class MainCarAdapter(carClickListener: (Int) -> Unit) : RecyclerView.Adapter<MainCarAdapter.Companion.CarViewHolder>() {
    private var cars = DataManager.cars
    private var carClickListener = carClickListener

    companion object {
        val TAG = "MainCarAdapter"

        class CarViewHolder(itemView: View, carClickListener: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
            var carName = itemView.findViewById(R.id.car_name) as TextView
            var lastSeen = itemView.findViewById(R.id.text_last_seen) as TextView
            var carId: Int? = null

            init {
                itemView.setOnClickListener({
                    carClickListener.invoke(carId!!)
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val v = LayoutInflater.from(parent.context).
                inflate(R.layout.main_car_view, parent, false)
        val viewHolder = CarViewHolder(v, carClickListener)
        return viewHolder
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]
        holder.carId = position
        holder.carName.text = car.name
        holder.lastSeen.text = car.lastSeen?.getTimeFormatted() ?: ""
    }

    override fun getItemCount(): Int {
        return cars.size
    }
}