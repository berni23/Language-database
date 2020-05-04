package com.berni.android.prototype1lanbase.ui

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Word
import kotlinx.android.synthetic.main.fragment_test2.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class Test2Fragment : BaseFragment(){

    private var pickedWords = listOf<Word>()
    private var resultTest = arrayListOf<Boolean>()
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        pickedWords = arguments?.get("pickedWords") as List<Word>
        return inflater.inflate(R.layout.fragment_test2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        wordTest_textView.text = pickedWords[0].wordName
        val len = pickedWords.size
        wordTest_editext.text.clear()
        var i: Int = 0

        counterTest_textView.text = " 0/$len"
        btn_nextTestWord.setOnClickListener {

            if (wordTest_editext.text.isEmpty()) {
                wordTest_editext.error = "answer can't be blank"
                wordTest_editext.requestFocus()
                return@setOnClickListener
            }

            val question = pickedWords[i].wordName.trim().toString().toLowerCase(Locale.ROOT)
            val answer = wordTest_editext.text.trim().toString().toLowerCase(Locale.ROOT)

            if (question == answer) {
                resultTest.add(true)
            } else {
                resultTest.add(false)
            }

            wordTest_textView.text = pickedWords[i].wordName
            wordTest_editext.text.clear()

            i++
            if (i == len) {

                val bundle = bundleOf("pickedWords" to pickedWords, "resultTest" to resultTest)
                navController.navigate(R.id.actionTestFinished, bundle)
            }
            counterTest_textView.text = " $i/$len"
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {

                var bool: Boolean? = true
                AlertDialog.Builder(activity!!).apply {
                    setTitle("Are you sure?")
                    setMessage("the test will be canceled and the progress lost")
                    setPositiveButton("Yes") { _, _ ->

                        navController.navigate(R.id.actionCancelTest)

                    }
                    setNegativeButton("No") { _, _ ->

                    }
                }.create().show()

            }
        }
        requireActivity().getOnBackPressedDispatcher().addCallback(
            this,  // LifecycleOwner
            callback
        )
    }

      /** override fun onBackPressed(): Boolean {

        var bool: Boolean? = null
        AlertDialog.Builder(this.context).apply {
            setTitle("Are you sure?")
            setMessage("You cannot undo this operation")
            setPositiveButton("Yes") { _, _ ->

                bool = true
            }
            setNegativeButton("No") { _, _ ->

                bool = false

            }
        }.create().show()

        return bool!!
    }**/




}
    /**verride fun OnBackPressedCallback() {

        Toast.makeText(context,"button back pressed ", Toast.LENGTH_SHORT)
    }
    **/


