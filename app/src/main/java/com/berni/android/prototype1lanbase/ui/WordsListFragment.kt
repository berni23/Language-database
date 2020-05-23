package com.berni.android.prototype1lanbase.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
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
import kotlinx.android.synthetic.main.fragment_test2.*
import kotlinx.android.synthetic.main.fragment_words_list.*
import kotlinx.coroutines.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.util.*
import java.util.concurrent.TimeUnit

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
    private var firstView = Tutorial.firstListWordView
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


        if(firstView) {

           // resources.getString(R.string.last_added_on)
            val toast: Toast =  Toast.makeText(context, "Here you can see the added words", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0,0)
            toast.show()

            timerToast.start()

        }

        // observe if the words within the category suffer any change (like edition or deletion)

        viewModel.wordsInCat(cat.catId).observe(viewLifecycleOwner, Observer<List<Word>> {
        recycler_view_words.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

           runBlocking(Dispatchers.Default){

               displayedWords =  it.reversed()
               recycler_view_words.adapter = WordAdapter(displayedWords,viewModel,listOf(timerToast,timerToast2,timerToast3),this.coroutineContext)

           }

              displayedWords1 = displayedWords
            // setting up info for the info box in top of words list, several cases to be accounted for

            lastAdded = listOf(

                displayedWords.getOrNull(0),
                displayedWords.getOrNull(1),
                displayedWords.getOrNull(2)
            )

            lastAdditionDate = displayedWords.getOrNull(0)?.date
            var lastAdditions =  resources.getString(R.string.last_additions)
            lastAdded.forEach {
                if (it?.wordName != null) {
                    lastAdditions += " ${it.wordName},"
                }
            }
            lastAdditions = lastAdditions.dropLast(1)  // drop the last comma of the string
            if (lastAdded.elementAt(0)?.wordName == null) {
                lastAdditions = resources.getString(R.string.no_words_added_yet)
            }
            val stringLastAdditionDate = "${resources.getString(R.string.last_added_on)} $lastAdditionDate"

            // editing the corresponding info to the textviews

            text_view_numWords.text = " ${recycler_view_words.adapter?.itemCount?:0} ${resources.getString(R.string.words)}"
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
                recycler_view_words.adapter = WordAdapter(newWordsList,viewModel,listOf(timerToast,timerToast2,timerToast3),coroutineContext)
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
                stopTimers()
            }

            R.id.alphabetically -> {

                displayedWords1 = sortAlphabetically(displayedWords)
                message =  resources.getString(R.string.sorting_alphabet)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            R.id.last_added -> {

                displayedWords1 = sortLastAdded(displayedWords)
                message = resources.getString(R.string.sorting_last_added)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            R.id.first_added -> {

                displayedWords1 = sortFirstAdded(displayedWords)
                message = resources.getString(R.string.sorting_first_added)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            R.id.withExample -> {

                displayedWords1 =  filterExample(displayedWords)
                message = resources.getString(R.string.filtering_ex)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            R.id.noExample -> {

                displayedWords1 = filterNoExample(displayedWords)
                message =  resources.getString(R.string.filtering_no_ex)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            R.id.byLength -> {

                displayedWords1 = sortByLength(displayedWords)
                message =  resources.getString(R.string.sorting_length)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

            }

            R.id.withDefinition ->{

                displayedWords1 = filterDefinition(displayedWords)
                message =  resources.getString(R.string.filtering_definition)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            R.id.noDefinition ->{

                displayedWords1 = filterNoDefinition(displayedWords)
                message =  resources.getString(R.string.filtering_no_definition)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
            R.id.acquired ->{

                displayedWords1 = filterAcquired(displayedWords)
                message =  resources.getString(R.string.filtering_acquired)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            R.id.notAcquired ->{

                displayedWords1 = filterNotAcquired(displayedWords)
                message =  resources.getString(R.string.filtering_not_acquired)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

            }
        }

        recycler_view_words.adapter = WordAdapter(displayedWords1,viewModel,listOf(timerToast,timerToast2,timerToast3),coroutineContext)
        return super.onOptionsItemSelected(item)
    }

    /** also, for the sorting and filtering not to be applied but rather activated, the filters
        can  perform the same way and the sorting can start with initial data and pass the first
        X elements, where x = size of filtered list
**/

    private val timerToast = object: CountDownTimer(5000,5000) {

        override fun onFinish() {

            val toast: Toast =  Toast.makeText(context, resources.getString(R.string.msg_timerToast1), Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0,0)
            toast.show()
            timerToast2.start()

        }

        override fun onTick(millisUntilFinished: Long) {}
    }

    private val timerToast2 = object: CountDownTimer(5000,5000) {
        
        override fun onFinish() {

            val toast: Toast =  Toast.makeText(context,resources.getString(R.string.msg_timerToast2), Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0,0)
            toast.show()
            timerToast3.start()
        }
        override fun onTick(millisUntilFinished: Long) {}
    }

    private val timerToast3 = object: CountDownTimer(5000,5000) {

        override fun onFinish() {

            val toast: Toast =  Toast.makeText(context, resources.getString(R.string.msg_timerToast3), Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0,0)
            toast.show()

            firstView = false
            Tutorial.firstListWordView = false
        }
        override fun onTick(millisUntilFinished: Long) {}
    }


    private fun stopTimers() {

        timerToast.cancel()
        timerToast2.cancel()
        timerToast3.cancel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {

                navController.popBackStack()
                stopTimers()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,  // LifecycleOwner
            callback
        )
    }
}