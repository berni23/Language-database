package com.berni.android.prototype1lanbase.db

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

        suspend fun getAllWords (): MutableList<Word> {return catDao.getAllWords()}

        fun getAllCats () : LiveData<List<Cat>> {return catDao.getAllCats()}

        fun validCatName(catName: String) : List<Cat> {return catDao.validCatName(catName)}

        fun validWordId(catName: String,wordName:String): List<Word> {return catDao.validWordId(catName,wordName)}

        fun getAllWordsLive(): LiveData<List<Word>> {return catDao.getAllWordsLive()}

        fun wordsInCat(currentCatId: Int) : LiveData<List<Word>> {return catDao.wordsInCat(currentCatId)}

        fun catsWithWords() : LiveData<List<CatWords>> {return  catDao.catsWithWords()}

        fun deleteWordsInCat(currentCatId: Int)  {catDao.deleteWordsInCat(currentCatId)}

        fun deleteCat(currentCat:Cat) {catDao.deleteCat(currentCat)}

        fun deleteWord(currentWord: Word) {catDao.deleteWord(currentWord)}

        suspend fun wordsForTest(): List<Word> {return catDao.wordsForTest()}

        // methods not used for the moment

        fun wordsInCatAlphabetic(currentCatName: String) :  LiveData<List<Word>> {return catDao.wordsInCatAlphabetic(currentCatName)}

        fun filterExample(currentCatName: String) :  LiveData<List<Word>> {return catDao.filterExample(currentCatName)}

        fun filterNoExample(currentCatName:String) : LiveData<List<Word>> {return catDao.filterNoExample(currentCatName)}

    }

