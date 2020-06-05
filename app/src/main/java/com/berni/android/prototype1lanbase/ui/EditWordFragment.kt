package com.berni.android.prototype1lanbase.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Word
import com.berni.android.prototype1lanbase.ui.viewmodel.MainViewModel
import com.berni.android.prototype1lanbase.ui.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_edit_word.*
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
    private lateinit var word: Word
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        word = arguments?.get("word") as Word
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_edit_word, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.edit_info)
        navController = Navigation.findNavController(view)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        // mandatory fields

        editWord_editText.setText(word.wordName)
        editTrans1_editText.setText(word.trans1)
        editDate.setText("${resources.getString(R.string.added_on)} ${word.date}")

        //optional fields

        editEx1_editText.setText(word.ex1 ?: "")
        editEx1Trans_editText.setText(word.trans_ex1 ?: "")
        editDefinition_editText.setText(word.definition ?: "")

        edit_btn_save.setOnClickListener {

            val catId = word.catParent!!
            val name = editWord_editText.text?.toString()?.trim()
            val trans = editTrans1_editText.text?.toString()?.trim()
            val ex = editEx1_editText.text?.toString()?.trim()
            val transEx = editEx1Trans_editText.text?.toString()?.trim()
            val def = editDefinition_editText.text?.toString()?.trim()

            if (name!!.isEmpty()) {

                editWord_editText.error = resources.getString(R.string.word_required)
                editWord_editText.requestFocus()
                return@setOnClickListener

            }

            if (trans!!.isEmpty()) {

                editTrans1_editText.error =resources.getString(R.string.trans_required)
                editTrans1_editText.requestFocus()
                return@setOnClickListener

            }

            launch(Dispatchers.Default) {

                val updatedWord = Word(name, trans, ex, transEx, def, word.date, catId)
                viewModel.deleteWord(word)
                viewModel.addWord(updatedWord)

            }

            Toast.makeText(it.context, resources.getString(R.string.editing_word_properties), Toast.LENGTH_SHORT).show()

        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_words, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.item_backEditToWordsList -> { navController.popBackStack() }

        }

        return super.onOptionsItemSelected(item)
    }

}