package se.designcoach.findmycar.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import se.designcoach.findmycar.R
import se.designcoach.findmycar.model.Car

/**
 * Created by lohnn-macPro on 02/04/16.
 */

class MainCarAdapter(cars: Array<Car>) : RecyclerView.Adapter<MainCarAdapter.Companion.CarViewHolder>() {
    private var cars = cars

    companion object {
        class CarViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
            var carName = itemView!!.findViewById(R.id.car_name) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CarViewHolder? {
        val v = LayoutInflater.from(parent!!.context).
                inflate(R.layout.main_car_view, parent, false)
        val viewHolder = CarViewHolder(v)
        return viewHolder
    }

    override fun onBindViewHolder(holder: CarViewHolder?, position: Int) {
        holder!!.carName.text = cars[position].name
    }

    override fun getItemCount(): Int {
        return cars.size
    }
}