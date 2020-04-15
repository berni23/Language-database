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


   // override val kodein: Kodein
    //    get() = kodein
   // private var catExists: Cat? = null

    private val viewModelFactory: ViewModelFactory by instance()
    private lateinit var viewModel: MainViewModel

    private lateinit var categoryName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        catName_editText.isEnabled = true

        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_saveCat.setOnClickListener {

            categoryName = catName_editText.text.toString().trim()
            val currentDate: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

            if (categoryName.isEmpty()) {

                catName_editText.error = "category required"
                catName_editText.requestFocus()
                return@setOnClickListener
            }

            catName_editText.isEnabled = false
            btn_saveCat.isEnabled = false

            viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

            launch{

                val cat  = Cat(categoryName,currentDate)
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

                val word = Word(categoryName,theWord,translation1,null)}

            //catName.hint = catName.text
            //catName.text = null


            //findNavController().navigate(R.id.actionSaveCat)

        }
    }

}
