package com.berni.android.prototype1lanbase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Cat
import com.berni.android.prototype1lanbase.db.Word
import com.berni.android.prototype1lanbase.wordId
import kotlinx.android.synthetic.main.fragment_first.*
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

        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        catName_editText.text = categoryName

        btn_save.setOnClickListener {

            val theWord = word_editText.text.toString().trim()
            val translation1 = trans1_editText.text.toString().trim()


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

                val word = Word(wordId(categoryName,theWord),categoryName,theWord,translation1,null)
                viewModel.addWord(word)

            }

            Toast.makeText(context, "word successfully added", Toast.LENGTH_SHORT).show()

            word_editText.text.clear()
            trans1_editText.text.clear()

        }


        btn_displayWords.setOnClickListener{

            val bundle = bundleOf("categoryName" to categoryName)

            Navigation.findNavController(it).navigate(R.id.actionWordsList,bundle)
        }
    }
}
