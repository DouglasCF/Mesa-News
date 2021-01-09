package br.com.fornaro.mesanews.data.source.remote.mappers

import br.com.fornaro.mesanews.data.extensions.toCalendar
import br.com.fornaro.mesanews.data.source.remote.response.NewsResponse
import br.com.fornaro.mesanews.domain.models.News

object NewsRemoteMapper : Mapper<NewsResponse, List<News>> {
    override fun map(input: NewsResponse) = input.data.map {
        News(
            title = it.title,
            description = it.description,
            content = it.content,
            author = it.author,
            publishedAt = it.publishedAt.toCalendar(),
            isHighlight = it.highlight,
            url = it.url,
            imageUrl = it.imageUrl
        )
    }
}