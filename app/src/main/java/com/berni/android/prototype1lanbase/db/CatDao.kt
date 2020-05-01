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

    //get all categories

    @Query( "SELECT* FROM Cat ORDER BY catName DESC")
    fun getAllCats() : LiveData<List<Cat>>

    //get all words

    @Query("SELECT* FROM Word ORDER BY wordId DESC")
    fun getAllWords() : List<Word>

    @Query("SELECT* FROM Cat WHERE catName=:newName" )

    fun validCatName(newName: String?) : List<Cat>

    @Query("SELECT* FROM Word WHERE wordId=:newId" )

    fun validWordId(newId: String?): List<Word>

    //update the whole word object

    @Update
    fun updateWord(word: Word)

    //update just the category name, keeping the rest of the parameters the same

    @Query("UPDATE Cat SET catName=:newName WHERE catName = :oldName")

    fun updateCat(oldName:String?,newName:String?)

    @Query("SELECT* FROM Cat ORDER BY catId DESC LIMIT 1 ")
    fun maxNum() : Cat?

    //get from a given category, ordering by first added

    @Query("SELECT* FROM Word ORDER BY wordId DESC")
    fun getAllWordsLive () : LiveData<List<Word>>

    //get from a given category, ordering by first adde

    @Query("SELECT* FROM Word WHERE catParent LIKE :category")
    fun wordsInCat(category: Int) : LiveData<List<Word>>

    //get from a given category, ordering alphabetically

    @Query("SELECT* FROM Word WHERE catParent LIKE :category ORDER BY wordName")
    fun wordsInCatAlphabetic(category: String) : LiveData<List<Word>>

    //get words with example

    @Query("SELECT* FROM Word WHERE catParent LIKE :category AND example IS NOT NULL ")
    fun filterExample(category:String) :LiveData<List<Word>>

    //get words with no example

    @Query("SELECT* FROM Word WHERE catParent LIKE :category AND example IS NULL ")
    fun filterNoExample(category:String) : LiveData<List<Word>>

    // delete all  words from a given category

    @Query("DELETE FROM Word WHERE catParent LIKE :category")
    fun deleteWordsInCat(category: Int?)

    //delete a given category

    @Delete
    fun deleteCat(cat: Cat)

    //delete a given word

    @Delete
    fun deleteWord(word: Word)
}
