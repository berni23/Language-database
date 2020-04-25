package com.berni.android.prototype1lanbase.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Cat
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.coroutines.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : BaseFragment(),KodeinAware {

    //lateinit var navController: NavController

    private var newCatName: String? = null
    override val kodein by closestKodein()

    private val viewModelFactory: ViewModelFactory by instance<ViewModelFactory>()
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(

    inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        return inflater.inflate(R.layout.fragment_first, container, false)  }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        //title = " Language Database"

        (activity as AppCompatActivity).supportActionBar?.title = "Language Database"

        recycler_view_cats.setHasFixedSize(true)

        //recycler_view_cats.layoutManager =  StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        // navController = Navigation.findNavController(view)

        viewModel.allCats.observe(viewLifecycleOwner, Observer<List<Cat>> {

            recycler_view_cats.layoutManager =  StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

           //awaitAll()

           // viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

            recycler_view_cats.adapter = CatAdapter(it,viewModel,this.coroutineContext)

        })

        btn_add.setOnClickListener {

            editText_newCat.text.clear()
            newCatName = null
            recycler_view_newCat.visibility = View.VISIBLE
             }

         btnCancel.setOnClickListener { recycler_view_newCat.visibility = View.GONE }

        btnCreate.setOnClickListener {

            //TODO( window disappears on screen rotated. probably fixed with creation of viewmodel or using a binding method)
             recycler_view_newCat.visibility = View.GONE

            newCatName = editText_newCat.text.toString().trim()
            val currentDate: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

            if (newCatName!!.isEmpty()) {

                editText_newCat.error = "category required"
                editText_newCat.requestFocus()
                return@setOnClickListener
            }

            launch {

                val cat = Cat(newCatName!!, currentDate)
                viewModel.addCat(cat)

            }

            recycler_view_newCat.visibility = View.GONE
            Toast.makeText(context, "category $newCatName successfully created", Toast.LENGTH_SHORT).show()
            editText_newCat.text.clear()

        }
    }


}
