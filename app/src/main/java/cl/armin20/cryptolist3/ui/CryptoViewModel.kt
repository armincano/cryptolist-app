package cl.armin20.cryptolist3.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.armin20.cryptolist3.CryptoList2Application
import cl.armin20.cryptolist3.model.Coins
import cl.armin20.cryptolist3.data.local.CryptoListRepository
import cl.armin20.cryptolist3.data.local.CryptoListRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.armin20.cryptolist3.ui.utils.DataStoreUtils

//stateHandle guarda el estado, mantien la posición de scrolling y evita el system initiated process death.
//The Navigation component, behind the scenes, saves the navigation arguments stored in NavStackEntry
class CryptoViewModel(private val stateHandle: SavedStateHandle) : ViewModel() {

    private val cryptoListRepository: CryptoListRepositoryInterface = CryptoListRepository.get(
        CryptoList2Application.getAppContext() as Application
    )

    //value holder whose reads and writes are observed by Compose
    val cryptoList = mutableStateOf(Coins(1, emptyList(), 1))
    var searchTextField = mutableStateOf("")

    var currentUserName = mutableStateOf("guest")
    var currentUserAvatar = mutableStateOf("avatar_default")

    init {
        Log.d("WelcomeViewModelbefore", "$currentUserName $currentUserAvatar")
        DataStoreUtils.getUserValuesDataStore(CryptoList2Application.getAppContext(), viewModelScope, currentUserName, currentUserAvatar)
        getCoins()
        Log.d("WelcomeViewModelafter", "$currentUserName $currentUserAvatar")
    }

    fun getDateTime(s: Long): String {
        return try {
            val sdf = SimpleDateFormat("HH:mm:ss")//"dd MMMM yyyy, HH:mm:ss"
            val date = Date(s)
            sdf.format(date)
        } catch (e: Exception) {
            e.toString()
        }
    }

    fun getCoins() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val fromRepositoryAll = cryptoListRepository.getCoinsAll()
                withContext(Dispatchers.Main) {//Recuerda que la UI se trabaja en Main
                    cryptoList.value = fromRepositoryAll
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error in getCoins: ", e)
            }
        }
    }

}