package com.berni.android.prototype1lanbase.db

import androidx.room.*
import android.os.Parcelable
import java.io.Serializable
import kotlin.properties.Delegates

/**data class CatWords(

    @Embedded
    val cat: Cat,
    @Relation(
        parentColumn = "catName",
        entityColumn = "catParent")

    val words: List<Word>
)**/

@Entity
data class Cat(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "catName")
    val catName: String,
    @ColumnInfo(name = "catDate")
    val catDate: String,
    @ColumnInfo(name = "catNum")
    val catNum: String?

):Serializable


@Entity
data class Word  (

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
    @ColumnInfo(name = "translation example")
    val trans_ex1: String?,
    @ColumnInfo(name = "definition")
    val definition: String?,
    @ColumnInfo(name = "date")
    val date:String

):Serializable
    /*:Serializable{
    @PrimaryKey(autoGenerate = true)
    var wordId: Int = 0}

    @Entity(primaryKeys = ["catId","wordId"])
    data class CatWordCrossRef (

       val catId:Int,
       val wordId: Int)


         */







