package br.com.fornaro.mesanews.data.source.local

import br.com.fornaro.mesanews.data.source.local.database.daos.NewsDao
import br.com.fornaro.mesanews.data.source.local.database.entities.NewsEntity
import br.com.fornaro.mesanews.domain.exceptions.UserNotLoggedInException

class NewsLocalDataSource(
    private val authenticationLocalDataSource: AuthenticationLocalDataSource,
    private val newsDao: NewsDao
) {
    suspend fun isFavorite(title: String) =
        authenticationLocalDataSource.email?.let { newsDao.selectFavorite(it, title) }
            ?: false

    suspend fun updateFavorite(title: String, isFavorite: Boolean) {
        authenticationLocalDataSource.email?.let { user ->
            newsDao.selectNews(user, title)
                ?.let { newsDao.updateFavorite(user, title, isFavorite) }
                ?: newsDao.insertNews(
                    NewsEntity(
                        user = user,
                        title = title,
                        isFavorite = isFavorite
                    )
                )
        } ?: throw UserNotLoggedInException()
    }
}