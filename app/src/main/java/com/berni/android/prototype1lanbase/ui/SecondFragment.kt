package com.berni.android.prototype1lanbase.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Word
import com.berni.android.prototype1lanbase.wordId
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : BaseFragment(),KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: ViewModelFactory by instance<ViewModelFactory>()
    private lateinit var viewModel: MainViewModel
    private lateinit var categoryName: String
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categoryName = arguments?.getString("categoryName").toString()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        (activity as AppCompatActivity).supportActionBar?.title = categoryName

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)


        btn_save.setOnClickListener {

            val theWord = word_editText.text.toString().trim()
            val translation1 = trans1_editText.text.toString().trim()
            var example1 : String? = ex1_editText.text.toString().trim()

            if(example1!!.isEmpty()) {example1 =null}

            val date =  SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
            
            if (theWord.isEmpty()) {

                word_editText.error = "word required"
                word_editText.requestFocus()
                return@setOnClickListener

            }

            if (translation1.isEmpty()) {

                trans1_editText.error = "translation required"
                trans1_editText.requestFocus()
                return@setOnClickListener

            }

            launch{

                val word = Word(wordId(categoryName,theWord),categoryName,theWord,translation1,example1,date.toString())
                viewModel.addWord(word)

            }

            Toast.makeText(context, "word successfully added", Toast.LENGTH_SHORT).show()

            word_editText.text.clear()
            trans1_editText.text.clear()
            ex1_editText.text.clear()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_fragment_second, menu)

        val wordsListView: View? = view?.findViewById<View>(R.id.item_toWordsList)

    }

   override fun onOptionsItemSelected(item: MenuItem): Boolean {

       val wordsListView: View? = view?.findViewById<View>(R.id.item_toWordsList)

       var sorted: List<Word>? = null

       when (item.itemId) {

           R.id.item_toWordsList -> {

               val bundle = bundleOf("categoryName" to categoryName)
               navController.navigate(R.id.actionWordsList, bundle)
           }
       }
       return super.onOptionsItemSelected(item)
   }
}





