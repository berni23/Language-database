package com.berni.android.prototype1lanbase.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Word
import kotlinx.android.synthetic.main.fragment_words_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

/**
 * A simple [Fragment] subclass.
 */
class WordsListFragment : BaseFragment(), KodeinAware {

    //lateinit var navController: NavController
    private lateinit var categoryName: String
    private lateinit var lastAdded: List<Word?>
    lateinit var displayedWords: List<Word>

    private  var lastAdditionDate : String? = ""
    private  var adapter : WordAdapter? = null
    private  var numWords: Int? = null

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
        recycler_view_words.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        runBlocking(Dispatchers.Default) { displayedWords= viewModel.wordsInCat(categoryName).reversed() }
        adapter = WordAdapter(displayedWords) // sorting by last added
        recycler_view_words.adapter = adapter
        numWords = adapter!!.itemCount

        lastAdded = listOf(displayedWords.getOrNull(0), displayedWords.getOrNull(1), displayedWords.getOrNull(2))
        lastAdditionDate = displayedWords.getOrNull(0)?.date
        var lastAdditions = "Last additions: "
        lastAdded.forEach { if (it?.wordName != null) { lastAdditions += " ${it.wordName},"  } }
        lastAdditions = lastAdditions.dropLast(1)

        //   Toast.makeText(view.context, " word = ${it?.wordName}", Toast.LENGTH_SHORT).show() }

        if (lastAdded.elementAt(0)?.wordName == null) { lastAdditions = "No words added yet"  }

        val stringLastAdditionDate = "Last addition on $lastAdditionDate"
        text_view_numWords.text = " ${numWords.toString()} words"
        lastAdditionDate?.let{text_view_lastDate.text =stringLastAdditionDate}
        text_view_last_additions.text = lastAdditions

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_words, menu)

        val searchView: SearchView = menu.findItem(R.id.item_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

              //  val wordsList = adapter?.words

                var newWordsList = mutableListOf<Word>()

                    displayedWords.forEach {

                        if (it.wordName.startsWith(newText!!)) {

                            newWordsList.add(it)

                        }
                    }

                    adapter = WordAdapter(newWordsList)

                recycler_view_words.adapter = adapter
                return false
            }

        })

        return super.onCreateOptionsMenu(menu, inflater)
     }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        var message :String? =  null
        when (item.itemId) {

            R.id.alphabetically -> {
                runBlocking(Dispatchers.Default){

                    displayedWords = viewModel.wordsInCatAlphabetic(categoryName)
                }
                message = "sorting by alphabetic order.."
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()}

            R.id.last_added ->{
                runBlocking(Dispatchers.Default){displayedWords = viewModel.wordsInCat(categoryName).reversed()}

                message = "sorting by last added.."
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()}

            R.id.first_added ->{
                runBlocking(Dispatchers.Default){
                    displayedWords= viewModel.wordsInCat(categoryName)}

                message =  "sorting by first added.."
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            R.id.withExample -> {
                runBlocking(Dispatchers.Default) {
                    displayedWords = viewModel.filterExample(categoryName)
                }
                message = "filtering words with an example.."
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            R.id.noExample -> {
                runBlocking(Dispatchers.Default) {
                    displayedWords = viewModel.filterNoExample(categoryName)
                }
                message = "filtering words without example.."
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }

        adapter = WordAdapter(displayedWords)
        recycler_view_words.adapter = adapter
        return super.onOptionsItemSelected(item)
    }

}
