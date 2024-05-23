package cl.armin20.cryptolist3.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.armin20.cryptolist3.CryptoList2Application
import cl.armin20.cryptolist3.data.local.CryptoListRepository
import cl.armin20.cryptolist3.data.local.CryptoListRepositoryInterface
import cl.armin20.cryptolist3.model.CoinDetailItem
import cl.armin20.cryptolist3.model.StarredCoin
import cl.armin20.cryptolist3.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StarredCryptoViewModel : ViewModel() {
    private val cryptoListRepository: CryptoListRepositoryInterface = CryptoListRepository.get(
        CryptoList2Application.getAppContext() as Application
    )

    var starredCryptoList = mutableStateOf(listOf<StarredCoin>())

    init {
        getAllStarredCryptos()
    }

    fun getAllStarredCryptos() {
        viewModelScope.launch(Dispatchers.IO) {
            val allStarredCoins = cryptoListRepository.getAllStarredCoins()
            withContext(Dispatchers.Main) {
                starredCryptoList.value = allStarredCoins
            }
        }
    }

}