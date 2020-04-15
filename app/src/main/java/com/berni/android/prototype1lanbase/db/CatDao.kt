package com.berni.android.prototype1lanbase.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction


@Dao
interface CatDao {

    @Transaction
    @Query("SELECT * FROM Cat")
    fun getCatWithWords(): LiveData<List<CatWords>>

    @Insert
    suspend fun addCat(cat: Cat)

    @Insert
    suspend fun addWord(word: Word )

    @Query( "SELECT* FROM Cat ORDER BY catName DESC")
    fun getAllCats() : LiveData<List<Cat>>

    @Query("SELECT* FROM Word ORDER BY wordId DESC")
    fun getAllWords () : LiveData<List<Word>>

}

