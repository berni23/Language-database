package com.berni.android.prototype1lanbase

import android.app.Application
import android.content.Context
import android.media.CamcorderProfile.get
import androidx.fragment.app.Fragment
import com.berni.android.prototype1lanbase.db.Cat
import com.berni.android.prototype1lanbase.db.LanDataBase
import com.berni.android.prototype1lanbase.db.Repository
import com.berni.android.prototype1lanbase.db.Word
import com.berni.android.prototype1lanbase.ui.BaseFragment
import com.berni.android.prototype1lanbase.ui.FirstFragment
import com.berni.android.prototype1lanbase.ui.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.bindings.KodeinBinding
import org.kodein.di.generic.*

class LanApplication() : Application(), KodeinAware {

   // lateinit var context: Context

    infix fun Int(int: Int){

    }
    override val kodein = Kodein.lazy {

        import(androidXModule(this@LanApplication))

        bind() from singleton {LanDataBase(instance()) }
        bind() from singleton {instance<LanDataBase>().catDao()}
        bind<Repository>() with singleton {Repository(instance(),instance(),instance())}
        bind() from provider  {ViewModelFactory(instance()) }
        bind() from singleton {String()}

        bind<Cat>() with singleton {Cat(instance<String>(),instance<String>(),instance<String>())}

        bind<Word>() with singleton { Word(instance(),instance(),instance(),instance(),instance(),instance(),instance(),instance()) }



      //  bind<Fragment>() with singleton {Fragment()}

      //  No binding found for bind<String>() with ?<FirstFragment>().? { ? }

       // org.kodein.di.Kodein$NotFoundException: No binding found for bind<Cat>() with ?<FirstFragment>().? { ? }

    }

    /**override fun onCreate() {
        super.onCreate()

        this.context = getApplicationContext()

     }**/
}





