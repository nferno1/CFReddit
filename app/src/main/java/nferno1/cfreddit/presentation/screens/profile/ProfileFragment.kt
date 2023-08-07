package nferno1.cfreddit.presentation.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import nferno1.cfreddit.R
import nferno1.cfreddit.databinding.FragmentUserInfoBinding
import nferno1.cfreddit.domain.ApiResult
import nferno1.cfreddit.presentation.UiText
import nferno1.cfreddit.presentation.viewModels.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : AbstractUserFragment() {

    override val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMyProfile()

        binding.logoutButton.setOnClickListener {
            viewModel.logOut { findNavController().navigate(R.id.from_profile_to_onboarding) }
        }
    }

    override fun observeProfileData() {
        lifecycleScope.launchWhenStarted {
            viewModel.observe {
                if (it != null) {
                    when (it) {
                        is ApiResult.Loading -> {
                            binding.progressLayout.visibility = View.VISIBLE
                        }

                        is ApiResult.Error -> {
                            binding.progressLayout.visibility = View.GONE
                            showToast(
                                (it.errorMessage
                                    ?: UiText.ResourceString(R.string.something_went_wrong)).asString(
                                    requireContext()
                                )
                            )
                        }

                        is ApiResult.Success -> {
                            binding.progressLayout.visibility = View.GONE
                            it.data?.let { setDataToView(it.map()) }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}