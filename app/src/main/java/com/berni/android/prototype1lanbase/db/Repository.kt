package com.berni.android.prototype1lanbase.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room


class Repository(private val catDao: CatDao, val cat: Cat, val word: Word) {

        suspend fun addCat(cat: Cat)        {catDao.addCat(cat)  }

        suspend fun addWord(word: Word)     { catDao.addWord(word) }

        fun getAllCats () : LiveData<List<Cat>> {  return catDao.getAllCats() }

        fun getAllWords (): LiveData<List<Word>> {return  catDao.getAllWords() }

        suspend fun wordsInCat(currentCatName: String) : List<Word> {return catDao.wordsInCat(currentCatName)}

         fun wordsInCatAlphabetic(currentCatName: String) : List<Word> { return catDao.wordsInCatAlphabetic(currentCatName)}

        fun deleteWordsInCat(currentCatName: String)  {catDao.deleteWordsInCat(currentCatName)}

        fun deleteCat(currentCat:Cat) {catDao.deleteCat(currentCat)}



    companion object {

            @Volatile private var instance: Repository? = null

            fun getInstance(catDao: CatDao,cat: Cat,word: Word) = instance ?: synchronized(this){instance ?:Repository(catDao,cat=cat,word = word).also{instance =it} }
        }
    }

