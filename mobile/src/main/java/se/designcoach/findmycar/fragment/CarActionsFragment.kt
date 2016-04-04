package se.designcoach.findmycar.fragment

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import se.designcoach.findmycar.DividerItemDecoration
import se.designcoach.findmycar.R
import se.designcoach.findmycar.adapter.CarActionsAdapter
import se.designcoach.findmycar.model.Car
import se.designcoach.findmycar.model.CarAction

/**
 * Created by lohnn-macPro on 03/04/16.
 */

class CarActionsFragment : Fragment() {
    companion object {
        val ARG_CAR_NAME = "carName"
        fun newInstance(car: Car): CarActionsFragment {
            val myFragment = CarActionsFragment();

            val args = Bundle();
            args.putString(ARG_CAR_NAME, car.name);
            myFragment.arguments = args;

            return myFragment;
        }
    }

    var carName: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        carName = arguments.getString(ARG_CAR_NAME)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_car_actions, container, false)
        (view.findViewById(R.id.carActions_collapsing_toolbar) as CollapsingToolbarLayout).title = carName

        //Fill car actions list
        val actionPark = CarAction("Park", resources.getDrawable(R.drawable.ic_add_location, null),
                object : () -> Unit {
                    override fun invoke() {
                    }
                })

        val actionFind = CarAction("Find my car", resources.getDrawable(R.drawable.ic_directions, null),
                object : () -> Unit {
                    override fun invoke() {
                    }
                })
        val carActions = arrayOf(actionPark, actionFind)

        val recyclerView = view.findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = CarActionsAdapter(carActions)
        recyclerView.addItemDecoration(DividerItemDecoration(context, null))

        return view
    }
}
