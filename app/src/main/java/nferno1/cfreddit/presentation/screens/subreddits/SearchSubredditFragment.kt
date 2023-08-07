package nferno1.cfreddit.presentation.screens.subreddits

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import nferno1.cfreddit.presentation.screens.AbstractSubredditsFragment
import nferno1.cfreddit.presentation.viewModels.subreddits.SearchSubredditViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchSubredditFragment : AbstractSubredditsFragment() {

    override var request = ""

    override val viewModel: SearchSubredditViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        request = arguments?.getString("searchQuery") ?: ""
        super.onViewCreated(view, savedInstanceState)

    }
}