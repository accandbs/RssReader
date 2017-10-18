package es.bancosantander.rssreader.ui.home

import es.bancosantander.rssreader.data.repository.entities.RSSItemUiModel
import es.bancosantander.rssreader.routing.Navigator
import javax.inject.Inject

class MainActivityPresenter
@Inject
constructor(
        val navigator: Navigator
) {

    private var decorator: MainActivityUserInterface? = null
    private lateinit var viewModel: MainViewModel

    private val delegate = object : MainActivityUserInterface.Delegate {

        override fun onRefresh() = viewModel.refreshItems()

        override fun onClick(channel: RSSItemUiModel) = navigator.navigateToDetail(channel)
    }

    fun initialize(decorator: MainActivityUserInterface, viewModel: MainViewModel) {
        this.decorator = decorator
        this.viewModel = viewModel
        this.decorator?.initialize(delegate, viewModel)
    }

    fun dispose() {
        this.decorator = null
    }
}
