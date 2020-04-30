package com.berni.android.prototype1lanbase.db

    class RepositoryImpl(

        override  val catDao: CatDao,
        override val cat: Cat,
        override val word: Word

    ):Repository {

        companion object {

            @Volatile private var instance: Repository? = null

            fun getInstance(catDao: CatDao, cat: Cat, word: Word) = instance ?: synchronized(this){instance ?:RepositoryImpl(catDao,cat=cat,word = word).also{instance =it} }
        }
    }

