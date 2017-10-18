package es.bancosantander.rssreader.ui.detail

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.OnClick
import es.bancosantander.rssreader.R
import es.bancosantander.rssreader.base.BaseActivity
import es.bancosantander.rssreader.data.repository.FeedRepository
import es.bancosantander.rssreader.data.repository.entities.RSSItemUiModel
import timber.log.Timber
import javax.inject.Inject
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.webkit.WebView
import android.widget.TextView
import butterknife.BindView


class DetailActivity : BaseActivity() {
    companion object {
        val CHANNEL_KEY = "es.bancosantander.rssreader.rssitem.key"
    }
    @Inject lateinit var repository: FeedRepository

    lateinit var url : String

    @BindView(R.id.webviewDetail)
    lateinit var webview : WebView
    @BindView(R.id.titulo)
    lateinit var titulo: TextView
    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar


    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_detail)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(true)
        actionBar?.setIcon(R.mipmap.ic_launcher)

        activityComponent.inject(this)
        Timber.d(intent?.getStringExtra(CHANNEL_KEY) ?: "")
        val item = repository.getItem(intent?.getStringExtra(CHANNEL_KEY) ?: "")
        item.observe(this, Observer<List<RSSItemUiModel>> {
            Timber.d("sad " + it?.get(0)?.description)
            titulo.text = it?.get(0)?.title.toString()
            url = it?.get(0)?.link.toString()
            webview.loadDataWithBaseURL(null,it?.get(0)?.description,"text/html", "UTF-8",null)

        })
        Timber.d(item?.value?.get(0)?.description)
    }
    @OnClick(R.id.buttonVisitar)
    fun onRowClick(){
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
}
