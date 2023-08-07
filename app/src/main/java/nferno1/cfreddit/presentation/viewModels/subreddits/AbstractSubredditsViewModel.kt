package nferno1.cfreddit.presentation.viewModels.subreddits

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import nferno1.cfreddit.data.MainRepository
import nferno1.cfreddit.di.IoDispatcher
import nferno1.cfreddit.domain.model.SubredditEntity
import nferno1.cfreddit.presentation.viewModels.SubscribableViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class AbstractSubredditsViewModel(
    private val mainRepository: MainRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : SubscribableViewModel(mainRepository, ioDispatcher) {

    abstract fun getPagedSubreddits(query: String): Flow<PagingData<SubredditEntity>>

    fun saveCurrentSubreddit(subreddit: SubredditEntity) {
        viewModelScope.launch(ioDispatcher) {
            mainRepository.saveCurrentSubreddit(subreddit)
        }
    }
}