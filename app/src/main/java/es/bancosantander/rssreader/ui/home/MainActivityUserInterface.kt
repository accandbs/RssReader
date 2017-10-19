package es.bancosantander.rssreader.ui.home

import android.arch.lifecycle.LiveData
import es.bancosantander.rssreader.data.repository.entities.RSSItemUiModel

interface MainActivityUserInterface {

    fun initialize(delegate: Delegate, viewModel: MainViewModel)

    fun showSearchView(show: Boolean)

    fun searchViewShowed(): Boolean

    interface Delegate {

        fun onRefresh()

        fun onClick(channel: RSSItemUiModel)

        fun showSearchView(show: Boolean)
    }
}
