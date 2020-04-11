package com.berni.android.prototype1lanbase.db

import androidx.room.*

data class CatWords(

    @Embedded
    val cat: Cat,

    @Relation(
        parentColumn = "catId",
        entityColumn = "wordId")

    val words: List<Word>

)

@Entity
data class Cat(

    @PrimaryKey val catId: Int,

    val catName: String,
    val catDate: String

)

@Entity
data class Word (

    @PrimaryKey val wordId: Int,

    val catParent: Int?,
    val wordName: String,
    val trans1: String,
    val ex1: String?


)

//@Entity(primaryKeys = ["catId","wordId"])
//data class CatWordCrossRef (

 //   val catId:Int,
//    val wordId: Int)









