package es.bancosantander.rssreader.ui.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import es.bancosantander.rssreader.R
import es.bancosantander.rssreader.arch.Callback
import es.bancosantander.rssreader.data.repository.entities.RSSItemUiModel
import javax.inject.Inject

class MainAdapter
@Inject
constructor() : RecyclerView.Adapter<MainViewHolder>() {

    private var items: List<RSSItemUiModel> = listOf()
    private var clickListener: Callback<Int>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.detail_line, parent, false) as ViewGroup
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) = holder.bind(items[position], clickListener)

    override fun getItemCount() = items.size

    fun refreshChannel(items: List<RSSItemUiModel>?) {
        items?.let {
            this.items = it
            notifyDataSetChanged()
        }
    }

    fun setClickListener(clickListener: Callback<Int>) {
        this.clickListener = clickListener
    }

    fun getItemAtPosition(position: Int) = items[position]
}

