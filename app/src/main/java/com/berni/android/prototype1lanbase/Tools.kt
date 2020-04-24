package com.berni.android.prototype1lanbase

import androidx.room.Query
import com.berni.android.prototype1lanbase.db.Word


// generate  word id, so words have to be unique within the same category but
// can be duplicated if in different categories .

fun wordId( catName:String, wordName: String) : String{

    return  catName.plus("_").plus(wordName)
}





// tools for filtering and sorting Words List Fragment


fun sortAlpha(word: List<Word>) {





}