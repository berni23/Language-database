package com.berni.android.prototype1lanbase.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Word::class, Cat::class],version = 8, exportSchema = false)

abstract class LanDataBase : RoomDatabase() {

    abstract fun catDao(): CatDao

        companion object {

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
                )
                    .build()
            //fun getInstance(catDao: CatDao): CatDao { return catDao }
        }
}