package br.com.fornaro.mesanews.features.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.fornaro.mesanews.databinding.DialogFragmentFilterBinding
import br.com.fornaro.mesanews.domain.enums.FeedFilter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.viewmodel.ext.android.viewModel

class FilterDialogFragment : BottomSheetDialogFragment() {

    private val viewModel: FilterDialogViewModel by viewModel()

    private var _binding: DialogFragmentFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DialogFragmentFilterBinding
        .inflate(inflater)
        .apply {
            _binding = this
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRadioButtons()
        setupViewModel()
    }

    private fun setupRadioButtons() = with(binding) {
        filterDateButton.setOnClickListener {
            viewModel.filterNewsByDate()
        }
        filterFavoriteButton.setOnClickListener {
            viewModel.filterNewsByFavorite()
        }
    }

    private fun setupViewModel() = with(viewModel) {
        state.observe(viewLifecycleOwner, ::handleState)
    }

    private fun handleState(state: FilterState) = when (state) {
        is FilterState.Success -> handleSuccess()
        is FilterState.Initial -> handleInitial(state.filter)
    }

    private fun handleSuccess() {
        findNavController().navigateUp()
    }

    private fun handleInitial(filter: FeedFilter) = when (filter) {
        FeedFilter.DATE -> binding.filterDateButton.isChecked = true
        FeedFilter.FAVORITE -> binding.filterFavoriteButton.isChecked = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}