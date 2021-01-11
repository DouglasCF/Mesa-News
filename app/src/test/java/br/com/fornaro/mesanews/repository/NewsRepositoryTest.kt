package br.com.fornaro.mesanews.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.fornaro.mesanews.data.repository.AuthenticationRepository
import br.com.fornaro.mesanews.data.repository.NewsRepository
import br.com.fornaro.mesanews.data.source.local.NewsLocalDataSource
import br.com.fornaro.mesanews.data.source.local.cache.FilterCache
import br.com.fornaro.mesanews.data.source.remote.NewsRemoteDataSource
import br.com.fornaro.mesanews.domain.models.News
import br.com.fornaro.mesanews.tools.UnitTestDispatcherMap
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

@ExperimentalCoroutinesApi
class NewsRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var authenticationRepository: AuthenticationRepository

    @RelaxedMockK
    private lateinit var newsRemoteDataSource: NewsRemoteDataSource

    @RelaxedMockK
    private lateinit var newsLocalDataSource: NewsLocalDataSource

    @MockK
    private lateinit var cache: FilterCache

    private lateinit var repository: NewsRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        repository = NewsRepository(
            authenticationRepository = authenticationRepository,
            newsRemoteDataSource = newsRemoteDataSource,
            newsLocalDataSource = newsLocalDataSource,
            cache = cache,
            dispatcherMap = UnitTestDispatcherMap
        )
    }

    @Test
    fun `should load highlights news successfully`() = runBlockingTest {
        val token = "token"
        val highlightsNews = provideNewsMock()

        coEvery { authenticationRepository.getToken() } returns token
        coEvery { newsRemoteDataSource.fetchHighlights(token) } returns flow { highlightsNews }
        coEvery { newsLocalDataSource.isFavorite("title") } returns false

        repository.getHighlightsNews()

        coVerify { newsRemoteDataSource.fetchHighlights(token) }
    }

    @Test
    fun `should load news successfully`() = runBlockingTest {
        val token = "token"
        val news = provideNewsMock()

        coEvery { authenticationRepository.getToken() } returns token
        coEvery { newsRemoteDataSource.fetchNews(token) } returns flow { news }
        coEvery { newsLocalDataSource.isFavorite("title") } returns false

        repository.getNews()

        coVerify { newsRemoteDataSource.fetchNews(token) }
    }
}

fun provideNewsMock() = listOf(
    News(
        title = "title",
        description = "description",
        content = "content",
        author = "author",
        publishedAt = Calendar.getInstance(),
        isHighlight = true,
        url = "url",
        imageUrl = "image"
    )
)