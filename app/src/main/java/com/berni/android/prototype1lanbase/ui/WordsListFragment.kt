package com.berni.android.prototype1lanbase.ui

import android.annotation.SuppressLint
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
    lateinit var lastAdded: List<Word?>
    var lastAdditionDate : String? = ""


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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view_words.setHasFixedSize(true)

        recycler_view_words.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        // navController = Navigation.findNavController(view)xt

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        runBlocking(Dispatchers.Default) {

            val wordsInCat: List<Word> = viewModel.wordsInCat(categoryName)
            adapter = WordAdapter(wordsInCat.reversed()) // sorting by last added
            recycler_view_words.adapter = adapter
            numWords = adapter!!.itemCount

            lastAdded =
                listOf(wordsInCat.getOrNull(0), wordsInCat.getOrNull(1), wordsInCat.getOrNull(2))
            lastAdditionDate = wordsInCat.getOrNull(0)?.date
        }

        var lastAdditions = "Last additions: "

        lastAdded.forEach {

            if (it?.wordName != null) {

                lastAdditions += "${it.wordName},"

            }
        }

        lastAdditions = lastAdditions.dropLast(1)

        //   Toast.makeText(view.context, " word = ${it?.wordName}", Toast.LENGTH_SHORT).show() }

        if (lastAdded.elementAt(0)?.wordName == null) {
            lastAdditions = "No words added yet"
        }

        val stringLastAdditionDate = "Last addition on $lastAdditionDate"
        text_view_numWords.text = " ${numWords.toString()} words"
        lastAdditionDate?.let{text_view_lastDate.text =stringLastAdditionDate}
        text_view_last_additions.text = lastAdditions

    }

              //"Last additions : ${lastAdded?.getOrNull(0)},${lastAdded?.getOrElse(1){""}},${lastAdded?.getOrNull(0)}"


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

               runBlocking(Dispatchers.Default){sortedLastAdded = viewModel.wordsInCat(categoryName).reversed()}
                    recycler_view_words.adapter = WordAdapter(sortedLastAdded!!)

                Toast.makeText(context, "sorting by last added..", Toast.LENGTH_SHORT).show() }


            R.id.first_added ->{


                runBlocking(Dispatchers.Default){sortedLastAdded = viewModel.wordsInCat(categoryName)}
                recycler_view_words.adapter = WordAdapter(sortedLastAdded!!)

                Toast.makeText(context, "sorting by first added..", Toast.LENGTH_SHORT).show() }

            }


        return super.onOptionsItemSelected(item)
    }

}




