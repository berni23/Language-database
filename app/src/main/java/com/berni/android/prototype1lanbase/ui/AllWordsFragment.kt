import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
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
import com.berni.android.prototype1lanbase.db.Word
import com.berni.android.prototype1lanbase.databinding.FragmentAllWordsBinding
import com.berni.android.prototype1lanbase.ui.BaseFragment
import com.berni.android.prototype1lanbase.ui.adapter.WordAdapter
import com.berni.android.prototype1lanbase.ui.viewmodel.MainViewModel
import com.berni.android.prototype1lanbase.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance


/**
 * A simple [Fragment] subclass.
 */
class AllWordsFragment : BaseFragment(), DIAware {

    //lateinit var navController: NavController

 // private lateinit var allWords: List<Word>
    private lateinit var lastAdded: List<Word?>
    private var displayedWords =  listOf<Word>()
    private var displayedWords1 =  listOf<Word>()
    private var lastAdditionDate: String? = ""
    private lateinit var navController: NavController

    override val di by closestDI()
    private val viewModelFactory: ViewModelFactory by instance<ViewModelFactory>()
    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentAllWordsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        _binding = FragmentAllWordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title =  resources.getString(R.string.all_words)

        navController = Navigation.findNavController(view)
        binding.recyclerViewWords.setHasFixedSize(true)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.allWords.observe(viewLifecycleOwner, Observer<List<Word>> {

            binding.recyclerViewWords.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

            runBlocking(Dispatchers.Default){

                displayedWords =  it
                binding.recyclerViewWords.adapter = WordAdapter(displayedWords, viewModel, listOf<CountDownTimer>(), this.coroutineContext)

            }

            displayedWords1 = displayedWords

            lastAdded = listOf(

                displayedWords.getOrNull(0),
                displayedWords.getOrNull(1),
                displayedWords.getOrNull(2)
            )

            lastAdditionDate = displayedWords.getOrNull(0)?.date
            var lastAdditions =  resources.getString(R.string.last_additions)
            lastAdded.forEach { if (it?.wordName != null) { lastAdditions += " ${it.wordName}," } }
            lastAdditions = lastAdditions.dropLast(1)  // drop the last comma of the string
            if (lastAdded.elementAt(0)?.wordName == null) { lastAdditions =  resources.getString(R.string.no_words_added_yet) }
            val stringLastAdditionDate = "${resources.getString(R.string.last_added_on)} $lastAdditionDate"

            // editing the corresponding info to the textviews

            binding.textViewNumWords.text = " ${binding.recyclerViewWords.adapter?.itemCount?:0} ${resources.getString(R.string.words)}"
            lastAdditionDate?.let {binding.textViewLastDate.text = stringLastAdditionDate }
            binding.textViewLastAdditions.text = lastAdditions

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_words, menu)

        val searchItem = menu.findItem(R.id.item_search)

        val searchView: SearchView = searchItem.actionView as SearchView

        searchView.setOnSearchClickListener { setItemsVisibility(menu, searchItem, false) }

        searchView.setOnCloseListener { setItemsVisibility(menu, searchItem, true)
            false
        }


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean { return false }

            override fun onQueryTextChange(newText: String?): Boolean {
                val newWordsList = mutableListOf<Word>()

                displayedWords1.forEach { if (it.wordName.startsWith(newText!!)) { newWordsList.add(it) } }
                displayedWords1 = newWordsList
                binding.recyclerViewWords.adapter = WordAdapter(displayedWords1, viewModel, listOf<CountDownTimer>(), coroutineContext)
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

        binding.recyclerViewWords.adapter = WordAdapter(displayedWords1, viewModel, listOf<CountDownTimer>(), coroutineContext)
        return super.onOptionsItemSelected(item)
    }

}



