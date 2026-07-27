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
import com.berni.android.prototype1lanbase.databinding.FragmentEditWordBinding
import com.berni.android.prototype1lanbase.ui.viewmodel.MainViewModel
import com.berni.android.prototype1lanbase.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance

/**
 * A simple [Fragment] subclass.
 */
class EditWordFragment : BaseFragment(), DIAware {

    override val di by closestDI()

    private val viewModelFactory: ViewModelFactory by instance<ViewModelFactory>()
    private var _binding: FragmentEditWordBinding? = null
    private val binding get() = _binding!!
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
        _binding = FragmentEditWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.edit_info)
        navController = Navigation.findNavController(view)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        // mandatory fields

        binding.editWordEditText.setText(word.wordName)
        binding.editTrans1EditText.setText(word.trans1)
        binding.editDate.setText("${resources.getString(R.string.added_on)} ${word.date}")

        //optional fields

        binding.editEx1EditText.setText(word.ex1 ?: "")
        binding.editEx1TransEditText.setText(word.trans_ex1 ?: "")
        binding.editDefinitionEditText.setText(word.definition ?: "")

        binding.editBtnSave.setOnClickListener {

            val catId = word.catParent!!
            val name = binding.editWordEditText.text?.toString()?.trim()
            val trans = binding.editTrans1EditText.text?.toString()?.trim()
            val ex = binding.editEx1EditText.text?.toString()?.trim()
            val transEx = binding.editEx1TransEditText.text?.toString()?.trim()
            val def = binding.editDefinitionEditText.text?.toString()?.trim()

            if (name!!.isEmpty()) {

                binding.editWordEditText.error = resources.getString(R.string.word_required)
                binding.editWordEditText.requestFocus()
                return@setOnClickListener

            }

            if (trans!!.isEmpty()) {

                binding.editTrans1EditText.error =resources.getString(R.string.trans_required)
                binding.editTrans1EditText.requestFocus()
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