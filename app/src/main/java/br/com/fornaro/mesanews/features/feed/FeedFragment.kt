package br.com.fornaro.mesanews.features.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.fornaro.mesanews.databinding.FragmentFeedBinding
import org.koin.android.viewmodel.ext.android.viewModel

class FeedFragment : Fragment() {

    private val viewModel: FeedViewModel by viewModel()

    private val highlightsAdapter by lazy(LazyThreadSafetyMode.NONE) {
        HighlightsAdapter(viewModel) {
            val direction = FeedFragmentDirections.newsDetailFragment(it)
            findNavController().navigate(direction)
        }
    }

    private val newsAdapter by lazy(LazyThreadSafetyMode.NONE) {
        NewsAdapter(viewModel) {
            val direction = FeedFragmentDirections.newsDetailFragment(it)
            findNavController().navigate(direction)
        }
    }

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentFeedBinding
        .inflate(inflater)
        .apply {
            _binding = this
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHighlightsRecyclerView()
        setupNewsRecyclerView()
        setupViewModel()
        setupError()
    }

    private fun setupHighlightsRecyclerView() = with(binding.highlightsRecycler) {
        adapter = highlightsAdapter
    }

    private fun setupNewsRecyclerView() = with(binding.newsRecycler) {
        adapter = newsAdapter
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    private fun setupViewModel() = with(viewModel) {
        state.observe(viewLifecycleOwner, ::handleState)
        getNews()
    }

    private fun setupError() = with(binding.error.tryAgainButton) {
        setOnClickListener { viewModel.getNews() }
    }

    private fun handleState(state: FeedState) {
        handleLoading(false)
        handleError(false)
        when (state) {
            is FeedState.Loading -> handleLoading(true)
            is FeedState.Error -> handleError(true)
            is FeedState.Success -> handleSuccess(state)
        }
    }

    private fun handleLoading(loading: Boolean) {
        binding.container.isVisible = !loading
        binding.toolbar.isVisible = !loading
        binding.loading.isVisible = loading
    }

    private fun handleError(error: Boolean) {
        binding.container.isVisible = !error
        binding.toolbar.isVisible = !error
        binding.error.root.isVisible = error
    }

    private fun handleSuccess(data: FeedState.Success) {
        highlightsAdapter.data = data.highlights
        newsAdapter.data = data.news
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}