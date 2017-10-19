package es.bancosantander.rssreader.data.repository

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
import android.util.MalformedJsonException
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import es.bancosantander.rssreader.arch.AndroidApplication
import es.bancosantander.rssreader.data.repository.entities.Item
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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
        var isJson = true
        executor.execute {
            try {
                val sharedPref = PreferenceManager.getDefaultSharedPreferences(appContext)
                val syncConnPref = sharedPref.getString("pref_sync", "http://www.xatakandroid.com/tag/feeds/rss2.xml")

                val gsonBuilder: GsonBuilder = GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz")
                var gson: Gson = gsonBuilder.create()
                var items:List<Item> = arrayListOf<Item>()

                try {
                    val feedService = Retrofit.Builder()
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            //.addConverterFactory(SimpleXmlConverterFactory.create())
                            .baseUrl("https://www.santander.com/")
                            .build()
                            .create(FeedService::class.java)

                    val response = feedService.getFeed(syncConnPref).execute()
                    val feed = response.body()
                    if (feed.articles?.size!! > 0 ) {
                        items = itemsMapper.mapJSON(feed)
                    }
                }
                catch (e: Throwable) {
                    when (e) {
                        is MalformedJsonException -> isJson = false
                        else -> isJson = false


                    }

                }
                if(!isJson) {
                    val feedService = Retrofit.Builder()
                            .client(client)
                            //.addConverterFactory(GsonConverterFactory.create(gson))
                            .addConverterFactory(SimpleXmlConverterFactory.create())
                            .baseUrl("https://www.santander.com/")
                            .build()
                            .create(FeedService::class.java)

                    val response = feedService.getFeed(syncConnPref).execute()
                    val feed = response.body()
                    if (feed.channel != null) {
                        items = itemsMapper.map(feed)
                    }
                }
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