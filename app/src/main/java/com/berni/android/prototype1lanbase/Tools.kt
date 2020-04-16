package com.berni.android.prototype1lanbase




// generate  word id, so words have to be unique within the same category but
// can be duplicated if in different categories .

fun wordId( catName:String, wordName: String) : String{


    return  catName.plus("_").plus(wordName)
}