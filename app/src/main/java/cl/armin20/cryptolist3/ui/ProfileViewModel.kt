package cl.armin20.cryptolist3.ui

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.armin20.cryptolist3.CryptoList2Application
import cl.armin20.cryptolist3.data.local.CryptoListRepository
import cl.armin20.cryptolist3.data.local.CryptoListRepositoryInterface
import cl.armin20.cryptolist3.data.local.writeUserAvatar
import cl.armin20.cryptolist3.data.local.writeUserName
import cl.armin20.cryptolist3.model.StarredCoin
import cl.armin20.cryptolist3.model.User
import cl.armin20.cryptolist3.ui.utils.DataStoreUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProfileViewModel : ViewModel() {

    private val cryptoListRepository: CryptoListRepositoryInterface = CryptoListRepository.get(
        CryptoList2Application.getAppContext() as Application
    )

    var currentUserName = mutableStateOf("guest")
    var currentUserAvatar = mutableStateOf("avatar_default")
    var users = mutableStateOf(listOf<User>())
    var selectedUserInChangeProfile = mutableStateOf(User(null, "", ""))
    var starredCryptoList = mutableStateOf(listOf<StarredCoin>())

    init {
        DataStoreUtils.getUserValuesDataStore(CryptoList2Application.getAppContext(), viewModelScope, currentUserName, currentUserAvatar)
        getAllStarredCryptos()
        viewModelScope.launch(Dispatchers.Main) {
            users.value =  cryptoListRepository.getAllUsers()
        }
    }

    fun getAllStarredCryptos() {
        viewModelScope.launch(Dispatchers.IO) {
            val allStarredCoins = cryptoListRepository.getAllStarredCoins()
            withContext(Dispatchers.Main) {
                starredCryptoList.value = allStarredCoins
            }
        }
    }

    fun saveUserDB(firstName:String, avatar:String="avatar_default"){
        val user = User(null, firstName, avatar)
        viewModelScope.launch(Dispatchers.IO) {
            cryptoListRepository.addUser(user)
        }
    }

    fun saveUserDataStore(firstName:String, avatar:String="avatar_default", context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            writeUserName(firstName, context)
            writeUserAvatar(avatar, context)
        }
    }

}