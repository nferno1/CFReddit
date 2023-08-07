package nferno1.cfreddit.presentation.viewModels

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import nferno1.cfreddit.R
import nferno1.cfreddit.data.MainRepository
import nferno1.cfreddit.di.IoDispatcher
import nferno1.cfreddit.domain.ApiResult
import nferno1.cfreddit.domain.model.SubredditEntity
import nferno1.cfreddit.presentation.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class PostsViesModel @Inject constructor(
    private val mainRepository: MainRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : SubscribableViewModel(mainRepository, ioDispatcher) {

    private val _voteChannel = Channel<ApiResult<Int>>()
    val voteChannel = _voteChannel.receiveAsFlow()

    fun handleVote(dir: Int, postId: String, position: Int) {
        viewModelScope.launch(ioDispatcher) {
            _voteChannel.send(ApiResult.Loading(position))
            kotlin.runCatching {
                mainRepository.vote(dir, postId)
            }.fold(
                onSuccess = { _voteChannel.send(ApiResult.Success(position)) },
                onFailure = {
                    _voteChannel.send(
                        ApiResult.Error(UiText.ResourceString(R.string.something_went_wrong))
                    )
                }
            )
        }
    }

    open fun getPagedPosts(subredditName: String) =
        mainRepository.getPagedPostsFlow(subredditName).cachedIn(viewModelScope)

    fun getCurrentSubreddit(subredditName: String): Flow<SubredditEntity> {
        return mainRepository.getCurrentSubreddit(subredditName)
    }
}