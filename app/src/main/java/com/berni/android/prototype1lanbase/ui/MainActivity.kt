package com.berni.android.prototype1lanbase.ui


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Test.time
import com.berni.android.prototype1lanbase.ui.tutorial.Tutorial
import com.berni.android.prototype1lanbase.ui.tutorial.TutorialActivity
import com.berni.android.prototype1lanbase.ui.viewmodel.MainViewModel
import com.berni.android.prototype1lanbase.ui.viewmodel.ViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import com.berni.android.prototype1lanbase.ui.test.Test2Fragment
import kotlin.concurrent.timer


class MainActivity : AppCompatActivity(),KodeinAware {


    override val kodein by closestKodein()
    val limitNotAcquired = 120
    private val viewModelFactory: ViewModelFactory by instance<ViewModelFactory>()

   // var myFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {

       /* if (savedInstanceState != null) {
            //Restore the fragment's instance
            myFragment = supportFragmentManager.getFragment(savedInstanceState, "Test2Fragment");

        }


        */

        AndroidThreeTen.init(this)
        super.onCreate(savedInstanceState)
        var bool: Boolean = Tutorial.firstTime
        val viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        runBlocking(Dispatchers.Default) {

            val bool2 = viewModel.anyCat()
            if (!bool2) {
                bool = false
            }
        }

        if (bool) {

            val intent = Intent(this, TutorialActivity::class.java)
            startActivity(intent)
        } else {

            setContentView(R.layout.activity_main)
            setSupportActionBar(findViewById(R.id.toolbar))
            supportActionBar?.title = "LDB"
        }
    }

   /* override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        myFragment.let{supportFragmentManager.putFragment(outState, "myFragmentName", myFragment!!)}
        outState.putLong("millisPassed", millisPassed)

    }


    */

}







