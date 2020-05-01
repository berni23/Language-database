package com.berni.android.prototype1lanbase.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.berni.android.prototype1lanbase.R
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 */
class TestFragment1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

   /** val  currentTime= Calendar.getInstance().timeInMillis

    val alsoCurrentTime = Calendar.getInstance().timeInMillis

    val diff =  java.util.concurrent.TimeUnit.DAYS.convert((currentTime - alsoCurrentTime),
        TimeUnit.MILLISECONDS)

    **/




}


