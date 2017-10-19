package es.bancosantander.rssreader.data.api.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
class Feed(
        @field:Element(name = "channel")
        var channel: FeedChannel? = null,

        var articles: List<JSONFeedItem>? = null ,

        var url: String? = null
)