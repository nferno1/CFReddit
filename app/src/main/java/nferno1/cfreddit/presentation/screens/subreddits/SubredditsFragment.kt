package nferno1.cfreddit.presentation.screens.subreddits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import nferno1.cfreddit.R
import nferno1.cfreddit.databinding.FragmentSubredditsBinding
import nferno1.cfreddit.presentation.screens.CommonAbstractFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubredditsFragment : CommonAbstractFragment() {

    private var _binding: FragmentSubredditsBinding? = null
    private val binding get() = _binding!!
    

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubredditsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSearchField()

        val pageAdapter = SubredditsViewPagerAdapter(this)
        val viewPager = binding.subredditsPager
        viewPager.adapter = pageAdapter
        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.new_title)
                1 -> tab.text = getString(R.string.popular_title)
            }
        }.attach()
    }

    private fun setUpSearchField() {
        binding.searchLayout.setStartIconOnClickListener {
            findNavController().navigate(
                R.id.from_subreddits_to_search,
                Bundle().also {
                    it.putString(
                        "searchQuery",
                        binding.searchField.text.toString()
                    )
                })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}