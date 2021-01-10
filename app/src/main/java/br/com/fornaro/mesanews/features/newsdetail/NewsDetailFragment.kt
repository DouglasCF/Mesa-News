package br.com.fornaro.mesanews.features.newsdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.fornaro.mesanews.databinding.FragmentNewsDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel


class NewsDetailFragment : Fragment() {

    private val args: NewsDetailFragmentArgs by navArgs()

    private val viewModel: NewsDetailViewModel by viewModel()

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
    }

    private fun setupWebView() = with(binding.webView) {
        settings.javaScriptEnabled = true
        webViewClient = WebViewClient()
        loadUrl(args.news.url)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}