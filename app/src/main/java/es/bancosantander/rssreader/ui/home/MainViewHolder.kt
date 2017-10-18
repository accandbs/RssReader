package es.bancosantander.rssreader.ui.home

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import es.bancosantander.rssreader.R
import es.bancosantander.rssreader.arch.Callback
import es.bancosantander.rssreader.data.repository.entities.RSSItemUiModel
import android.graphics.drawable.Drawable



class MainViewHolder(
        itemView: ViewGroup
) : RecyclerView.ViewHolder(itemView) {

    @BindView(R.id.desc)
    lateinit var desc: TextView
    @BindView(R.id.title)
    lateinit var title: TextView

    private lateinit var item: RSSItemUiModel
    private var clickListener: Callback<Int>? = null

    init {
        ButterKnife.bind(this, itemView)
    }

    fun bind(item: RSSItemUiModel, clickListener: Callback<Int>?) {
        this.item = item
        this.clickListener = clickListener
        configureView()
    }

    private fun configureView() {
        title.text = item.title
        desc.text = Html.fromHtml(item.description)

    }

    @OnClick(R.id.detail_row)
    internal fun onRowClick() {
        clickListener?.invoke(adapterPosition)
    }

}
