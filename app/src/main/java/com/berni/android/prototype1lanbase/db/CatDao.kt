package com.berni.android.prototype1lanbase.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface CatDao {

   //TODO  Emit  toast 'on conflict'

    //insert

    @Insert(onConflict = OnConflictStrategy.IGNORE )
    suspend fun addCat(cat: Cat)

    @Insert(onConflict = OnConflictStrategy.IGNORE )
    suspend fun addWord(word: Word )

    //get

    @Query( "SELECT* FROM Cat ORDER BY catName DESC")
    fun getAllCats() : LiveData<List<Cat>>

    @Query("SELECT* FROM Word ORDER BY wordId DESC")
    fun getAllWords () : LiveData<List<Word>>


    @Query("SELECT* FROM Word WHERE catParent LIKE :category")
    fun wordsInCat(category: String) : List<Word>


    @Query("SELECT* FROM Word WHERE catParent LIKE :category ORDER BY wordName")
    fun wordsInCatAlphabetic(category: String) : List<Word>

    // delete

    @Query("DELETE FROM Word WHERE catParent LIKE :category")
    fun deleteWordsInCat(category: String)

    @Delete
    fun deleteCat(cat: Cat)
}
