package com.berni.android.prototype1lanbase.ui
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Word
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import android.icu.util.Calendar
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import org.kodein.di.generic.instance
import kotlinx.android.synthetic.main.fragment_test1.*


/**
 * A simple [Fragment] subclass.
 */
class Test1Fragment : BaseFragment(),KodeinAware {

    override val kodein by closestKodein()

    private lateinit var navController: NavController
    private lateinit var viewModel: MainViewModel

    private val viewModelFactory: ViewModelFactory by instance<ViewModelFactory>()
    private  var wordsForTest  = listOf<Word>()
   // private var today  = Calendar.DATE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? { return inflater.inflate(R.layout.fragment_test1, container, false)}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        wordsForTest= arguments?.get("listWords") as MutableList<Word>
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        val pickedWords = ranWords()

        btn_testReady.setOnClickListener{

            val bundle = bundleOf("pickedWords" to pickedWords)

            Toast.makeText(context, "let's do it!", Toast.LENGTH_SHORT).show()

            navController.navigate(R.id.actionStartTest, bundle)

        }
    }


      fun ranWords(): List<Word> {

        //val wordsForTest = _allWords.filter {it.test==true }
        var listTest = listOf<Word>()
        if (wordsForTest.size <= 15) {listTest = wordsForTest.shuffled()
        } else {listTest = wordsForTest.shuffled().subList(0,14)}
        return listTest

    }

}


