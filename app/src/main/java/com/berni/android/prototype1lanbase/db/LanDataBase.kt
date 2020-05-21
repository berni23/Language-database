package com.berni.android.prototype1lanbase.db

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Word::class, Cat::class],version = 10, exportSchema = true)

abstract class LanDataBase : RoomDatabase() {

    abstract fun catDao(): CatDao

    //(catName, catDate, catId,tag)
        companion object {

           /** private val MIGRATION_9_10 = object : Migration(9, 10) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("""
                CREATE TABLE new_Cat (
                     catName TEXT,
                     catDate TEXT,
                     catId INTEGER PRIMARY KEY NOT NULL,
                     tag TEXT NOT NULL DEFAULT ''
                )
                """.trimIndent())
                    database.execSQL("""
                INSERT INTO new_Cat (catName, catDate, catId,tag)
                SELECT catName, catDate,catId, tag FROM Cat
                """.trimIndent())
                    database.execSQL("DROP TABLE Cat")
                    database.execSQL("ALTER TABLE new_Cat RENAME TO Cat")
                }
            }**/

          /** private val MIGRATION_9_10 = object : Migration(9, 10) {
               override fun migrate(database: SupportSQLiteDatabase) {
                   database.execSQL(
                       "ALTER TABLE Cat ADD COLUMN tag TEXT NOT NULL DEFAULT ''"
                   )

               }
           }**/

            @Volatile
            private var instance: LanDataBase? = null

            //Value to ensure that no two threads are doing the same thing

            private val LOCK = Any()
            operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {

                instance ?: buildDataBase(context).also {instance = it}
            }

                private fun buildDataBase(context: Context) =
                Room.databaseBuilder(
                    context.applicationContext,
                  LanDataBase::class.java,
                    "Lan.db"
                ) .build()

        //.addMigrations(MIGRATION_9_10).build()

        }
}