package nferno1.cfreddit.presentation

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

interface Communication<T : Any> {

    fun map(data: T)

    suspend fun observe(collector: FlowCollector<T?>)

    class Base<T : Any> @Inject constructor() : Communication<T> {

        private val mutableStateFlow = MutableStateFlow<T?>(null)

        override fun map(data: T) {
            mutableStateFlow.value = data
        }

        override suspend fun observe(collector: FlowCollector<T?>) {
            mutableStateFlow.collect(collector)
        }
    }
}