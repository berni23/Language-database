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

    //get all cat

    @Query( "SELECT* FROM Cat ORDER BY catName DESC")
    fun getAllCats() : LiveData<List<Cat>>

    //get all words

    @Query("SELECT* FROM Word ORDER BY wordId DESC")
    fun getAllWords () : LiveData<List<Word>>

    //get from a given category, ordering by first added

    @Query("SELECT* FROM Word WHERE catParent LIKE :category")
    fun wordsInCat(category: String) : List<Word>

    //get from a given category, ordering alphabetically

    @Query("SELECT* FROM Word WHERE catParent LIKE :category ORDER BY wordName")
    fun wordsInCatAlphabetic(category: String) : List<Word>

    //get words with example

    @Query("SELECT* FROM Word WHERE catParent LIKE :category AND example IS NOT NULL ")
    fun filterExample(category:String) :List<Word>

    // delete all  words from a given category

    @Query("DELETE FROM Word WHERE catParent LIKE :category")
    fun deleteWordsInCat(category: String)

    //delete a given category

    @Delete
    fun deleteCat(cat: Cat)
}
