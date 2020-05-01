package com.berni.android.prototype1lanbase.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Word
import com.berni.android.prototype1lanbase.wordId
import kotlinx.android.synthetic.main.fragment_edit_word.*
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

/**
 * A simple [Fragment] subclass.
 */
class EditWordFragment : BaseFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: ViewModelFactory by instance<ViewModelFactory>()
    private lateinit var viewModel: MainViewModel
    private  lateinit var word :Word

    //private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        word = arguments?.get("word") as Word
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_word, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = word.wordName
        viewModel  = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        // mandatory fields

        editWord_editText.setText(word.wordName)
        editTrans1_editText.setText(word.trans1)
        editDate.setText("added on ${word.date}")

        //optional fields

        editEx1_editText.setText(word.ex1?:"")
        editEx1Trans_editText.setText(word.trans_ex1?:"")
        editDefinition_editText.setText(word.definition?:"")

        edit_btn_save.setOnClickListener{

                val catId = word.catParent!!
                val name = editWord_editText.text?.toString()?.trim()
                val trans =editTrans1_editText.text?.toString()?.trim()
                val ex = editEx1_editText.text?.toString()?.trim()
                val transEx = editEx1Trans_editText.text?.toString()?.trim()
                val def = editDefinition_editText.text?.toString()?.trim()

                if (name!!.isEmpty()) {

                    editWord_editText.error = "word required"
                    editWord_editText.requestFocus()
                    return@setOnClickListener

                }

                if (trans!!.isEmpty()) {

                    editTrans1_editText.error = "translation required"
                    editTrans1_editText.requestFocus()
                    return@setOnClickListener

                }

                val id = wordId(catId.toString(),name)

            launch(Dispatchers.Default){

                val updatedWord = Word(id,name,trans,ex,transEx,def,word.date,catId)

                if (id==word.wordId)  {viewModel.updateWord(updatedWord)}

                else {

                    viewModel.deleteWord(word)
                    viewModel.addWord(updatedWord)
                }
             }

            Toast.makeText(it.context,"editing word properties...",Toast.LENGTH_SHORT).show()

        }

    }
}