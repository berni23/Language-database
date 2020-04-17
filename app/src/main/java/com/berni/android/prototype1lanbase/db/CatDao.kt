package com.berni.android.prototype1lanbase.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface CatDao {


   //TODO  Emit  toast 'on conflict'

    @Insert(onConflict = OnConflictStrategy.IGNORE )
    suspend fun addCat(cat: Cat)

    @Insert(onConflict = OnConflictStrategy.IGNORE )

    suspend fun addWord(word: Word )

    @Query( "SELECT* FROM Cat ORDER BY catName DESC")
    fun getAllCats() : LiveData<List<Cat>>

    @Query("SELECT* FROM Word ORDER BY wordId DESC")
    fun getAllWords () : LiveData<List<Word>>

}
