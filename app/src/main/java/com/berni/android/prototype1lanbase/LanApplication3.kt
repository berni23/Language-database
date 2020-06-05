package com.berni.android.prototype1lanbase

import android.app.Application
import com.berni.android.prototype1lanbase.db.*
import com.berni.android.prototype1lanbase.ui.viewmodel.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*

class LanApplication3() : Application(), KodeinAware {

   // lateinit var context: Context

    override val kodein = Kodein.lazy {

        import(androidXModule(this@LanApplication3))

        bind() from singleton {Int}
        bind() from singleton {LanDataBase(instance()) }
        bind() from singleton {instance<LanDataBase>().catDao()}
        bind<Repository>() with singleton {RepositoryImpl(instance(),instance(),instance()) }
        bind() from provider  {
            ViewModelFactory(
                instance()
            )
        }
        bind() from singleton {String()}
        bind() from provider  {Cat(instance<String>(),instance<String>())}
        bind() from provider  {Word(instance<String>(),instance<String>(),instance<String>(),instance<String>(),instance<String>(),instance<String>()) }
        
      //  bind<Fragment>() with singleton {Fragment()}

      //  No binding found for bind<String>() with ?<FirstFragment>().? { ? }

       // org.kodein.di.Kodein$NotFoundException: No binding found for bind<Cat>() with ?<FirstFragment>().? { ? }

    }

    /**override fun onCreate() {
        super.onCreate()

        this.context = getApplicationContext()

     }**/
}





