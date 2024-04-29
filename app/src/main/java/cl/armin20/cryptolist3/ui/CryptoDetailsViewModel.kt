package cl.armin20.cryptolist3.ui

import android.app.Application
import android.content.ContentValues
import android.nfc.Tag
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class CryptoDetailsViewModel(stateHandle: SavedStateHandle) : ViewModel() {

    private val cryptoListRepository: CryptoListRepositoryInterface = CryptoListRepository.get(
        CryptoList2Application.getAppContext() as Application
    )

    //value holder whose reads and writes are observed by Compose
    val cryptoDetail = mutableStateOf(
        CoinDetailItem(
            "0",
            Data("offline", "OFFLINE", "Connect to the internet", 0f, 0f, 0f, "offline"),
            0
        )
    )

    fun getDateTime(s: Long): String {
        return try {
            val sdf = SimpleDateFormat("dd MMMM, HH:mm:ss")//"dd MMMM yyyy, HH:mm:ss"
            val date = Date(s)
            sdf.format(date)
        } catch (e: Exception) {
            e.toString()
        }
    }


    init {
        Log.i(ContentValues.TAG, "Error in getDetailCoin: ")
        val id = stateHandle.get<String>("id") ?: ""
        getDetailCoin(id)
    }

    private fun getDetailCoin(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val fromRepositorySingle = cryptoListRepository.getSingle(id)
                withContext(Dispatchers.Main) {//Recuerda que la UI se trabaja en Main
                    cryptoDetail.value = fromRepositorySingle
                }
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Error in getDetailCoin: ", e)
            }
        }
    }
}
