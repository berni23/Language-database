package com.berni.android.prototype1lanbase.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CatDao {

    //insert
    @Insert(onConflict = OnConflictStrategy.IGNORE )
    suspend fun addCat(cat: Cat)

    @Insert(onConflict = OnConflictStrategy.IGNORE )
    suspend fun addWord(word: Word )

    //get all categories

    @Query( "SELECT* FROM Cat ORDER BY catName DESC")
    fun getAllCats() : LiveData<List<Cat>>

    //get all words

    @Query("SELECT* FROM Word ORDER BY wordId DESC")
    fun getAllWords() : MutableList<Word>

    //get all words using live data

    @Query("SELECT* FROM Word ORDER BY wordId DESC")
    fun getAllWordsLive () : LiveData<List<Word>>

    //get from a given category, ordering by first added

    @Query("SELECT* FROM Word WHERE catParent LIKE :category")
    fun wordsInCat(category: Int) : LiveData<List<Word>>

    @Transaction
    @Query("SELECT * FROM Cat")
    fun catsWithWords() : LiveData<List<CatWords>>

    @Transaction
    @Query("SELECT * FROM Cat")
    fun catsNwords() : List<CatWords>

    //check if the catname has already been used or not

    @Query("SELECT* FROM Cat WHERE catName=:newName" )
    fun validCatName(newName: String?) : List<Cat>

    //  check if wordId has already been used or not

    @Query("SELECT* FROM Word WHERE wordName=:newName AND catParent=:cat" )
    fun validWordId(cat: String?,newName: String?): List<Word>

    //update the whole word object
    @Update
    fun updateWord(word: Word)
    //update just the category name, keeping the rest of the parameters the same
    @Query("UPDATE Cat SET catName=:newName WHERE catName = :oldName")
    fun updateCat(oldName:String?,newName:String?)

    // delete category

    @Delete
    fun deleteCat(cat: Cat)

    //delete a given word

    @Delete
    fun deleteWord(word: Word)

    // delete all  words from a given category

    @Query("DELETE FROM Word WHERE catParent LIKE :category")
    fun deleteWordsInCat(category: Int?)

    //---------------------------------------------------------------------
    //queries for the tests
    //-----------------------------------------------------------------------

    @Query("SELECT * FROM Word WHERE test=1" )
    fun wordsForTest(): List<Word>

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------

}
