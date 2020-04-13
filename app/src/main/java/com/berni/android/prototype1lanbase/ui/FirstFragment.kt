package com.berni.android.prototype1lanbase.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.berni.android.prototype1lanbase.R
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : BaseFragment(),KodeinAware {

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

        view.findViewById<Button>(R.id.btn_add).setOnClickListener {
            findNavController().navigate(R.id.actionAddCat)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        recycler_view_cats.setHasFixedSize(true)
        recycler_view_cats.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        launch {

            val Cats = viewModel.AllCats
            //        recycler_view_notes.adapter = NotesAdapter(notes)

        }

    }
}







 //   private fun bindUI()  = GlobalScope.launch{
  //      val currentWeather  = viewModel.weather.await() // we want to await because it is deferred

  //      currentWeather.observe(this@CurrentWeatherFragment,Observer {

   //         if(it==null) return@Observer
   //         textView.text = it.toString()

   //     })
   // }
//}


