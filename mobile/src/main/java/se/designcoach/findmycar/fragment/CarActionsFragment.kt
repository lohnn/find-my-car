package se.designcoach.findmycar.fragment

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import se.designcoach.findmycar.R
import se.designcoach.findmycar.model.Car

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
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_car_actions, container, false)
        //        (view.findViewById(R.id.car_name) as TextView).text = carName
        (view.findViewById(R.id.carActions_collapsing_toolbar) as CollapsingToolbarLayout).title = carName
        return view
    }
}
