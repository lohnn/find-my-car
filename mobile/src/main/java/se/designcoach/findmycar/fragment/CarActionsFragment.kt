package se.designcoach.findmycar.fragment

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import se.designcoach.findmycar.DividerItemDecoration
import se.designcoach.findmycar.MainActivity
import se.designcoach.findmycar.R
import se.designcoach.findmycar.adapter.CarActionsAdapter
import se.designcoach.findmycar.model.Car
import se.designcoach.findmycar.model.CarAction

/**
 * Created by lohnn-macPro on 03/04/16.
 */

class CarActionsFragment : Fragment() {
    companion object {
        val ARG_CAR = "carName"
        fun newInstance(car: Car): CarActionsFragment {
            val myFragment = CarActionsFragment();

            val args = Bundle();
            args.putSerializable(ARG_CAR, car);
            myFragment.arguments = args;

            return myFragment;
        }
    }

    lateinit var car: Car
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        car = arguments.getSerializable(ARG_CAR) as Car
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_car_actions, container, false)

        //TODO: add v7 palette library for setting toolbar color
        val carImageView = view.findViewById(R.id.imageView) as ImageView
        Glide.with(this)
                .load(R.drawable.oldtimer)
                .centerCrop()
                .into(carImageView)

        (view.findViewById(R.id.carActions_collapsing_toolbar) as CollapsingToolbarLayout).title = car.name

        //Fill car actions list
        val actionPark = CarAction(getString(R.string.carActions_park), resources.getDrawable(R.drawable.ic_add_location, null),
                object : () -> Unit {
                    override fun invoke() {
                        (activity as MainActivity).carActionPark(car)
                        (activity as MainActivity).closeFragment()
                    }
                })
        val carActions = arrayListOf(actionPark)

        if (car.lastSeen != null) {
            val actionFind = CarAction(getString(R.string.carActions_find), resources.getDrawable(R.drawable.ic_directions, null),
                    object : () -> Unit {
                        override fun invoke() {
                            (activity as MainActivity).carActionFind(car)
                            (activity as MainActivity).closeFragment()
                        }
                    })
            carActions.add(actionFind)
        }

        val recyclerView = view.findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = CarActionsAdapter(carActions)
        recyclerView.addItemDecoration(DividerItemDecoration(context, null))

        return view
    }
}
