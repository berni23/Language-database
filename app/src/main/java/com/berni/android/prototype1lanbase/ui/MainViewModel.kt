package com.berni.android.prototype1lanbase.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.berni.android.prototype1lanbase.db.Cat
import com.berni.android.prototype1lanbase.db.Repository
import com.berni.android.prototype1lanbase.db.Word
import java.util.*

class MainViewModel(private val repos: Repository ) : ViewModel(){



    suspend fun addCat(cat: Cat)     =  repos.addCat(cat)
    suspend fun addWord(word: Word)  =  repos.addWord(word)

  //  val allCats: LiveData<List<Cat>>
  //  get() = repos.getAllCats()

    val allCats: LiveData<List<Cat>> = repos.getAllCats()

    val allWords: LiveData<List<Word>> =   repos.getAllWords()

    }



