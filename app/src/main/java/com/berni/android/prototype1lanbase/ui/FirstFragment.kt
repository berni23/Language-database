package com.berni.android.prototype1lanbase.ui

import android.icu.util.Calendar
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Cat
import com.berni.android.prototype1lanbase.db.Test
import com.berni.android.prototype1lanbase.db.Word
import com.berni.android.prototype1lanbase.hideKeyboard
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

    private var newCatName: String? = null
    override val kodein by closestKodein()

    private val viewModelFactory: ViewModelFactory by instance<ViewModelFactory>()
    private lateinit var viewModel: MainViewModel
    private lateinit var navController: NavController

    private var _allWords = listOf<Word>()
    private var _allCats = listOf<Cat>()
    private var displayedCats = listOf<Cat>()

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Language Database"

        recycler_view_cats.setHasFixedSize(true)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.allCats.observe(viewLifecycleOwner, Observer<List<Cat>> {

            _allCats = it
            displayedCats = _allCats

            runBlocking(Dispatchers.Default) { _allWords = viewModel.getAllWords() }
            recycler_view_cats.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            recycler_view_cats.adapter = CatAdapter(it, _allWords, viewModel, this.coroutineContext)

        })

        btn_add.setOnClickListener {

            editText_newCat.text.clear()
            newCatName = null
            recycler_view_newCat.visibility = View.VISIBLE
        }

        btnCancel.setOnClickListener { recycler_view_newCat.visibility = View.GONE }

        btnCreate.setOnClickListener {

            //TODO( window disappears on screen rotated. probably fixed with creation of viewmodel or using a binding method)

            newCatName = editText_newCat.text.toString().trim()
            val currentDate: String =
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

            if (newCatName!!.isEmpty()) {

                editText_newCat.error = "category required"
                editText_newCat.requestFocus()
                return@setOnClickListener
            }

            var bool = true

            runBlocking(Dispatchers.Default) { bool = viewModel.validCatName(newCatName!!) }

            if (bool) {

                launch(Dispatchers.Default) {

                    //val  num = viewModel.maxNum()
                    val cat = Cat(newCatName!!, currentDate)
                    viewModel.addCat(cat)
                }

                Toast.makeText(context, "category name accepted", Toast.LENGTH_SHORT).show()
            } else {
                editText_newCat.error = " category already exists"
                editText_newCat.requestFocus()
                Toast.makeText(context, "category name rejected", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            recycler_view_newCat.visibility = View.GONE
            Toast.makeText(context, "category $newCatName successfully created", Toast.LENGTH_SHORT)
                .show()
            editText_newCat.text.clear()
            hideKeyboard()

        }

        recycler_view_newCat.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu_main, menu)

        val searchView: SearchView = menu.findItem(R.id.item_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText.isNullOrEmpty()) {
                    displayedCats = _allCats
                } else {

                    val newCatsList = mutableListOf<Cat>()

                    displayedCats.forEach {

                        if (it.catName.startsWith(newText)) {

                            newCatsList.add(it)
                        }
                    }

                    displayedCats = newCatsList
                }
                recycler_view_cats.layoutManager =
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                recycler_view_cats.adapter =
                    CatAdapter(displayedCats, _allWords, viewModel, coroutineContext)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.item_test -> {

                Test.setCounter()

                val wordsNotAcquired = _allWords.filter { !it.acquired } // words yet to be acquired by user's memory

                var wordsForTest = listOf<Word>()

                if (Test.number >= 2) {

                    val message = "You have already made two tests today, try tomorrow!!  =)"

                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

                } else if (_allWords.size <= 5) {

                    val message = "Please add some more words in order to make a test"
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

                } else {

                    val testFalse = wordsNotAcquired.filter {!it.test }

                    runBlocking(Dispatchers.Default) {

                        testFalse.forEach {

                            val diff = Calendar.DATE - it.lastOk

                            if (it.lvl == 1 && diff >= 3) { it.test = true }

                            else if (it.lvl == 2 && diff >= 7) { it.test = true }

                            viewModel.updateWord(it)

                        }
                    }

                    viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

                    runBlocking(Dispatchers.Default) {wordsForTest = viewModel.wordsForTest }

                    if (wordsForTest.size<=5) {

                        val message = "Please add some more words or wait a bit in order to make a test"
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

                    }

                    else {

                    val bundle = bundleOf("listWords" to wordsForTest)
                    navController.navigate(R.id.actionTest1, bundle)

                    }

                }

                }
            }

            return super.onOptionsItemSelected(item)

        }
    }











