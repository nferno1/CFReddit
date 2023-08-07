package nferno1.cfreddit.presentation.screens.subreddits

import androidx.fragment.app.viewModels
import nferno1.cfreddit.presentation.screens.AbstractSubredditsFragment
import nferno1.cfreddit.presentation.viewModels.subreddits.PopularSubredditsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularSubredditsFragment : AbstractSubredditsFragment() {

    override val request = "popular"

    override val viewModel: PopularSubredditsViewModel by viewModels()

}