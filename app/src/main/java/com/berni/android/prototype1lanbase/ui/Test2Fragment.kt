package com.berni.android.prototype1lanbase.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Cat
import com.berni.android.prototype1lanbase.db.Word
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.android.synthetic.main.fragment_test2.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class Test2Fragment : BaseFragment() {

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

            if (i==len-1)  {

                val bundle = bundleOf("pickedWords" to pickedWords,"resultTest" to resultTest)
                navController.navigate(R.id.actionTestFinished, bundle)

            }
            else {

                if (wordTest_editext.text.isEmpty()) {
                    wordTest_editext.error = "answer can't be blank"
                    wordTest_editext.requestFocus()
                    return@setOnClickListener
                }

                val question = pickedWords[i].wordName.trim().toString().toLowerCase(Locale.ROOT)
                val answer = wordTest_editext.text.trim().toString().toLowerCase(Locale.ROOT)

                if (question == answer) {resultTest.add(true)}

                else {resultTest.add(false)}

                i++

                counterTest_textView.text = " ${(i).toString()}/$len"
                wordTest_textView.text = pickedWords[i].wordName
                wordTest_editext.text.clear()


            }

        }
    }

}
