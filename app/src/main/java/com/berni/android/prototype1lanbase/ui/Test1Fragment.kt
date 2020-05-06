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
import androidx.navigation.Navigation
import org.kodein.di.generic.instance
import kotlinx.android.synthetic.main.fragment_test1.*


/**
 * A simple [Fragment] subclass.
 */
class Test1Fragment : BaseFragment(),KodeinAware {

    override val kodein by closestKodein()

    private lateinit var navController: NavController
    private lateinit var viewModel: MainViewModel

    private  val viewModelFactory: ViewModelFactory by instance<ViewModelFactory>()
    private lateinit var wordsForTest: List<Word>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_test1, container, false)}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        wordsForTest= arguments?.get("listWords") as List<Word>
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        val pickedWords = ranWords()
        btn_testReady.setOnClickListener{

            val bundle = bundleOf("pickedWords" to pickedWords)
            navController.navigate(R.id.actionStartTest, bundle)
            Toast.makeText(context, "let's do it!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun ranWords(): List<Word> {

        val listTest = wordsForTest.shuffled()
        return if (wordsForTest.size >= 15)  { listTest.subList(0,15).toList() }
        else  { listTest }
     }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_test1, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.item_backEditToWordsList -> { navController.popBackStack() }

        }

        return super.onOptionsItemSelected(item)
    }

}


