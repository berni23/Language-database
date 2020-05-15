package com.berni.android.prototype1lanbase.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
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
import java.util.concurrent.TimeUnit


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

        pickedWords = arguments?.get("pickedWords") as ArrayList<Word>
        return inflater.inflate(R.layout.fragment_test2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timer.start()

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
            if (question == answer) { resultTest.add(true) }
            else {resultTest.add(false) }

            i++
            if (i == len) {

                val bundle = bundleOf("pickedWords" to pickedWords, "resultTest" to resultTest)
                navController.navigate(R.id.actionTestFinished, bundle)
                timer.cancel()

            } else {
                counterTest_textView.text = " $i/$len"
                wordTest_textView.text = pickedWords[i].wordName
                wordTest_editext.text.clear()
            }
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
                        timer.cancel()

                    }
                    setNegativeButton("No") { _, _ -> }
                }.create().show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,  // LifecycleOwner
            callback
        )
    }

    private val timer = object: CountDownTimer(900000,1000) {
        override fun onTick(millis: Long) {


            timerTest_textView.let{timerTest_textView.text =  String.format("%02d : %02d ",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))}
        }


        override fun onFinish() {

            Toast.makeText(context,"time's up!",Toast.LENGTH_SHORT).show()
            val diff = pickedWords.size -resultTest.size

            if (diff >0) {
                for (i in 1..diff) {

                    resultTest.add(false)

                }
            }
            val bundle = bundleOf("pickedWords" to pickedWords, "resultTest" to resultTest)
            navController.navigate(R.id.actionTestFinished, bundle)

        }
    }

}


