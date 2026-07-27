package com.berni.android.prototype1lanbase

import android.app.Application
import com.berni.android.prototype1lanbase.db.*
import com.berni.android.prototype1lanbase.ui.viewmodel.ViewModelFactory
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.*

class LanApplication3() : Application(), DIAware {

   // lateinit var context: Context

    override val di = DI.lazy {

        import(androidXModule(this@LanApplication3))

        bind<Int.Companion>() with singleton {Int}
        bind<LanDataBase>() with singleton {LanDataBase(instance()) }
        bind<CatDao>() with singleton {instance<LanDataBase>().catDao()}
        bind<Repository>() with singleton {RepositoryImpl(instance(),instance(),instance()) }
        bind<ViewModelFactory>() with provider  {
            ViewModelFactory(
                instance()
            )
        }
        bind<String>() with singleton {String()}
        bind<Cat>() with provider  {Cat(instance<String>(),instance<String>())}
        bind<Word>() with provider  {Word(instance<String>(),instance<String>(),instance<String>(),instance<String>(),instance<String>(),instance<String>()) }
        
      //  bind<Fragment>() with singleton {Fragment()}

      //  No binding found for bind<String>() with ?<FirstFragment>().? { ? }

       // org.kodein.di.Kodein$NotFoundException: No binding found for bind<Cat>() with ?<FirstFragment>().? { ? }

    }

    /**override fun onCreate() {
        super.onCreate()

        this.context = getApplicationContext()

     }**/
}





