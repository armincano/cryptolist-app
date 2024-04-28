package cl.armin20.cryptolist3

import android.app.Application
import android.content.Context

//Exposing the application context to get Context and pass it to the DB's singleton.
//Add it in AndroidManifest
class CryptoList2Application: Application() {
    init { app = this }
    companion object {
        private lateinit var app: CryptoList2Application
        fun getAppContext(): Context = app.applicationContext
    }
}