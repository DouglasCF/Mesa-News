package br.com.fornaro.mesanews.features.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import br.com.fornaro.mesanews.R
import br.com.fornaro.mesanews.databinding.FragmentFeedBinding
import br.com.fornaro.mesanews.domain.enums.ErrorType
import br.com.fornaro.mesanews.extensions.toast
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
    }

    private fun setupViewModel() = with(viewModel) {
        state.observe(viewLifecycleOwner, ::handleState)
        getHighlightsNews()
    }

    private fun handleState(state: FeedState) {
        handleLoading(false)
        when (state) {
            is FeedState.Loading -> handleLoading(true)
            is FeedState.Error -> handleError(state.error)
            is FeedState.Success -> handleSuccess(state)
        }
    }

    private fun handleLoading(loading: Boolean) = with(binding.loading) { isVisible = loading }

    private fun handleError(errorType: ErrorType) = when (errorType) {
        ErrorType.NO_INTERNET -> toast(R.string.error_no_internet)
        ErrorType.INVALID_NAME -> toast(R.string.error_invalid_name)
        ErrorType.INVALID_EMAIL -> toast(R.string.error_invalid_email)
        ErrorType.INVALID_PASSWORD -> toast(R.string.error_invalid_password)
        ErrorType.INVALID_CONFIRM_PASSWORD -> toast(R.string.error_invalid_confirm_password)
        else -> toast(R.string.error_generic_error)
    }

    private fun handleSuccess(data: FeedState.Success) {
        data
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}