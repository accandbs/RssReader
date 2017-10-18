package es.bancosantander.rssreader.data.domain.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import es.bancosantander.rssreader.data.domain.converter.DateConverter
import es.bancosantander.rssreader.data.repository.entities.Item

@Database(entities = arrayOf(Item::class),
        version = 1,
        exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class FeedDb : RoomDatabase() {


    abstract fun itemsDao(): ItemsDao
}
