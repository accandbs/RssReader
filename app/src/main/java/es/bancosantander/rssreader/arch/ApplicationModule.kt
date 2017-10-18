package es.bancosantander.rssreader.arch

import android.content.Context
import android.content.pm.PackageManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: AndroidApplication) {

    @Provides
    @Singleton
    internal fun provideContext(): Context {
        return this.application
    }

    @Provides
    @Singleton
    internal fun providePackageManager(): PackageManager {
        return application.packageManager
    }
}
