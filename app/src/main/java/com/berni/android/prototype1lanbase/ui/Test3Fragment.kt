package com.berni.android.prototype1lanbase.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Test
import com.berni.android.prototype1lanbase.db.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import kotlinx.android.synthetic.main.fragment_test3.*
import kotlinx.coroutines.runBlocking
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

/**
 * A simple [Fragment] subclass.
 */
class Test3Fragment : BaseFragment(),KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: ViewModelFactory by instance<ViewModelFactory>()
    private lateinit var viewModel: MainViewModel
    private lateinit var result: List<Boolean>
    private lateinit var testWords: List<Word>
    private lateinit var navController: NavController
    private var today = Calendar.DATE
    private var i = 0
    private var correct: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        result = arguments?.get("resultTest") as List<Boolean>
        testWords = arguments?.get("pickedWords") as List<Word>
        return inflater.inflate(R.layout.fragment_test3, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        runBlocking(Dispatchers.Default) {

            testWords.forEach {

                if (result[i]) {

                   // it.lvl += 1 // uncoment when using app
                    correct += 1
                }
                if (it.lvl >= 3) {
                    it.acquired = true
                }

                //it.test = false // uncomment when using app
                it.lastOk = today
                it.acquired=true // temp code, just to check how some  views  get displayed on word acquired, delete when using app .
                viewModel.updateWord(it)
                i++

            }
        }

        Test.number += 1
        progressbar.progress = correct
        progressbar.max = result.size
        ratioTestFinished.text = "$correct/${result.size}"

        msg()

        btn_backToMain.setOnClickListener {

            if (i == result.size) {
                navController.navigate(R.id.actionBackToMain)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true
        ) {
            override fun handleOnBackPressed() {
                navController.navigate(R.id.actionBackToMain)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,  // LifecycleOwner
            callback
        )
    }

    private fun msg() {

        var text = resources.getString(R.string.score5)
        val ratio: Double = correct.toDouble()/result.size.toDouble()
        if (ratio==1.0) {text = resources.getString(R.string.score1) }
        else if (ratio>=0.9)  {text = resources.getString(R.string.score2) }
        else if (ratio>=0.7)  {text = resources.getString(R.string.score3) }
        else if(ratio >=0.5)  {text = resources.getString(R.string.score4) }

        msgTestFinished.text = text

    }
}
