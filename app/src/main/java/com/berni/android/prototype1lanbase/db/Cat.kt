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

    @ColumnInfo(name = "catName")
    val catName: String,
    @ColumnInfo(name = "catDate")
    val catDate: String,
   // @ColumnInfo(name = "catNum")
  //  val catNum: String?,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "catId")
    val catId: Int = 0

):Serializable

@Entity
data class Word  (

    // word id made out of catName + wordName . Function in Tools.kt
    @PrimaryKey(autoGenerate = false)
    val wordId: String,
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
    val date:String,
    @ColumnInfo(name  = "catParent")
    val catParent: Int? = 0,
    @ColumnInfo(name = "acquired")
    val acquired:Boolean  = false,
    @ColumnInfo(name = "test")
    var test : Boolean = true,
    @ColumnInfo(name ="lvl" )
    val lvl:Int = 0,
    @ColumnInfo(name  ="lastOK")
    val lastOk: Int = 10


):Serializable
    /*:Serializable{
    @PrimaryKey(autoGenerate = true)
    var wordId: Int = 0}

    @Entity(primaryKeys = ["catId","wordId"])
    data class CatWordCrossRef (

       val catId:Int,
       val wordId: Int)


         */







