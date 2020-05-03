package com.berni.android.prototype1lanbase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.berni.android.prototype1lanbase.db.Word
import com.berni.android.prototype1lanbase.ui.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class Test3Fragment : BaseFragment() {

    private lateinit var result: List<Boolean>
    private lateinit var testWords: List<Word>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}
