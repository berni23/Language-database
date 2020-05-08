package com.berni.android.prototype1lanbase.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.berni.android.prototype1lanbase.*
import com.berni.android.prototype1lanbase.db.Cat
import com.berni.android.prototype1lanbase.db.Word
import kotlinx.android.synthetic.main.fragment_words_list.*
import kotlinx.coroutines.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class WordsListFragment : BaseFragment(), KodeinAware {

    //lateinit var navController: NavController

    private lateinit var cat: Cat
    private lateinit var lastAdded: List<Word?>
    private var displayedWords =  listOf<Word>()
    private var displayedWords1 =  listOf<Word>()
    private var lastAdditionDate: String? = ""
    private lateinit var navController: NavController


    override val kodein by closestKodein()
    private val viewModelFactory: ViewModelFactory by instance<ViewModelFactory>()
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        cat = arguments?.get("cat") as Cat
        return inflater.inflate(R.layout.fragment_words_list, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = cat.catName
        navController = Navigation.findNavController(view)
        recycler_view_words.setHasFixedSize(true)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        // observe if the words within the category suffer any change (like edition or deletion)

        viewModel.wordsInCat(cat.catId).observe(viewLifecycleOwner, Observer<List<Word>> {

        recycler_view_words.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

           runBlocking(Dispatchers.Default){

               displayedWords =  it.reversed()

               recycler_view_words.adapter = WordAdapter(displayedWords,viewModel,this.coroutineContext)

           }

              displayedWords1 = displayedWords

            // setting up info for the info box in top of words list, several cases to be accounted for

            lastAdded = listOf(

                displayedWords.getOrNull(0),
                displayedWords.getOrNull(1),
                displayedWords.getOrNull(2)
            )

            lastAdditionDate = displayedWords.getOrNull(0)?.date
            var lastAdditions = "Last additions: "
            lastAdded.forEach {
                if (it?.wordName != null) {
                    lastAdditions += " ${it.wordName},"
                }
            }
            lastAdditions = lastAdditions.dropLast(1)  // drop the last comma of the string
            if (lastAdded.elementAt(0)?.wordName == null) {
                lastAdditions = "No words added yet"
            }
            val stringLastAdditionDate = "Last addition on $lastAdditionDate"

            // editing the corresponding info to the textviews

            text_view_numWords.text = " ${recycler_view_words.adapter?.itemCount?:0} words"
            lastAdditionDate?.let { text_view_lastDate.text = stringLastAdditionDate }
            text_view_last_additions.text = lastAdditions

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_words, menu)

        val searchView: SearchView = menu.findItem(R.id.item_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                val newWordsList = mutableListOf<Word>()

                displayedWords1.forEach {

                    if (it.wordName.startsWith(newText!!)) {

                        newWordsList.add(it)

                    }
                }

                displayedWords1 = newWordsList
                recycler_view_words.adapter = WordAdapter(displayedWords1,viewModel,coroutineContext)
                return false
            }

        })

        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val message: String?
        when (item.itemId) {

            R.id.item_backToSecond -> {
                navController.popBackStack()

            }

            R.id.alphabetically -> {

                displayedWords1 = sortAlphabetically(displayedWords)
                message = "sorting by alphabetic order.."
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            R.id.last_added -> {

                displayedWords1 = sortLastAdded(displayedWords)
                message = "sorting by last added.."
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            R.id.first_added -> {

                displayedWords1 = sortFirstAdded(displayedWords)
                message = "sorting by first added.."
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            R.id.withExample -> {

                displayedWords1 =  filterExample(displayedWords)
                message = "filtering words with an example.."
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            R.id.noExample -> {

                displayedWords1 = filterNoExample(displayedWords)
                message = "filtering words without example.."
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            R.id.byLength -> {

                displayedWords1 = sortByLength(displayedWords)
                message = "sorting words by their length.."
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

            }

            R.id.withDefinition ->{

                displayedWords1 = filterDefinition(displayedWords)
                message = "filtering words with a definition.."
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            R.id.noDefinition ->{

                displayedWords1 = filterNoDefinition(displayedWords)
                message = "filtering words without definition.."
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
            R.id.acquired ->{

                displayedWords1 = filterAcquired(displayedWords)
                message = "filtering words already acquired.."
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            R.id.notAcquired ->{

                displayedWords1 = filterNotAcquired(displayedWords)
                message = "filtering words not acquired.."
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

            }
        }

        recycler_view_words.adapter = WordAdapter(displayedWords1,viewModel,this.coroutineContext)
        return super.onOptionsItemSelected(item)
    }


    /** also, for the sorting and filtering not to be applied but rather activated, the filters
        can  perform the same way and the sorting can start with initial data and pass the first
        X elements, where x = size of filtered list
**/
}


