package cl.armin20.cryptolist3.ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.armin20.cryptolist3.CryptoList2Application
import cl.armin20.cryptolist3.ui.utils.DataStoreUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel : ViewModel() {
    val isFirstRun = mutableStateOf(true)
    var isFirstRunDataStoreGot =  mutableStateOf(false)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val firstRunValue = mutableStateOf(false)
            DataStoreUtils.getFirstRunValueDataStore(
                CryptoList2Application.getAppContext(),
                viewModelScope,
                firstRunValue
            )
            withContext(Dispatchers.Main) {
                isFirstRun.value = firstRunValue.value
                isFirstRunDataStoreGot.value = true
            }
        }
    }
}