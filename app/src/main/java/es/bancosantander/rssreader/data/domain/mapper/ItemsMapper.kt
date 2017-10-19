package es.bancosantander.rssreader.data.domain.mapper

import es.bancosantander.rssreader.data.api.model.Feed
import es.bancosantander.rssreader.data.repository.entities.Item
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ItemsMapper
@Inject
constructor() : Mapper<List<Item>, Feed> {
    override fun mapJSON(input: Feed): List<Item> {
        val items = mutableListOf<Item>()
        val feedItems = input.articles ?: emptyList()
        for (feedItem in feedItems) {
            items.add(
                    Item(
                            pubDate = formatJson.parse(feedItem.publishedAt ?: ""),
                            link = feedItem.url ?: "",
                            title = feedItem.title ?: "",
                            description = feedItem.description ?: "",
                            read = 0
                    )
            )
        }
        return items
    }

    companion object {
        //Mon, 05 Jun 2017 07:41:40 +0000
        //val format = SimpleDateFormat("dd/mm/yyyy")
        val formatXml = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH)
        val formatJson = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)

    }

    override fun map(input: Feed): List<Item> {
        val items = mutableListOf<Item>()
        val feedItems = input.channel?.feedItems ?: emptyList()
        for (feedItem in feedItems) {
            items.add(
                    Item(
                            pubDate = formatXml.parse(feedItem.pubDate ?: ""),
                            title = feedItem.title ?: "",
                            link = feedItem.link ?: "",
                            description = feedItem.description ?: "",
                            read = 0
                    )
            )
        }
        return items
    }
}