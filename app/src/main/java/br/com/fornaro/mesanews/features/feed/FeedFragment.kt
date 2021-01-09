package br.com.fornaro.mesanews.features.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.fornaro.mesanews.databinding.FragmentFeedBinding
import org.koin.android.viewmodel.ext.android.viewModel

class FeedFragment : Fragment() {

    private val viewModel: FeedViewModel by viewModel()

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}