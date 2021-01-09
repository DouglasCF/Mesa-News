package br.com.fornaro.mesanews.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import br.com.fornaro.mesanews.R
import br.com.fornaro.mesanews.databinding.FragmentLoginBinding
import br.com.fornaro.mesanews.domain.enums.ErrorType
import br.com.fornaro.mesanews.extensions.toast
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModel()

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentLoginBinding
        .inflate(inflater)
        .apply {
            _binding = this
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCreateAccountButton()
        setupLoginButton()
        setupViewModel()
    }

    private fun setupLoginButton() = with(binding.loginButton) {
        setOnClickListener {
            viewModel.signIn(
                email = binding.emailText.text.toString(),
                password = binding.passwordText.text.toString()
            )
        }
    }

    private fun setupViewModel() = with(viewModel) {
        state.observe(viewLifecycleOwner, ::handleState)
    }

    private fun handleState(state: SignInState) {
        handleLoading(false)
        when (state) {
            is SignInState.Loading -> handleLoading(true)
            is SignInState.Error -> handleError(state.error)
            is SignInState.Success -> handleSuccess()
        }
    }

    private fun handleLoading(loading: Boolean) = with(binding.loading) { isVisible = loading }

    private fun handleError(errorType: ErrorType) = when (errorType) {
        ErrorType.NO_INTERNET -> toast(R.string.error_no_internet)
        ErrorType.INVALID_EMAIL -> toast(R.string.error_invalid_email)
        ErrorType.INVALID_PASSWORD -> toast(R.string.error_invalid_password)
        else -> toast(R.string.error_generic_error)
    }

    private fun handleSuccess() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupCreateAccountButton() = with(binding.createAccountButton) {
        setOnClickListener { findNavController().navigate(R.id.createAccountFragment) }
    }
}