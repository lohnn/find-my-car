package se.designcoach.findmycar.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import se.designcoach.findmycar.R
import se.designcoach.findmycar.model.CarAction
import java.util.*

/**
 * Created by lohnn on 2016-04-04.
 */

class CarActionsAdapter(carActions: ArrayList<CarAction>) : RecyclerView.Adapter<CarActionsAdapter.Companion.CarActionViewHolder>() {
    var carActions = carActions

    companion object {
        val TAG = "CarActionsAdapter"

        class CarActionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var actionName = itemView.findViewById(R.id.carAction_text) as TextView
            var actionIcon = itemView.findViewById(R.id.carAction_icon) as ImageView
            var itemClickListener: (() -> Unit)? = null

            init {
                itemView.setOnClickListener({
                    itemClickListener?.invoke()
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarActionsAdapter.Companion.CarActionViewHolder {
        val v = LayoutInflater.from(parent.context).
                inflate(R.layout.car_action_view, parent, false)
        return CarActionViewHolder(v)
    }

    override fun onBindViewHolder(holder: CarActionViewHolder, position: Int) {
        val carAction = carActions[position]
        holder.actionName.text = carAction.name
        holder.actionIcon.setImageDrawable(carAction.icon)
        holder.itemClickListener = carAction.itemClickListener
    }

    override fun getItemCount(): Int {
        return carActions.size
    }
}