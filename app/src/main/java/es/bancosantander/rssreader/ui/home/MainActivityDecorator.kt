package es.bancosantander.rssreader.ui.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.annotation.StringRes
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import es.bancosantander.rssreader.R
import es.bancosantander.rssreader.arch.isNullOrEmpty
import es.bancosantander.rssreader.base.BaseActivity
import es.bancosantander.rssreader.data.repository.FeedRepository
import es.bancosantander.rssreader.data.repository.NetworkError
import es.bancosantander.rssreader.data.repository.NetworkError.*
import es.bancosantander.rssreader.data.repository.entities.RSSItemUiModel
import es.bancosantander.rssreader.ui.TextAlertDialog
import kotlinx.android.synthetic.main.activity_main.view.*
import timber.log.Timber
import javax.inject.Inject

class MainActivityDecorator
@Inject
constructor(
        val activity: BaseActivity,
        val layoutManager: LinearLayoutManager,
        val adapter: MainAdapter,
        val dialog: TextAlertDialog
) : MainActivityUserInterface, SearchView.OnQueryTextListener, SearchView.OnCloseListener{

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.searchView)
    lateinit var searchView: SearchView
    @BindView(R.id.main_list)
    lateinit var list: RecyclerView
    @BindView(R.id.swipeRefreshLayout)
    lateinit var swipeRefresh: SwipeRefreshLayout
    @Inject lateinit var repository: FeedRepository

    private var delegate: MainActivityUserInterface.Delegate? = null

    fun bind(view: View) {
        ButterKnife.bind(this, view)
        initToolbar()
        list.layoutManager = layoutManager
        list.itemAnimator = DefaultItemAnimator()
        swipeRefresh.setOnRefreshListener({ delegate?.onRefresh() })
    }

    fun dispose() {
        delegate = null
    }

    override fun initialize(delegate: MainActivityUserInterface.Delegate, viewModel: MainViewModel) {
        this.delegate = delegate
        list.adapter = adapter
        adapter.setClickListener({ delegate.onClick(adapter.getItemAtPosition(it)) })

        viewModel.items.observe(activity, Observer<List<RSSItemUiModel>> { showItems(it) })
        viewModel.errors.observe(activity, Observer<NetworkError> { showErrors(it) })
        delegate?.onRefresh()
    }

    private fun showItems(items: List<RSSItemUiModel>?) {
        list.visibility = if (items.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE
        stopRefresh()
        adapter.refreshChannel(items)
    }

    private fun showErrors(error: NetworkError?) {
        stopRefresh()
        if (error == null) return
        when (error) {
            SUCCESS -> Unit
            DISCONNECTED -> showToast(R.string.net_error)
            BAD_URL -> showToast(R.string.url_error)
            NOT_A_FEED -> showToast(R.string.feed_error)
            UNKNOWN -> showToast(R.string.unknown_error)
        }
    }

    private fun initToolbar() {
        activity.setSupportActionBar(toolbar)
        val actionBar = activity.supportActionBar
        actionBar?.setDisplayShowTitleEnabled(true)
        actionBar?.setIcon(R.mipmap.ic_launcher)
        searchView.visibility = View.GONE
        searchView.setOnQueryTextListener(this)
        searchView.setOnCloseListener(this)
    }

    override fun showSearchView(show: Boolean) {
        searchView.searchView.visibility = if (show) View.VISIBLE else View.GONE

    }

    override fun searchViewShowed(): Boolean {
        var showed : Boolean = false
        if(searchView.visibility.equals(View.GONE))
            showed = false
        else
            showed = true
        return showed;
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {

        return false
    }

    override fun onQueryTextChange(p0: String?): Boolean {

        var rsslist : LiveData<List<RSSItemUiModel>> = repository.getItemsWIthTitle(p0!!)
        rsslist.observe(activity, Observer<List<RSSItemUiModel>> {
            adapter.refreshChannel(it)

        })
        return false
    }

    override fun onClose(): Boolean {
        searchView.visibility = View.GONE
        return false
    }

    private fun showToast(@StringRes text: Int) = Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()

    private fun stopRefresh() {
        swipeRefresh.isRefreshing = false
    }
}
