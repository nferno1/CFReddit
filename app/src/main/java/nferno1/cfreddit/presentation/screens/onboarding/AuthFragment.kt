package nferno1.cfreddit.presentation.screens.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import nferno1.cfreddit.R
import nferno1.cfreddit.databinding.FragmentAuthBinding
import nferno1.cfreddit.domain.ApiResult.*
import nferno1.cfreddit.presentation.screens.CommonAbstractFragment
import nferno1.cfreddit.presentation.viewModels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : CommonAbstractFragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()
    private val authLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val intentData = it.data ?: return@registerForActivityResult
            viewModel.handleAuthResponseIntent(intentData)
        }

    private fun openAuthPage() = viewModel.prepareAuthPageIntent { authLauncher.launch(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.observe {
                if (it is Loading) {
                    binding.progressLayout.visibility = View.VISIBLE
                }
                if (it is Success) {
                    findNavController().navigate(R.id.from_auth_to_main)
                    binding.progressLayout.visibility = View.GONE
                }
                if (it is Error) {
                    binding.progressLayout.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        it.exception.asString(requireContext()),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.authButton.setOnClickListener {
            openAuthPage()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}