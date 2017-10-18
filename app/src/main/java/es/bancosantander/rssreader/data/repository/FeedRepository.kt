package es.bancosantander.rssreader.data.repository

import android.arch.lifecycle.LiveData
import es.bancosantander.rssreader.data.domain.db.ItemsDao
import es.bancosantander.rssreader.data.repository.entities.Item
import es.bancosantander.rssreader.data.repository.entities.RSSItemUiModel.Companion.READ

class FeedRepository
constructor(
        val itemsDao: ItemsDao,
        val useCase: RefreshUseCase,
        val errorData: ErrorLiveData

) {


    fun getItems() = itemsDao.getAll()

    fun getItem(url:String) = itemsDao.getItem(url)

    fun getErrors(): LiveData<NetworkError> = errorData


    fun refreshItems() {
        useCase.refreshItems({ errorData.setNetworkError(it) })
    }

    fun markAsRead(item: Item) {
        item.read = READ
        itemsDao.updateItem(item)
    }


}