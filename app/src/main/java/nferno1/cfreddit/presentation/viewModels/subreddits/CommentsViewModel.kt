package nferno1.cfreddit.presentation.viewModels.subreddits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import nferno1.cfreddit.R
import nferno1.cfreddit.data.MainRepository
import nferno1.cfreddit.di.IoDispatcher
import nferno1.cfreddit.domain.ApiResult
import nferno1.cfreddit.domain.model.CommentEntity
import nferno1.cfreddit.presentation.Communication
import nferno1.cfreddit.presentation.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val communication: Communication.Base<ApiResult<List<CommentEntity>>>,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _voteChannel = Channel<ApiResult<Int>>()
    val voteChannel = _voteChannel.receiveAsFlow()

    fun getComments(postId: String) {
        viewModelScope.launch(ioDispatcher) {
            communication.map(ApiResult.Loading())
            val response = mainRepository.getComments(postId)
            val comments = response.body()?.last()
            comments?.commonData?.data?.map { it.data.map() }?.filterIsInstance<CommentEntity>()
                .also { communication.map(ApiResult.Success(it)) }
        }
    }

    suspend fun observe(collector: FlowCollector<ApiResult<List<CommentEntity>>?>) {
        communication.observe(collector)
    }

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
}