package cl.armin20.cryptolist3.ui.utils

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cl.armin20.cryptolist3.data.local.getFirstRunValue
import cl.armin20.cryptolist3.data.local.getUserValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DataStoreUtils {

    suspend fun getFirstRunValueDataStore(context: Context): Boolean {
        return getFirstRunValue(context).first()
    }

    fun getUserValuesDataStore(
        context: Context,
        viewModelScope: CoroutineScope,
        currentUserName: MutableState<String>,
        currentUserAvatar: MutableState<String>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                currentUserName.value = getUserValue("user_name", context).first()
                currentUserAvatar.value = getUserValue("user_avatar", context).first()
            }
        }
    }

}