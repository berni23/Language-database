package com.berni.android.prototype1lanbase.db

import android.content.Context
import androidx.room.*

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Word::class, Cat::class],version = 2, exportSchema = false)
abstract class LanDataBase : RoomDatabase() {

    abstract fun catDao(): CatDao

    companion object {

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

                // Create the new table
                database.execSQL("CREATE TABLE words_new(wordName TEXT NOT NULL, trans1 TEXT NOT NULL, ex1 TEXT, trans_ex1 TEXT, definition TEXT, date TEXT NOT NULL, catParent INTEGER , acquired INTEGER NOT NULL, acquiredDate TEXT, wordId INTEGER PRIMARY KEY NOT NULL,lvl INTEGER NOT NULL, test INTEGER NOT NULL)")
                // Copy the data
                database.execSQL("INSERT INTO words_new(wordName,trans1,ex1,trans_ex1,definition,date,catParent,acquired,acquiredDate,wordId,lvl,test) SELECT wordName,translation,example,translation example,definition,date,catParent,acquired,acquiredDate,wordId,lvl,test FROM Word")
                database.execSQL("DROP TABLE Word")
                database.execSQL("ALTER TABLE words_new ADD COLUMN lastOk TEXT")
               // database.execSQL("ALTER TABLE words_new ADD COLUMN lvl INTEGER")
                //database.execSQL("ALTER TABLE words_new ADD COLUMN test INTEGER NOT NULL")
                // Change the table name to the correct one
                database.execSQL("ALTER TABLE words_new RENAME TO Word")

            }
        }
        @Volatile
        private var instance: LanDataBase? = null

        //Value to ensure that no two threads are doing the same thing

        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {

            instance ?: buildDataBase(context).also {instance = it }
        }

        private fun buildDataBase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                LanDataBase::class.java,
                "Lan.db"
            ).addMigrations(MIGRATION_1_2).build()
    }
}