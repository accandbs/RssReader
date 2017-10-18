package es.bancosantander.rssreader.ui.home

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import es.bancosantander.rssreader.arch.AndroidApplication
import es.bancosantander.rssreader.data.repository.FeedRepository
import es.bancosantander.rssreader.data.repository.NetworkError
import es.bancosantander.rssreader.data.repository.entities.RSSItemUiModel
import javax.inject.Inject

data class MainViewModel
constructor(
        val app: Application
) : AndroidViewModel(app) {

    @Inject lateinit var repository: FeedRepository

    var items: LiveData<List<RSSItemUiModel>>
    val errors: LiveData<NetworkError>

    init {
        (app as AndroidApplication).applicationComponent.inject(this)
        items = repository.getItems()
        errors = repository.getErrors()
    }

    fun refreshItems() {
        repository.refreshItems()
    }

}
