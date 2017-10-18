package es.bancosantander.rssreader.routing

import android.content.Intent
import es.bancosantander.rssreader.base.BaseActivity
import es.bancosantander.rssreader.data.repository.entities.RSSItemUiModel
import es.bancosantander.rssreader.ui.detail.DetailActivity
import javax.inject.Inject

class Navigator
@Inject
constructor(
        val activity: BaseActivity
) {
    fun navigateToDetail(item: RSSItemUiModel) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.CHANNEL_KEY, item.link)
        activity.startActivity(intent)
    }
}