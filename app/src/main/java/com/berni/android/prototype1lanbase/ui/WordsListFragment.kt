package com.berni.android.prototype1lanbase.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Cat
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_words_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

/**
 * A simple [Fragment] subclass.
 */
class WordsListFragment : BaseFragment(), KodeinAware {

    lateinit var navController: NavController
    lateinit var categoryName: String

    override val kodein by closestKodein()

    private val viewModelFactory: ViewModelFactory by instance<ViewModelFactory>()
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categoryName = arguments?.getString("categoryName").toString()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_words_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      //  LinearLayoutManager(Context context, int orientation, boolean reverseLayout)

        recycler_view_words.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL, false)

        navController = Navigation.findNavController(view)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)


        launch(Dispatchers.Default){
            recycler_view_words.adapter = WordAdapter(viewModel.wordsInCat(categoryName))

        }
        //viewModel.allCats.observe(viewLifecycleOwner, Observer<List<Cat>> { recycler_view_words.adapter = CatAdapter(it) })


    }

}
