package nferno1.cfreddit.presentation.viewModels

import android.content.Intent
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import nferno1.cfreddit.R
import nferno1.cfreddit.data.MainRepository
import nferno1.cfreddit.di.IoDispatcher
import nferno1.cfreddit.domain.ApiResult
import nferno1.cfreddit.domain.AppAuth
import nferno1.cfreddit.presentation.Communication
import nferno1.cfreddit.presentation.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authService: AuthorizationService,
    private val mainRepository: MainRepository,
    private val communication: Communication.Base<ApiResult<UiText>>,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun handleAuthResponseIntent(intent: Intent) {
        val exception = AuthorizationException.fromIntent(intent)
        val tokenExchangeRequest = AppAuth.prepareTokenRequest(intent)

        viewModelScope.launch(ioDispatcher) {
            if (exception != null) {
                Log.d("AAA", exception.message ?: "authResponse handle exception")
            } else {
                onAuthCodeReceived(tokenExchangeRequest)
            }
        }
    }

    suspend fun onAuthCodeReceived(tokenExchangeRequest: TokenRequest) {
        runCatching {
            communication.map(ApiResult.Loading())
            AppAuth.performTokenRequestSuspend(
                authService = authService,
                tokenRequest = tokenExchangeRequest
            )
        }.onSuccess {
            mainRepository.saveAccessToken(it)
            Log.d("AAA token", it)
            communication.map(ApiResult.Success(UiText.DynamicString(it)))
        }.onFailure {
            val errorMessage =
                if (it.message == null) UiText.ResourceString(R.string.authcode_receiving_error)
                else UiText.DynamicString(it.message!!)

            communication.map(ApiResult.Error(errorMessage))
        }
    }

    suspend fun observe(collector: FlowCollector<ApiResult<UiText>?>) {
        communication.observe(collector)
    }

    fun prepareAuthPageIntent(openAuthPage: (Intent) -> Unit) {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        val authRequest = AppAuth.getAuthorizationRequest()

        val authPageIntent = authService.getAuthorizationRequestIntent(
            authRequest,
            customTabsIntent
        )
        openAuthPage(authPageIntent)
    }
}