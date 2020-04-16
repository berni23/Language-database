package com.berni.android.prototype1lanbase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Cat
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

    private val viewModelFactory: ViewModelFactory by instance()
    private lateinit var viewModel: MainViewModel
    lateinit var categoryName: String

    var newCategoryName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categoryName = arguments?.getString("categoryName").toString()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // just let the user write a category name if they are creating a new one, not if
        //  using an already created one

         if (categoryName!="null") {

             catName_editText.setText(categoryName)
             catName_editText!!.isEnabled = false
             btn_saveCat.isEnabled= false  }

         else {catName_editText.isEnabled = true}

        btn_saveCat.setOnClickListener {

            newCategoryName = catName_editText!!.text.toString().trim()
            val currentDate: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

            if (newCategoryName!!.isEmpty()) {

                catName_editText.error = "category required"
                catName_editText.requestFocus()
                return@setOnClickListener
            }

            catName_editText.isEnabled = false
            btn_saveCat.isEnabled = false

            viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

            launch{

                val cat  = Cat(newCategoryName!!,currentDate)
                viewModel.addCat(cat)

            }

            catName_editText.isEnabled = false

        }

        btn_save.setOnClickListener {

            val theWord = word_editText.text.toString().trim()
            val translation1 = trans1.text.toString().trim()

            if (categoryName.isEmpty()) {

                catName_editText.error = "category required"
                catName_editText.requestFocus()
                return@setOnClickListener
            }

            if (theWord.isEmpty()) {

                word_editText.error = "word required"
                word_editText.requestFocus()
                return@setOnClickListener

            }

            launch{

                val word = Word(wordId(categoryName,theWord),categoryName,theWord,translation1,null)
                viewModel.addWord(word)

            }

            //catName.hint = catName.text
            //catName.text = null
            //findNavController().navigate(R.id.actionSaveCat)

        }
    }
}
