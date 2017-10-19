package es.bancosantander.rssreader.data.domain.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import es.bancosantander.rssreader.data.repository.entities.Item
import es.bancosantander.rssreader.data.repository.entities.RSSItemUiModel

@Dao
abstract class ItemsDao {

    fun insert(items: List<Item>) {
        for (item in items) {
            insert(item)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(item: Item)

    @Update
    abstract fun updateItem(item: Item)

    @Query("SELECT * FROM items where link = :link")
    abstract fun getItem(link: String): LiveData<List<RSSItemUiModel>>

    @Query("SELECT * FROM items ORDER BY pubDate DESC")
    abstract fun getAll(): LiveData<List<RSSItemUiModel>>

    @Query("SELECT * FROM items where title LIKE :search")
    abstract fun getItemsWitnTitle(search: String): LiveData<List<RSSItemUiModel>>
}
