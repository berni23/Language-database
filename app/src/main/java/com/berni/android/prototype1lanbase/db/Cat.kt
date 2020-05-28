package com.berni.android.prototype1lanbase.db

import androidx.room.*
//import androidx.room.migration.Migration
//import androidx.sqlite.db.SupportSQLiteDatabase
import java.io.Serializable

data class CatWords(

    @Embedded
    val cat: Cat,

    @Relation(
        parentColumn = "catId",
        entityColumn = "catParent")

    val words: List<Word>)

@Entity
data class Cat(

    @ColumnInfo(name = "catName")
    val catName: String,
    @ColumnInfo(name = "catDate")
    val catDate: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "catId")
    val catId: Int = 0

    //@ColumnInfo(name="tag")
   // var tag:String = "hi"

):Serializable

   /** { val MIGRATION_9_10 = object : Migration(9, 10) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE Song ADD COLUMN tag TEXT NOT NULL DEFAULT ''")
        }
    }
   }**/


@Entity
data class Word  (

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
    var acquired:Boolean  =false,
    var acquiredDate: String? = null,
    @ColumnInfo(name = "test")
    var test : Boolean = true,
    @ColumnInfo(name ="lvl" )
    var lvl:Int = 0,
    @ColumnInfo(name  ="lastOK")
    var lastOk: Int = 10


):Serializable {

    @PrimaryKey(autoGenerate = true)
    var wordId: Int = 0

    // Migration from 1 to 2, Room 2.1.0
}

    /*:Serializable{
    @PrimaryKey(autoGenerate = true)
    var wordId: Int = 0}

    @Entity(primaryKeys = ["catId","wordId"])
    data class CatWordCrossRef (

       val catId:Int,
       val wordId: Int)


         */







