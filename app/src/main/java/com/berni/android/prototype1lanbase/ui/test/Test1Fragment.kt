package com.berni.android.prototype1lanbase.ui.test
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.databinding.FragmentTest1Binding
import com.berni.android.prototype1lanbase.db.Word
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.berni.android.prototype1lanbase.ui.BaseFragment
import com.berni.android.prototype1lanbase.ui.viewmodel.MainViewModel
import com.berni.android.prototype1lanbase.ui.viewmodel.ViewModelFactory
import org.kodein.di.instance


/**
 * A simple [Fragment] subclass.
 */
class Test1Fragment : BaseFragment(),DIAware {

    override val di by closestDI()

    private lateinit var navController: NavController
    private lateinit var viewModel: MainViewModel

    private  val viewModelFactory: ViewModelFactory by instance<ViewModelFactory>()
    private lateinit var wordsForTest: List<Word>
    private var _binding: FragmentTest1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        setHasOptionsMenu(true)
        _binding = FragmentTest1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Test"
        navController = Navigation.findNavController(view)
        wordsForTest= arguments?.get("listWords") as List<Word>
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        val pickedWords = ranWords()
        binding.btnTestReady.setOnClickListener{

            val bundle = bundleOf("pickedWords" to pickedWords)
            navController.navigate(R.id.actionStartTest, bundle)
            Toast.makeText(context, resources.getString(R.string.lets_do_it), Toast.LENGTH_SHORT).show()
        }
    }

    private fun ranWords(): List<Word> {

        val listTest = wordsForTest.shuffled()
        return if (wordsForTest.size >= 15)  {listTest.subList(0,15).toList() }
        else  {listTest}
     }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_test1, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {R.id.item_backEditToWordsList -> { navController.popBackStack() } }
        return super.onOptionsItemSelected(item)
    }

}


