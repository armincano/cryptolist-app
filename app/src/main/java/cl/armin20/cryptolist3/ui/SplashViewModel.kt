package cl.armin20.cryptolist3.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.armin20.cryptolist3.CryptoList2Application
import cl.armin20.cryptolist3.ui.utils.DataStoreUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel : ViewModel() {
    val isFirstRun = mutableStateOf(true)
    val isInitComplete = mutableStateOf(false)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val firstRunValue = DataStoreUtils.getFirstRunValueDataStore(
                CryptoList2Application.getAppContext()
            )
            withContext(Dispatchers.Main) {
                isFirstRun.value = firstRunValue
                isInitComplete.value = true
            }
        }
    }
}