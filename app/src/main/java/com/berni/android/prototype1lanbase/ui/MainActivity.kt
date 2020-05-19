package com.berni.android.prototype1lanbase.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.berni.android.prototype1lanbase.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = " Language Database"


        val bool = Tutorial.firstTime

        if (bool) {
            val intent = Intent(this, TutorialActivity::class.java)
            startActivity(intent)
        }
    }
    }








