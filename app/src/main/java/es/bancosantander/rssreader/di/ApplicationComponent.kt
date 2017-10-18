package es.bancosantander.rssreader.di

import android.content.Context
import android.content.pm.PackageManager
import dagger.Component
import es.bancosantander.rssreader.arch.ApplicationModule
import es.bancosantander.rssreader.data.api.ApiModule
import es.bancosantander.rssreader.data.domain.DomainModule
import es.bancosantander.rssreader.data.domain.db.ItemsDao
import es.bancosantander.rssreader.data.repository.FeedRepository
import es.bancosantander.rssreader.ui.home.MainViewModel
import okhttp3.OkHttpClient
import java.util.concurrent.Executor
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        ApiModule::class,
        DomainModule::class
        ))
interface ApplicationComponent {

    fun inject(into: MainViewModel)

    //fun inject(into: DetailViewModel)

    fun provideContext(): Context

    fun providePackageManager(): PackageManager

    fun provideOkHttpClient(): OkHttpClient

    fun provideItemsDao(): ItemsDao

    fun provideExecutor(): Executor

    fun provideFeedRepository(): FeedRepository

}
