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
import com.berni.android.prototype1lanbase.db.Cat
import com.berni.android.prototype1lanbase.db.Word
import com.berni.android.prototype1lanbase.wordId
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
    private lateinit var cat: Cat
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cat= arguments?.get("categoryName") as Cat

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
        (activity as AppCompatActivity).supportActionBar?.title = cat.catName
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        btn_save.setOnClickListener {

            //TODO() http request for auto -completion of all the blanks except for the 'word'

            // required blanks

            val theWord = word_editText.text.toString().trim()
            val translation1 = trans1_editText.text.toString().trim()
            val date =  SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

            // optional blanks

            var example1 : String? = ex1_editText.text.toString().trim()
            var translation_example1 : String? = ex1Trans_editText.text.toString().trim()
            var definition : String? = definition_editText.text.toString().trim()


            if(example1!!.isEmpty()) {example1 =null}
            if(translation_example1!!.isEmpty()) {translation_example1 =null}
            if(definition!!.isEmpty()) {definition=null}

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

             var bool = true
             var id = wordId(cat.catId.toString(),theWord)

            runBlocking(Dispatchers.Default) {bool = viewModel.validWordId(cat.catId.toString(),theWord) }

            if(bool) {

            launch{

                val word = Word(theWord,translation1,example1,translation_example1,definition,date.toString(),cat.catId)
                viewModel.addWord(word)

            }   }

            else {

                  word_editText.error = "word already exists"
                  word_editText.requestFocus()
                  return@setOnClickListener
            }

            Toast.makeText(context, "word successfully added", Toast.LENGTH_SHORT).show()

            word_editText.text.clear()
            trans1_editText.text.clear()
            ex1_editText.text.clear()
            definition_editText.text.clear()
            ex1Trans_editText.text.clear()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_fragment_second, menu)
    }

   override fun onOptionsItemSelected(item: MenuItem): Boolean {

       when (item.itemId) {
           R.id.item_toWordsList -> {

               val bundle = bundleOf("cat" to cat)
               navController.navigate(R.id.actionWordsList, bundle)
           }

               R.id.item_back -> {

                   navController.popBackStack()


               }
           }
       return super.onOptionsItemSelected(item)
   }
}





