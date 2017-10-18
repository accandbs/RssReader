package es.bancosantander.rssreader.arch

import android.app.Application
import es.bancosantander.rssreader.BuildConfig
import es.bancosantander.rssreader.di.ApplicationComponent
import es.bancosantander.rssreader.di.DaggerApplicationComponent
import timber.log.Timber

class AndroidApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}
