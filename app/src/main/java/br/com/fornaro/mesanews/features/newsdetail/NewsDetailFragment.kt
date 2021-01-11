package br.com.fornaro.mesanews.features.newsdetail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.fornaro.mesanews.R
import br.com.fornaro.mesanews.databinding.FragmentNewsDetailBinding
import br.com.fornaro.mesanews.domain.models.News
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class NewsDetailFragment : Fragment() {

    private val args: NewsDetailFragmentArgs by navArgs()

    private val viewModel: NewsDetailViewModel by viewModel { parametersOf(args.news) }

    private lateinit var favoriteIcon: MenuItem

    private var _binding: FragmentNewsDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentNewsDetailBinding
        .inflate(inflater)
        .apply {
            _binding = this
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWebView()
        setupToolbar()
        setupViewModel()
    }

    private fun setupWebView() = with(binding.webView) {
        settings.javaScriptEnabled = true
        webViewClient = WebViewClient()
        loadUrl(args.news.url)
    }

    private fun setupToolbar() = with(binding.toolbar) {
        favoriteIcon = menu.findItem(R.id.news_detail_favorite)
        setNavigationOnClickListener { findNavController().navigateUp() }
        setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.news_detail_favorite -> viewModel.favoriteNews()
                R.id.news_detail_share -> viewModel.share()
            }
            true
        }
    }

    private fun setupViewModel() = with(viewModel) {
        state.observe(viewLifecycleOwner, ::handleState)
        checkFavorite()
    }

    private fun handleState(state: NewsDetailState) {
        when (state) {
            is NewsDetailState.Success -> handleSuccess(state)
            is NewsDetailState.Share -> handleShare(state.news)
        }
    }

    private fun handleSuccess(state: NewsDetailState.Success) {
        if (state.isFavorite) favoriteIcon.setIcon(R.drawable.ic_bookmark)
        else favoriteIcon.setIcon(R.drawable.ic_bookmark_border)
    }

    private fun handleShare(news: News) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, getString(R.string.news_detail_share_text, news.url))
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}