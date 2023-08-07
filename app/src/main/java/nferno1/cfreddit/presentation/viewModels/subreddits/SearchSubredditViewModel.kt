package nferno1.cfreddit.presentation.viewModels.subreddits

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import nferno1.cfreddit.data.MainRepository
import nferno1.cfreddit.di.IoDispatcher
import nferno1.cfreddit.domain.model.SubredditEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchSubredditViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AbstractSubredditsViewModel(mainRepository, ioDispatcher) {

    override fun getPagedSubreddits(query: String): Flow<PagingData<SubredditEntity>> =
        mainRepository.getPagedSearchedSubredditsFlow(query).cachedIn(viewModelScope)

}