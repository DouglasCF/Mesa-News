package br.com.fornaro.mesanews.features.createaccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import br.com.fornaro.mesanews.R
import br.com.fornaro.mesanews.databinding.FragmentCreateAccountBinding
import br.com.fornaro.mesanews.domain.enums.ErrorType
import br.com.fornaro.mesanews.extensions.toast
import org.koin.android.viewmodel.ext.android.viewModel

class CreateAccountFragment : Fragment() {

    private val viewModel: CreateAccountViewModel by viewModel()

    private var _binding: FragmentCreateAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCreateAccountBinding
        .inflate(inflater)
        .apply {
            _binding = this
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBackButton()
        setupCreateAccountButton()
        setupViewModel()
    }

    private fun setupBackButton() = with(binding.backButton) {
        setOnClickListener { findNavController().navigateUp() }
    }

    private fun setupCreateAccountButton() = with(binding.createAccountButton) {
        setOnClickListener {
            viewModel.signUp(
                name = binding.nameText.text.toString(),
                email = binding.emailText.text.toString(),
                password = binding.passwordText.text.toString(),
                confirmPassword = binding.confirmPasswordText.text.toString()
            )
        }
    }

    private fun setupViewModel() = with(viewModel) {
        state.observe(viewLifecycleOwner, ::handleState)
    }

    private fun handleState(state: CreateAccountState) {
        handleLoading(false)
        when (state) {
            is CreateAccountState.Loading -> handleLoading(true)
            is CreateAccountState.Error -> handleError(state.error)
            is CreateAccountState.Success -> handleSuccess()
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

    private fun handleSuccess() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}