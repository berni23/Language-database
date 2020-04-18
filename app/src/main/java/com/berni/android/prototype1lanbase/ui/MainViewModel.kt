package com.berni.android.prototype1lanbase.ui

import android.view.View
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Cat
import com.berni.android.prototype1lanbase.db.Repository
import com.berni.android.prototype1lanbase.db.Word
import kotlinx.android.synthetic.main.fragment_first.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import java.util.*


// Main view model for everything related with accessing or updating database

class MainViewModel(private val repos: Repository ) : ViewModel(){



    suspend fun addCat(cat: Cat)     =  repos.addCat(cat)
    suspend fun addWord(word: Word)  =  repos.addWord(word)

    val allCats: LiveData<List<Cat>> =     repos.getAllCats()
    val allWords: LiveData<List<Word>> =   repos.getAllWords()

    val newcatWindow_visible = false



}



