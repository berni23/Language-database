package com.berni.android.prototype1lanbase

import android.app.Application
import android.content.Context
import com.berni.android.prototype1lanbase.db.Cat
import com.berni.android.prototype1lanbase.db.LanDataBase
import com.berni.android.prototype1lanbase.db.Repository
import com.berni.android.prototype1lanbase.db.Word
import com.berni.android.prototype1lanbase.ui.FirstFragment
import com.berni.android.prototype1lanbase.ui.MainViewModel
import com.berni.android.prototype1lanbase.ui.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import java.util.*

class LanApplication() : Application(), KodeinAware {


    lateinit var context: Context

    override val kodein = Kodein.lazy {


        import(androidXModule(this@LanApplication))

        bind() from singleton { Word(instance(),instance(),instance(),instance(),instance(),instance()) }
        bind() from singleton {String()}
        bind() from singleton { Cat(instance(),instance()) }
        bind() from singleton { LanDataBase(instance()) }
        bind() from singleton { instance<LanDataBase>().catDao() }
        bind() from provider { ViewModelFactory(instance()) }
        bind<Repository>() with singleton { Repository(instance(),instance(),instance()) }

      //  No binding found for bind<String>() with ?<FirstFragment>().? { ? }

       // org.kodein.di.Kodein$NotFoundException: No binding found for bind<Cat>() with ?<FirstFragment>().? { ? }

    }

    override fun onCreate() {
        super.onCreate()

        // initialize timezone library. Best place to do so in onCreate method


        //this.context = getApplicationContext()
    }


}





