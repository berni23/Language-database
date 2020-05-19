package com.berni.android.prototype1lanbase.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.berni.android.prototype1lanbase.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class MainActivity : AppCompatActivity(),KodeinAware {

         override val kodein by closestKodein()
         private val viewModelFactory: ViewModelFactory by instance<ViewModelFactory>()

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var bool : Boolean = Tutorial.firstTime
        val viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        runBlocking(Dispatchers.Default) {

            val bool2 = viewModel.anyCat()
            if (!bool2) { bool = false }
        }

        if (bool){

            val intent = Intent(this, TutorialActivity::class.java)
            startActivity(intent)
        }

        else {

            setContentView(R.layout.activity_main)
            setSupportActionBar(findViewById(R.id.toolbar))
            supportActionBar?.title = "Language Database"
        }
    }
    }








