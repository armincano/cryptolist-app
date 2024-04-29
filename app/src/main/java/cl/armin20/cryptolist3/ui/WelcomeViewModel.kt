package cl.armin20.cryptolist3.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.armin20.cryptolist3.data.local.getUserName
import cl.armin20.cryptolist3.data.local.writeUserName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class WelcomeViewModel : ViewModel() {

    var userName = ""

    fun saveUserName(name: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            writeUserName(name, context)
        }
    }

    fun getName(name: String, context: Context) {
        val getUserName = getUserName(name, context)

        viewModelScope.launch(Dispatchers.IO) {
            userName = getUserName.first()
            if (userName.isEmpty()) {
                saveUserName("a guest", context)
            }
        }

    }

}