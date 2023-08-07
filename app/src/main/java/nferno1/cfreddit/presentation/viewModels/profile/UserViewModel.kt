package nferno1.cfreddit.presentation.viewModels.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import nferno1.cfreddit.data.MainRepository
import nferno1.cfreddit.di.IoDispatcher
import nferno1.cfreddit.domain.ApiResult
import nferno1.cfreddit.domain.model.AbstractRedditEntity
import nferno1.cfreddit.presentation.Communication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val communication: Communication.Base<ApiResult<AbstractRedditEntity>>,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun getUserInfo(userName: String) {
        viewModelScope.launch(ioDispatcher) {
            communication.map(ApiResult.Loading())
            val response = mainRepository.getUserInfo(userName)
            communication.map(response)
        }
    }

    suspend fun observe(collector: FlowCollector<ApiResult<AbstractRedditEntity>?>) {
        communication.observe(collector)
    }
}