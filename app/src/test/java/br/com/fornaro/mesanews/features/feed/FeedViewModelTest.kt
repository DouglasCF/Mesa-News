package br.com.fornaro.mesanews.features.feed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.fornaro.mesanews.data.repository.NewsRepository
import br.com.fornaro.mesanews.domain.enums.ErrorType
import br.com.fornaro.mesanews.domain.models.News
import br.com.fornaro.mesanews.domain.usecase.UpdateFavoriteUseCase
import br.com.fornaro.mesanews.tools.BaseCoroutinesTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class FeedViewModelTest : BaseCoroutinesTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: FeedViewModel

    @MockK
    private lateinit var newsRepository: NewsRepository

    @MockK
    private lateinit var updateFavoriteUseCase: UpdateFavoriteUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        viewModel = FeedViewModel(
            newsRepository = newsRepository,
            updateFavoriteUseCase = updateFavoriteUseCase
        )
    }

    @Test
    fun `should load feed successfully`() {
        val highlights = mockk<List<News>>()
        val news = mockk<List<News>>()
        val data = FeedState.Success(
            highlights = highlights,
            news = news
        )

        coEvery { newsRepository.getHighlightsNews() } returns highlights
        coEvery { newsRepository.getNews() } returns news

        viewModel.getNews()

        assertEquals(data, viewModel.state.value)
    }

    @Test
    fun `should not load feed when an error happens`() {
        coEvery { newsRepository.getHighlightsNews() } throws Exception()

        viewModel.getNews()

        assertEquals(FeedState.Error(ErrorType.GENERIC_ERROR), viewModel.state.value)
    }
}