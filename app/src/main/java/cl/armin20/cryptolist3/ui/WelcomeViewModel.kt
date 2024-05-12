package cl.armin20.cryptolist3.ui

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.armin20.cryptolist3.CryptoList2Application
import cl.armin20.cryptolist3.data.local.CryptoListRepository
import cl.armin20.cryptolist3.data.local.CryptoListRepositoryInterface
import cl.armin20.cryptolist3.data.local.writeFirstRun
import cl.armin20.cryptolist3.data.local.writeUserAvatar
import cl.armin20.cryptolist3.data.local.writeUserName
import cl.armin20.cryptolist3.model.User
import cl.armin20.cryptolist3.ui.utils.DataStoreUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WelcomeViewModel : ViewModel() {

    private val cryptoListRepository: CryptoListRepositoryInterface = CryptoListRepository.get(
        CryptoList2Application.getAppContext() as Application
    )

    fun saveFirstRunAndUserDBAndUserDataStore(context: Context, firstName:String, avatar:String="avatar_default", onItemClick: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            writeFirstRun(context)
            writeUserName(firstName, context)
            writeUserAvatar(avatar, context)
            val user = User(null, firstName, avatar)
            cryptoListRepository.addUser(user)
            withContext(Dispatchers.Main) {
                onItemClick()
            }
        }
    }

}