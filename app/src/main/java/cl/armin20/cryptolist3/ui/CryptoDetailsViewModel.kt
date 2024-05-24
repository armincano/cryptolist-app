package cl.armin20.cryptolist3.ui

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.armin20.cryptolist3.CryptoList2Application
import cl.armin20.cryptolist3.model.CoinDetailItem
import cl.armin20.cryptolist3.model.Data
import cl.armin20.cryptolist3.data.local.CryptoListRepository
import cl.armin20.cryptolist3.data.local.CryptoListRepositoryInterface
import cl.armin20.cryptolist3.model.StarredCoin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class CryptoDetailsViewModel(stateHandle: SavedStateHandle) : ViewModel() {

    private val cryptoListRepository: CryptoListRepositoryInterface = CryptoListRepository.get(
        CryptoList2Application.getAppContext() as Application
    )

    var id = stateHandle.get<String>("id") ?: ""
    val isStarredFlow = MutableStateFlow(false)

    //value holder whose reads and writes are observed by Compose
    val cryptoDetail = mutableStateOf(
        CoinDetailItem(
            "0",
            Data("offline", "", "Connecting...", 0f, 0f, 0f, "offline"),
            0
        )
    )

    init {
        isSingleCoinStarred(id)
        getDetailCoin(id)
    }

    fun addStarredCoin(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            cryptoListRepository.addStarredCoin(StarredCoin(id))
            isStarredFlow.emit(true)
        }
    }

    fun removeStarredCoin(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            cryptoListRepository.removeStarredCoin(StarredCoin(id))
            isStarredFlow.emit(false)
        }
    }

    fun isSingleCoinStarred(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val isStarred = cryptoListRepository.isSingleCoinStarred(id) == 1
            withContext(Dispatchers.Main){
                isStarredFlow.emit(isStarred)
            }
        }
    }

    fun getDetailCoin(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val fromRepositorySingle = cryptoListRepository.getSingleCoin(id)
                withContext(Dispatchers.Main) {//Recuerda que la UI se trabaja en Main
                    cryptoDetail.value = fromRepositorySingle
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Error in getDetailCoin: ", e)
            }
        }
    }

    fun parseTimestamp(timestamp: Long): String {
        return try {
            val sdf = SimpleDateFormat("MMMM dd, HH:mm:ss")//"dd MMMM yyyy, HH:mm:ss"
            val date = Date(timestamp)
            sdf.format(date)
        } catch (e: Exception) {
            e.toString()
        }
    }

}

