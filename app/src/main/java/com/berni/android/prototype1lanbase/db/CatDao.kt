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
    fun getCatWithWords(): List<CatWords>

    @Insert
    suspend fun addCat(cat: Cat)

    @Insert
    suspend fun addWord(word: Word )

    @Query( "SELECT* FROM Cat ORDER BY catId DESC")
    fun getAllCats ( vararg cat: Cat)

    @Query("SELECT* FROM Word ORDER BY wordId DESC")
    fun getAllWords (vararg word: Word)

}
