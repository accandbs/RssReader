package es.bancosantander.rssreader.data.api.services

import es.bancosantander.rssreader.data.api.model.Feed
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface FeedService {
    @GET
    fun getFeed(@Url feed: String): Call<Feed>
}
