package nferno1.cfreddit.presentation.screens.subreddits

import androidx.fragment.app.viewModels
import nferno1.cfreddit.presentation.screens.AbstractSubredditsFragment
import nferno1.cfreddit.presentation.viewModels.subreddits.NewSubredditsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewSubredditsFragment : AbstractSubredditsFragment() {

    override val request = "new"

    override val viewModel: NewSubredditsViewModel by viewModels()

}