package com.berni.android.prototype1lanbase



fun wordId( catName:String, wordName: String) : String{


    return  catName.plus("_").plus(wordName)
}