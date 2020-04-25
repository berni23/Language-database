package com.berni.android.prototype1lanbase.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class Repository(private val catDao: CatDao, val cat: Cat, val word: Word) {

        suspend fun addCat(cat: Cat)  {catDao.addCat(cat)}

        suspend fun addWord(word: Word)  {catDao.addWord(word)}

        fun getAllCats () : LiveData<List<Cat>> {return catDao.getAllCats()}

        fun getAllWords (): LiveData<List<Word>> {return catDao.getAllWords()}

        fun wordsInCat(currentCatName: String) : LiveData<List<Word>> {return catDao.wordsInCat(currentCatName)}

        fun wordsInCatAlphabetic(currentCatName: String) :  MutableLiveData<List<Word>> {return catDao.wordsInCatAlphabetic(currentCatName)}

        fun filterExample(currentCatName: String) :  MutableLiveData<List<Word>> {return catDao.filterExample(currentCatName)}

        fun filterNoExample(currentCatName:String) : MutableLiveData<List<Word>> {return catDao.filterNoExample(currentCatName)}

        fun deleteWordsInCat(currentCatName: String)  {catDao.deleteWordsInCat(currentCatName)}

        fun deleteCat(currentCat:Cat) {catDao.deleteCat(currentCat)}



    companion object {

            @Volatile private var instance: Repository? = null

            fun getInstance(catDao: CatDao,cat: Cat,word: Word) = instance ?: synchronized(this){instance ?:Repository(catDao,cat=cat,word = word).also{instance =it} }
        }
    }

