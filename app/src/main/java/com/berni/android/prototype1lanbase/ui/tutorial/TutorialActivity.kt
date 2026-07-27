package com.berni.android.prototype1lanbase.ui.tutorial


import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.databinding.ActivityTutorialBinding
import com.berni.android.prototype1lanbase.ui.MainActivity

class TutorialActivity : AppCompatActivity() {

    var msg: Int = 0
    private lateinit var binding: ActivityTutorialBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityTutorialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = " Language Database"
        binding.tutorialT2.textSize = 20F

        binding.btnTutorialNext.setOnClickListener { messages(1 ) }
        binding.btnTutorialBack.setOnClickListener { messages(-1) }
    }

    override fun onBackPressed() { finish() }

   private fun messages(count:Int) {

    if (msg == 0) {

        if (count>0) {

            msg += count
            binding.tutorialT1.text = resources.getString(R.string.tutorial_m3)
            binding.tutorialT2.visibility = GONE
            binding.tutorialT3.visibility = GONE

        }

    } else if (msg == 1) {


        msg+=count
        binding.tutorialT1.text = resources.getString(R.string.tutorial_m4)
        binding.tutorialT2.visibility = VISIBLE
        binding.tutorialT2.text = resources.getString(R.string.tutorial_m5)


    } else if (msg == 2) {
        msg+=count
        binding.tutorialT1.text = resources.getString(R.string.tutorial_m6)
        binding.tutorialT2.text = resources.getString(R.string.tutorial_m7)

    } else if (msg == 3) {

        msg+=count

        binding.tutorialT1.text = resources.getString(R.string.tutorial_m8)
        binding.tutorialT2.visibility = GONE

    } else if (msg == 4) {

        msg+=count
        binding.tutorialT1.text = resources.getString(R.string.tutorial_m9)
        binding.tutorialT2.visibility = GONE

    } else if (msg == 5) {


        msg += count

        binding.tutorialT1.visibility = GONE
        binding.tutorialT2.visibility = VISIBLE
        binding.tutorialT2.text = resources.getString(R.string.tutorial_m10)
        binding.tutorialT2.textSize = 30F

    }

     else {

        Tutorial.firstTime = false
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        }
   }
}



