package com.berni.android.prototype1lanbase.db


//@Database(entities = arrayOf(Nota::class),version = 1)
class LanDataBase private constructor() {

        companion object {

            @Volatile

            private var instance: LanDataBase? = null

            fun getInstance() =

                synchronized(this) { instance ?: LanDataBase().also { instance = it } }
        }

    }
