package es.bancosantander.rssreader.data.api.model

import org.simpleframework.xml.Element
import java.util.*

class JSONFeedItem(
        var title: String ,
        var url: String,
        var publishedAt: String ,
        var description: String
)
