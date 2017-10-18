package es.bancosantander.rssreader.data.repository.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "items")
data class Item(
        @PrimaryKey
        var link: String,
        var pubDate: Date,
        var title: String,
        var description: String,
        var read: Int
)
data class RSSItemUiModel(
        @ColumnInfo
        var link: String,
        @ColumnInfo
        var title: String,
        @ColumnInfo
        var description: String
)
{
    companion object {
        val READ = 1
    }
}

