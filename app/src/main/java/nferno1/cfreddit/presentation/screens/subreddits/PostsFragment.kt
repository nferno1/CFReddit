package nferno1.cfreddit.presentation.screens.subreddits

import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import nferno1.cfreddit.R
import nferno1.cfreddit.domain.ApiResult
import nferno1.cfreddit.presentation.UiText
import nferno1.cfreddit.presentation.adapters.HeaderAdapter
import nferno1.cfreddit.presentation.adapters.PageLoadStateAdapter
import nferno1.cfreddit.presentation.screens.AbstractPostsFragment
import nferno1.cfreddit.presentation.viewModels.PostsViesModel
import dagger.hilt.android.AndroidEntryPoint
import nferno1.cfreddit.data.remote.RedditApi.Companion.subredditName

@AndroidEntryPoint
class PostsFragment : AbstractPostsFragment() {

    override val viewModel: PostsViesModel by viewModels()
    override var apiQuery: String = ""
    private val headerAdapter = HeaderAdapter(
        onSubscribeClick = { name, isSubscribed, position ->
            viewModel.subscribeUnsubscribe(
                name,
                isSubscribed,
                position
            )
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        apiQuery = arguments?.getString(subredditName) ?: ""
        super.onViewCreated(view, savedInstanceState)

    }

    override fun setUpPageAdapter() {
        val concatAdapter = ConcatAdapter(
            headerAdapter,
            postsPageAdapter.withLoadStateFooter(PageLoadStateAdapter())
        )
        val divider = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        divider.setDrawable(
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.item_divider
            )!!
        )
        binding.recyclerView.apply {
            adapter = concatAdapter
            addItemDecoration(divider)
        }
    }

    override fun observeSubredditUpdates(currentSubredditId: String) {
        lifecycleScope.launchWhenStarted {
            viewModel.getCurrentSubreddit(currentSubredditId).collect {
                headerAdapter.setData(it)
            }
        }
    }

    override fun observeSubscribeResult() {
        lifecycleScope.launchWhenStarted {
            viewModel.subscribeChannel.collect { result ->
                if (result is ApiResult.Error) {
                    showToast(
                        UiText.ResourceString(R.string.something_went_wrong)
                            .asString(requireContext())
                    )
                } else {
                    headerAdapter.updateSubscribeInfo(result)
                }
            }
        }
    }
}