package es.bancosantander.rssreader.data.domain

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import es.bancosantander.rssreader.data.domain.db.FeedDb
import es.bancosantander.rssreader.data.domain.db.ItemsDao
import es.bancosantander.rssreader.data.repository.ErrorLiveData
import es.bancosantander.rssreader.data.repository.FeedRepository
import es.bancosantander.rssreader.data.repository.RefreshUseCase
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class DomainModule() {

    @Singleton
    @Provides
    fun provideFeedDb(application: Context): FeedDb {
        return Room.databaseBuilder(application, FeedDb::class.java, "feed.db")
                .allowMainThreadQueries()
                .build()
    }

    @Singleton
    @Provides
    fun provideItemsDao(db: FeedDb): ItemsDao {
        return db.itemsDao()
    }

    @Singleton
    @Provides
    fun provideExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    @Singleton
    @Provides
    fun provideRepository(itemsDao: ItemsDao,
                          refreshUseCase: RefreshUseCase,
                          errorData: ErrorLiveData): FeedRepository {
        return FeedRepository(itemsDao, refreshUseCase, errorData)
    }
}
