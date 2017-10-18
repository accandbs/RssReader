package es.bancosantander.rssreader.ui.home

import es.bancosantander.rssreader.data.repository.entities.RSSItemUiModel

interface MainActivityUserInterface {

    fun initialize(delegate: Delegate, viewModel: MainViewModel)

    interface Delegate {

        fun onRefresh()

        fun onClick(channel: RSSItemUiModel)
    }
}
