package com.berni.android.prototype1lanbase.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Word
import kotlinx.android.synthetic.main.fragment_words_list.*
import kotlinx.coroutines.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

/**
 * A simple [Fragment] subclass.
 */
class WordsListFragment : BaseFragment(), KodeinAware {

    //lateinit var navController: NavController
    lateinit var categoryName: String

    override val kodein by closestKodein()

    private val viewModelFactory: ViewModelFactory by instance<ViewModelFactory>()
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        categoryName = arguments?.getString("categoryName").toString()

        return inflater.inflate(R.layout.fragment_words_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //recycler_view_words.setHasFixedSize(true)

        recycler_view_words.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        // navController = Navigation.findNavController(view)xt

        catName2_textView.text = categoryName

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        launch(Dispatchers.Default){
            recycler_view_words.adapter = WordAdapter( viewModel.wordsInCat(categoryName)) }

        }

        override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

               super.onCreateOptionsMenu(menu, inflater)
               inflater.inflate(R.menu.menu_words, menu)

    }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {

            var SortedAlpha: List<Word>? = null

        when (item.itemId) {

            R.id.alphabetically -> {

                runBlocking(Dispatchers.Default){SortedAlpha = viewModel.wordsInCatAlphabetic(categoryName)}

                    recycler_view_words.adapter = WordAdapter(SortedAlpha!!)
                    Toast.makeText(context, "sorting by alphabetic order..", Toast.LENGTH_SHORT).show()}

            R.id.last_added ->  {Toast.makeText(context, "sorting by last added..", Toast.LENGTH_SHORT).show() }
        }

        return super.onOptionsItemSelected(item)
    }

}




