package com.berni.android.prototype1lanbase.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.berni.android.prototype1lanbase.R

/**
 * A simple [Fragment] subclass.
 */
class InfoFragment : Fragment() {

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        //(activity as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.edit_info)
        navController = Navigation.findNavController(view)

    }
        // mandatory fields

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_words, menu)
        menu.findItem(R.id.item_backEditToWordsList).setIcon(R.drawable.ic_arrow_back_black_24dp)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {R.id.item_backEditToWordsList -> {navController.popBackStack() } }

        return super.onOptionsItemSelected(item)
    }

}
