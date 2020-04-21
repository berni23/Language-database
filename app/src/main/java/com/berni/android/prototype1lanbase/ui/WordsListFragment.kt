package com.berni.android.prototype1lanbase.ui

import android.os.Bundle
import android.view.*
import android.widget.Adapter
import android.widget.Toast
import androidx.core.content.ContextCompat
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
import kotlinx.android.synthetic.*

/**
 * A simple [Fragment] subclass.
 */
class WordsListFragment : BaseFragment(), KodeinAware {

    //lateinit var navController: NavController
    lateinit var categoryName: String

    var adapter : WordAdapter? = null

    var numWords: Int? = null

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

        recycler_view_words.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        // navController = Navigation.findNavController(view)xt

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        runBlocking(Dispatchers.Default){
            adapter = WordAdapter( viewModel.wordsInCat(categoryName))
            recycler_view_words.adapter =adapter
            numWords = adapter!!.itemCount

        }

        numWords_textView.text = numWords.toString()
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {

        super.onCreateContextMenu(menu, v, menuInfo)

        MenuInflater(v.context).inflate(R.menu.menu_cat, menu)

    }


        override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

               super.onCreateOptionsMenu(menu, inflater)
               inflater.inflate(R.menu.menu_words, menu)

            // menu.setIcon(ContextCompat.getDrawable(context!!, R.drawable.ic_add_black_24dp));

    }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {

            var sortedAlpha: List<Word>? = null
            var sortedLastAdded: List<Word>? = null

        when (item.itemId) {


            R.id.alphabetically -> {

                runBlocking(Dispatchers.Default){sortedAlpha = viewModel.wordsInCatAlphabetic(categoryName)}

                    recycler_view_words.adapter = WordAdapter(sortedAlpha!!)

                    Toast.makeText(context, "sorting by alphabetic order..", Toast.LENGTH_SHORT).show()}



            R.id.last_added ->  {

               runBlocking(Dispatchers.Default){sortedLastAdded = viewModel.wordsInCat(categoryName)}
                    recycler_view_words.adapter = WordAdapter(sortedLastAdded!!)

                Toast.makeText(context, "sorting by last added..", Toast.LENGTH_SHORT).show() }
        }

        return super.onOptionsItemSelected(item)
    }

}




