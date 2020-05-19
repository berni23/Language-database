package com.berni.android.prototype1lanbase.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import com.berni.android.prototype1lanbase.R
import kotlinx.android.synthetic.main.activity_tutorial.*

class TutorialActivity : AppCompatActivity() {

    var msg: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        btn_tutorial_next.setOnClickListener {

            if (msg) {

                val string: String = getString(R.string.tutorial_m3)
                tutorial_t1.text = string
                tutorial_t2.text = ""
                tutorial_t3.text = ""
                msg = false

            } else {

                Tutorial.firstTime = false
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}




