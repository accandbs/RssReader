package es.bancosantander.rssreader.data.repository

import android.app.Application
import android.content.Context
import es.bancosantander.rssreader.arch.Callback
import es.bancosantander.rssreader.data.api.services.FeedService
import es.bancosantander.rssreader.data.domain.db.ItemsDao
import es.bancosantander.rssreader.data.domain.mapper.ItemsMapper
import okhttp3.OkHttpClient
import org.xmlpull.v1.XmlPullParserException
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.net.UnknownHostException
import java.util.concurrent.Executor
import javax.inject.Inject
import es.bancosantander.rssreader.ui.settings.SettingsActivity
import android.preference.PreferenceManager
import android.content.SharedPreferences
import es.bancosantander.rssreader.arch.AndroidApplication
import retrofit2.Retrofit


class RefreshUseCase
@Inject
constructor(
        val client: OkHttpClient,
        val itemsDao: ItemsDao,
        val executor: Executor,
        val itemsMapper: ItemsMapper,
        val appContext: Context


) {

    fun refreshItems( callback: Callback<NetworkError>) {
        executor.execute {
            try {
                val sharedPref = PreferenceManager.getDefaultSharedPreferences(appContext)
                val syncConnPref = sharedPref.getString("pref_sync", "")

                val feedService = Retrofit.Builder()
                        .client(client)
                        .addConverterFactory(SimpleXmlConverterFactory.create())
                        .baseUrl("https://www.santander.com/")
                        .build()
                        .create(FeedService::class.java)

                val response = feedService.getFeed(syncConnPref).execute()
                val feed = response.body()
                val items = itemsMapper.map(feed)
                itemsDao.insert(items)
                callback(NetworkError.SUCCESS)
            } catch (e: Throwable) {
                when (e) {
                    is UnknownHostException -> callback(NetworkError.DISCONNECTED)
                    is IllegalArgumentException -> callback(NetworkError.BAD_URL)
                    is XmlPullParserException -> callback(NetworkError.NOT_A_FEED)
                    else -> callback(NetworkError.UNKNOWN)
                }
            }
        }
    }
}