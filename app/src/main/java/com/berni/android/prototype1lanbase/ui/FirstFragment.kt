package com.berni.android.prototype1lanbase.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Cat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_first.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : BaseFragment(),KodeinAware,View.OnClickListener{

    lateinit var navController: NavController

    override val kodein by closestKodein()

    private val viewModelFactory: ViewModelFactory by instance()
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view_cats.setHasFixedSize(true)
         recycler_view_cats.layoutManager =
           StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        navController = Navigation.findNavController(view)

        view.findViewById<FloatingActionButton>(R.id.btn_add).setOnClickListener(this)


        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.allCats.observe(viewLifecycleOwner  , Observer<List<Cat>> {

            recycler_view_cats.adapter = CatAdapter(it)

        })

    }


    override fun onClick(v: View?) {

        // Can be done easier , but this is more general if we have more actions to link with floating buttons.

        when(v!!.id) {

            R.id.btn_add -> {

                navController.navigate(R.id.actionAddCat)

            }

        }
    }

}


//org.kodein.di.Kodein$NotFoundException: No binding found for bind<Cat>() with ?<FirstFragment>().? { ? }


