import android.app.Application
import com.berni.android.prototype1lanbase.db.LanDataBase
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

           override val kodein = Kodein.lazy {

           import(androidXModule(this@LanApplication))

            bind() from singleton { LanDataBase(instance()) }
            bind() from singleton { instance <LanDataBase>().catDao() }

          //  bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(),instance()) }
            bind() from provider{ ViewModelFactory(instance()) }

        }

        override fun onCreate() {
            super.onCreate()

            // initialize timezone library. Best place to do so in onCreate method

        }
    }


