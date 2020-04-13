package com.berni.android.prototype1lanbase.ui

import androidx.lifecycle.ViewModel
import com.berni.android.prototype1lanbase.db.Cat
import com.berni.android.prototype1lanbase.db.Repository
import com.berni.android.prototype1lanbase.db.Word

class MainViewModel(private val repos: Repository ) : ViewModel() {

    suspend fun addCat(cat: Cat)     =  repos.addCat(cat)
    suspend fun addWord(word: Word)  =  repos.addWord(word)

    val AllCats   =  repos.getAllCats()
    val AllWords =   repos.getAllWords()

    }



