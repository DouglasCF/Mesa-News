package br.com.fornaro.mesanews.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.fornaro.mesanews.data.repository.AuthenticationRepository
import br.com.fornaro.mesanews.data.repository.NewsRepository
import br.com.fornaro.mesanews.data.source.remote.NewsRemoteDataSource
import br.com.fornaro.mesanews.domain.exceptions.InvalidTokenException
import br.com.fornaro.mesanews.domain.models.News
import br.com.fornaro.mesanews.tools.UnitTestDispatcherMap
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class NewsRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var authenticationRepository: AuthenticationRepository

    @MockK
    private lateinit var newsRemoteDataSource: NewsRemoteDataSource

    private lateinit var repository: NewsRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        repository = NewsRepository(
            authenticationRepository = authenticationRepository,
            newsRemoteDataSource = newsRemoteDataSource,
            dispatcherMap = UnitTestDispatcherMap
        )
    }

    @Test(expected = InvalidTokenException::class)
    fun `should throw exception when token is null while getting highlights`() = runBlockingTest {
        every { authenticationRepository.token } returns null

        repository.getHighlightsNews()
    }

    @Test
    fun `should load highlights news successfully`() = runBlockingTest {
        val token = "token"
        val highlightsNews = mockk<List<News>>()

        every { authenticationRepository.token } returns token
        coEvery { newsRemoteDataSource.fetchHighlights(token) } returns highlightsNews

        val result = repository.getHighlightsNews()

        assertEquals(highlightsNews, result)
        coVerify { newsRemoteDataSource.fetchHighlights(token) }
    }

    @Test(expected = InvalidTokenException::class)
    fun `should throw exception when token is null while getting news`() = runBlockingTest {
        every { authenticationRepository.token } returns null

        repository.getNews()
    }

    @Test
    fun `should load news successfully`() = runBlockingTest {
        val token = "token"
        val news = mockk<List<News>>()

        every { authenticationRepository.token } returns token
        coEvery { newsRemoteDataSource.fetchNews(token) } returns news

        val result = repository.getNews()

        assertEquals(news, result)
        coVerify { newsRemoteDataSource.fetchNews(token) }
    }
}