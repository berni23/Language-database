package com.berni.android.prototype1lanbase.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.View.inflate
import android.widget.Button
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isInvisible
import androidx.core.widget.PopupWindowCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Cat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : BaseFragment(),KodeinAware {

    lateinit var navController: NavController
    var newCatName: String? = null

    override val kodein by closestKodein()

    private val viewModelFactory: ViewModelFactory by instance()
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(

    inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? { return inflater.inflate(R.layout.fragment_first, container, false)  }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view_cats.setHasFixedSize(true)
        recycler_view_cats.layoutManager =  StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        navController = Navigation.findNavController(view)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.allCats.observe(viewLifecycleOwner, Observer<List<Cat>> { recycler_view_cats.adapter = CatAdapter(it) })


        btn_add.setOnClickListener() {

            editText_newCat.text.clear()
            newCatName = null
            recycler_view_newCat.setVisibility(View.VISIBLE) }

        btnCancel.setOnClickListener() { recycler_view_newCat.setVisibility(View.GONE) }

        btnCreate.setOnClickListener() {

            //TODO( window disappears on screen rotated. probably fixed with creation of viewmodel or using a binding method)
            recycler_view_newCat.setVisibility(View.VISIBLE)

            newCatName = editText_newCat.text.toString().trim()
            val currentDate: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

            if (newCatName!!.isEmpty()) {

                editText_newCat.error = "category required"
                editText_newCat.requestFocus()
                return@setOnClickListener
            }

            Toast.makeText(context, "category successfully created", Toast.LENGTH_SHORT).show()

           // viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

            launch {

                val cat = Cat(newCatName!!, currentDate)
                viewModel.addCat(cat)

            }
            recycler_view_newCat.setVisibility(View.GONE)
            editText_newCat.text.clear()

        }


        /*   btn_add.setOnClickListener() {

            val window = PopupWindow(getActivity()!!.getApplicationContext())
            val windowView = layoutInflater.inflate(R.layout.new_cat,null)

            window.contentView = windowView
            // LayoutInflater.from(context).inflate(R.layout.adapter_cat, parent, false)

            window.showAtLocation(view, Gravity.CENTER, 0, 0)


    } }*/

    }}




    //override fun onClick(v: View?) {

        // Can be done easier , but this is more general if we have more actions to link with floating buttons.

      //  when(v!!.id) {


 //              btn add-> navController.navigate(R.id.actionAddCat)

  //          }

 //       }



//org.kodein.di.Kodein$NotFoundException: No binding found for bind<Cat>() with ?<FirstFragment>().? { ? }


