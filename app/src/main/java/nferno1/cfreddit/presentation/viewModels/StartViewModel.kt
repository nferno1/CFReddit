package nferno1.cfreddit.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import nferno1.cfreddit.data.MainRepository
import nferno1.cfreddit.di.IoDispatcher
import nferno1.cfreddit.presentation.Communication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val communication: Communication.Base<Boolean>,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun checkAccessToken() {
        viewModelScope.launch(ioDispatcher) {
            val result = mainRepository.checkAccessToken()
            communication.map(result)
        }
    }

    suspend fun observe(collector: FlowCollector<Boolean?>) {
        communication.observe(collector)
    }
}