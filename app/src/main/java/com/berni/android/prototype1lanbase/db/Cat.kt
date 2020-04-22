package com.berni.android.prototype1lanbase.db

import androidx.room.*

data class CatWords(

    @Embedded
    val cat: Cat,
    @Relation(
        parentColumn = "catName",
        entityColumn = "catParent")

    val words: List<Word>
)

@Entity
data class Cat(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "catName")
    val catName: String,
    @ColumnInfo(name = "catDate")
    val catDate: String
)

@Entity
data class Word (

    // word id made out of catName + wordName . Function in Tools.kt

    @PrimaryKey(autoGenerate = false)
    val wordId: String,
    @ColumnInfo(name  = "catParent")
    val catParent: String?,
    @ColumnInfo(name  = "wordName")
    val wordName: String,
    @ColumnInfo(name  = "translation")
    val trans1: String,
    @ColumnInfo(name  = "example")
    val ex1: String?,
    @ColumnInfo(name = "date")
    val date:String


)

    /*:Serializable{
    @PrimaryKey(autoGenerate = true)
    var wordId: Int = 0}

    @Entity(primaryKeys = ["catId","wordId"])
    data class CatWordCrossRef (

       val catId:Int,
       val wordId: Int)


         */







