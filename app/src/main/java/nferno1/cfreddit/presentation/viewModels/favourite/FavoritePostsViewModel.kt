package nferno1.cfreddit.presentation.viewModels.favourite

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import nferno1.cfreddit.data.MainRepository
import nferno1.cfreddit.di.IoDispatcher
import nferno1.cfreddit.domain.model.PostEntity
import nferno1.cfreddit.presentation.viewModels.PostsViesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FavoritePostsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : PostsViesModel(mainRepository, ioDispatcher) {

    override fun getPagedPosts(subredditName: String): Flow<PagingData<PostEntity>> {
        return mainRepository.getPagedFavoritePosts().cachedIn(viewModelScope)
    }
}