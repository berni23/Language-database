package com.berni.android.prototype1lanbase.db

import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


interface Repository{

        val catDao: CatDao
        val cat: Cat
        val word: Word

        suspend fun addCat(cat: Cat)  {catDao.addCat(cat)}

        suspend fun addWord(word: Word)  {catDao.addWord(word)}

        suspend fun updateWord(word: Word) {catDao.updateWord(word)}

        suspend fun updateCat(oldName:String,newName:String) {catDao.updateCat(oldName,newName)}

        suspend fun getAllWords(): MutableList<Word> {return catDao.getAllWords()}

        suspend fun orderDates(): MutableList<String> { return catDao.orderDays()}

        suspend fun orderAcquired():MutableList<String> {return catDao.orderDaysAcquired()}

        suspend fun counterWords(): Int {return catDao.getAllWords().size}

        fun anyCat() : Boolean {return catDao.catsNwords().isEmpty() }

        fun anyWord() : Boolean { return catDao.getAllWords().isEmpty() }

        fun getAllCats () : LiveData<List<Cat>> {return catDao.getAllCats()}

        fun validCatName(catName: String) : List<Cat> {return catDao.validCatName(catName)}

        fun validWordId(catName: String,wordName:String): List<Word> {return catDao.validWordId(catName,wordName)}

        fun getAllWordsLive(): LiveData<List<Word>> {return catDao.getAllWordsLive()}

        fun wordsInCat(currentCatId: Int) : LiveData<List<Word>> {return catDao.wordsInCat(currentCatId)}

        fun catsWithWords() : LiveData<List<CatWords>> {return catDao.catsWithWords()}

        fun catsNwords() : MutableList<CatWords> {return catDao.catsNwords()}

        fun deleteWordsInCat(currentCatId: Int) {catDao.deleteWordsInCat(currentCatId)}

        fun deleteCat(currentCat:Cat) {catDao.deleteCat(currentCat)}

        fun deleteWord(currentWord: Word) {catDao.deleteWord(currentWord)}

        fun getNumAcquired(bool:Boolean):Int {return catDao.getNumAcquired(bool)}

        suspend fun wordsForTest(): List<Word> {return catDao.wordsForTest()}

        // methods not used for the moment

    }

