package com.berni.android.prototype1lanbase.ui

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
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
    private var today  = Calendar.DATE


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        result= arguments?.get("resultTest") as List<Boolean>
        testWords = arguments?.get("pickedWords") as List<Word>

        return inflater.inflate(R.layout.fragment_test3, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        launch(Dispatchers.Default){

            var i=0
            testWords.forEach{

                if (result[i]) {it.lvl += 1}
                if(it.lvl>=3)  {it.acquired=true}

                it.test = false
                it.lastOk = today
                viewModel.updateWord(it)

                i++
        }
        }
        //display test statistics
        // tirori on answer correct

    }
}
