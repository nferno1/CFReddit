package nferno1.cfreddit.presentation.screens.favourite

import androidx.fragment.app.viewModels
import nferno1.cfreddit.presentation.screens.AbstractSubredditsFragment
import nferno1.cfreddit.presentation.viewModels.favourite.FavoriteSubredditsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteSubredditsFragment : AbstractSubredditsFragment() {

    override val request = ""

    override val viewModel: FavoriteSubredditsViewModel by viewModels()
}