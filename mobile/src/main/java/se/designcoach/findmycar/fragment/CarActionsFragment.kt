package se.designcoach.findmycar.fragment

import android.os.Bundle
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
        fun newInstance(car: Car): CarActionsFragment {
            val myFragment = CarActionsFragment();

            val args = Bundle();
            //        args.putInt("someInt", someInt);
            myFragment.arguments = args;

            return myFragment;
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_car_actions, container, false)
    }
}
