package com.berni.android.prototype1lanbase.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.berni.android.prototype1lanbase.db.Cat
import com.berni.android.prototype1lanbase.db.CatWords
import com.berni.android.prototype1lanbase.db.Repository
import com.berni.android.prototype1lanbase.db.Word

// Main view model for everything related with accessing or updating database

class MainViewModel(private val repos: Repository ) : ViewModel() {

    suspend fun addCat(cat: Cat) = repos.addCat(cat)

    suspend fun addWord(word: Word) = repos.addWord(word)

    suspend fun updateWord(word: Word) = repos.updateWord(word)

    suspend fun updateCat(oldName: String, newName: String) = repos.updateCat(oldName, newName)

    suspend fun getAllWords(): MutableList<Word> { return repos.getAllWords() }

    suspend fun orderDays(): MutableList<String> {return repos.orderDates()}

    suspend fun counterWords() : Int {return repos.counterWords()}

    fun deleteWordsInCat(currentCatId: Int) = repos.deleteWordsInCat(currentCatId)

    fun deleteCat(currentCat: Cat) = repos.deleteCat(currentCat)

    fun anyCat() = repos.anyCat()

    fun deleteWord(currentWord: Word) = repos.deleteWord(currentWord)

    fun wordsInCat(currentCatId: Int) = repos.wordsInCat(currentCatId)

    fun validCatName(catName: String): Boolean { return repos.validCatName(catName).isEmpty() }

    fun validWordId(catName: String, wordName: String): Boolean { return repos.validWordId(catName, wordName).isEmpty() }

    val allCats: LiveData<List<Cat>> = repos.getAllCats()

    val allWords: LiveData<List<Word>> = repos.getAllWordsLive()

    suspend fun wordsForTest(): List<Word> = repos.wordsForTest()

    fun catsWithWords(): LiveData<List<CatWords>> = repos.catsWithWords()

    fun catsNWords(): List<CatWords> = repos.catsNwords()

    suspend fun counterAcquired(): List<Int> {

        val words = repos.getAllWords()
        var int1 = 0
        var int2 = 0

        words.forEach {

            if (it.acquired) { int1++ }
            else { int2++ }
        }
        return listOf(int1, int2)
    }
}

