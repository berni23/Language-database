package com.berni.android.prototype1lanbase.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.berni.android.prototype1lanbase.*
import com.berni.android.prototype1lanbase.db.Cat
import com.berni.android.prototype1lanbase.db.Test
import com.berni.android.prototype1lanbase.db.Word
import com.berni.android.prototype1lanbase.databinding.FragmentSecondBinding
import com.berni.android.prototype1lanbase.ui.viewmodel.MainViewModel
import com.berni.android.prototype1lanbase.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : BaseFragment(),DIAware {

    override val di by closestDI()
    private val viewModelFactory: ViewModelFactory by instance<ViewModelFactory>()
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private var firstWord  = true
    private var notAcquired =0

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
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.wordEditText.requestFocus()

        (activity as AppCompatActivity).supportActionBar?.title = cat.catName
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        runBlocking(Dispatchers.Default){

            firstWord = viewModel.anyWord()
            notAcquired = viewModel.getNumAcquired(false)

        }

        if (firstWord)

        {
            val toast: Toast = Toast.makeText(context, resources.getString(R.string.S2_first_word),Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0,0)
            toast.show()

            val anim1: AnimationDrawable
            binding.arrSecond.apply {
                setBackgroundResource(R.drawable.anim_arrow)
                anim1 = background as AnimationDrawable
            }
            anim1.start()
        }

        binding.btnSave.setOnClickListener {

            if (notAcquired >= limitNotAcquired) {
                AlertDialog.Builder(context).apply {

                    setPositiveButton(resources.getString(R.string.okay)) { _, _ -> }
                    this.setMessage(resources.getString(R.string.already_120))

                }.create().show()
            } else {

                //TODO() http request for auto -completion of all the blanks except for the 'word' //

                // required blanks

                val theWord = binding.wordEditText.text.toString().trim()
                val translation1 = binding.trans1EditText.text.toString().trim()
                val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

                // optional blanks

                var example1: String? = binding.ex1EditText.text.toString().trim()
                var translationExample1: String? = binding.ex1TransEditText.text.toString().trim()
                var definition: String? = binding.definitionEditText.text.toString().trim()
                if (example1!!.isEmpty()) {example1 = null}

                if (translationExample1!!.isEmpty()) {translationExample1 = null}
                if (definition!!.isEmpty()) {definition = null}

                if (theWord.isEmpty()) {

                    binding.wordEditText.error = resources.getString(R.string.word_required)
                    binding.wordEditText.requestFocus()
                    return@setOnClickListener
                }

                if (translation1.isEmpty()) {

                    binding.trans1EditText.error = resources.getString(R.string.trans_required)
                    binding.trans1EditText.requestFocus()
                    return@setOnClickListener

                }

                var bool = true
                runBlocking(Dispatchers.Default) { bool = viewModel.validWordId(cat.catId.toString(),theWord)}

                if (bool) {

                    launch {

                        val word = Word(theWord, translation1, example1, translationExample1, definition, date.toString(), cat.catId)
                        viewModel.addWord(word)
                    }

                    Test.warningTest++

                } else {

                    binding.wordEditText.error = resources.getString(R.string.word_exists)
                    binding.wordEditText.requestFocus()
                    return@setOnClickListener
                }

                binding.wordEditText.text.clear()
                binding.trans1EditText.text.clear()
                binding.ex1EditText.text.clear()
                binding.definitionEditText.text.clear()
                binding.ex1TransEditText.text.clear()

                if (firstWord) {

                    val toast: Toast = Toast.makeText(context, resources.getString(R.string.S2_first_word_added), Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                    binding.arrSecond.rotation = -90F
                }

                else if(Test.warningTest>limitWarning)
                {Toast.makeText(context, resources.getString(R.string.word_successfully_added_warning), Toast.LENGTH_LONG).show() }

                else {Toast.makeText(context, resources.getString(R.string.word_successfully_added), Toast.LENGTH_SHORT).show() }
            }
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
               hideKeyboard()
               navController.navigate(R.id.actionWordsList, bundle)
           }

               R.id.item_back -> {

                   hideKeyboard()
                   navController.popBackStack()}
           }
       return super.onOptionsItemSelected(item)
   }


}





