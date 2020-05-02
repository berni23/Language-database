package com.berni.android.prototype1lanbase.ui
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Word
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import android.icu.util.Calendar
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.generic.instance
import java.util.Locale.filter

/**
 * A simple [Fragment] subclass.
 */
class TestFragment1 : BaseFragment(),KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: ViewModelFactory by instance<ViewModelFactory>()
    private lateinit var viewModel: MainViewModel

    private  var wordsForTest  = listOf<Word>()

    private var today  = Calendar.DATE
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        wordsForTest= arguments?.get("listWords") as MutableList<Word>

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

    }


      fun RanWords(): List<Word> {

          //val wordsForTest = _allWords.filter {it.test==true }

        var listTest = listOf<Word>()
        if (wordsForTest.size <= 15) { listTest = wordsForTest.shuffled()
        } else {listTest = wordsForTest.shuffled().subList(0,14) }

        return listTest

    }


}


