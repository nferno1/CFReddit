package nferno1.cfreddit.presentation.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import nferno1.cfreddit.R
import nferno1.cfreddit.databinding.FragmentUserInfoBinding
import nferno1.cfreddit.domain.model.UserEntity
import nferno1.cfreddit.presentation.UiText
import nferno1.cfreddit.presentation.screens.CommonAbstractFragment

abstract class AbstractUserFragment : CommonAbstractFragment() {

    abstract val viewModel: ViewModel
    protected var _binding: FragmentUserInfoBinding? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeProfileData()

    }

    abstract fun observeProfileData()

    fun setDataToView(data: UserEntity) {
        Glide.with(this)
            .load(data.iconImg)
            .into(binding.avatar)
        binding.userName.text = data.name
        binding.created.text =
            UiText.ResourceString(R.string.created, data.created).asString(requireContext())
        binding.karma.text =
            UiText.ResourceString(R.string.karma, data.karma.toString()).asString(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}