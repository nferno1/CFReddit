package nferno1.cfreddit.presentation.viewModels.subreddits

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import nferno1.cfreddit.data.MainRepository
import nferno1.cfreddit.di.IoDispatcher
import javax.inject.Inject

@HiltViewModel
class PopularSubredditsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AbstractSubredditsViewModel(mainRepository, ioDispatcher) {

    override fun getPagedSubreddits(query: String) =
        mainRepository.getPagedSubredditsFlow(query).cachedIn(viewModelScope)
}