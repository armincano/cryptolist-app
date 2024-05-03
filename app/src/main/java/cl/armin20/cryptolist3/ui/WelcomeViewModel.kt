package cl.armin20.cryptolist3.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.armin20.cryptolist3.data.local.getUserNameValues
import cl.armin20.cryptolist3.data.local.writeUserName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class WelcomeViewModel : ViewModel() {

    var currentUserName = ""

    fun saveUserName(userName: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            writeUserName(userName, context)
        }
    }

    fun getFirstUserName(userNameKey: String, context: Context) {
        val userNameValuesFlow = getUserNameValues(userNameKey, context)
        viewModelScope.launch(Dispatchers.IO) {
            currentUserName = userNameValuesFlow.first()
            if (currentUserName.isEmpty()) {
                saveUserName("guest", context)
            }
        }

    }

}