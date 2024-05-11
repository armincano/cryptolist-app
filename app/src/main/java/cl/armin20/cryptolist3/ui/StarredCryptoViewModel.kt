package cl.armin20.cryptolist3.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import cl.armin20.cryptolist3.CryptoList2Application
import cl.armin20.cryptolist3.data.local.CryptoListRepository
import cl.armin20.cryptolist3.data.local.CryptoListRepositoryInterface

class StarredCryptoViewModel : ViewModel() {
    private val cryptoListRepository: CryptoListRepositoryInterface = CryptoListRepository.get(
        CryptoList2Application.getAppContext() as Application
    )


}