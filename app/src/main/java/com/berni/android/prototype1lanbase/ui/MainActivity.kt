package com.berni.android.prototype1lanbase.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.berni.android.prototype1lanbase.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_first.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.title = " Language Database"


    }
}


      /**  fun getAppContext(): Context {
          return getApplicationContext()

       **/






