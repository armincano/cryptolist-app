package cl.armin20.cryptolist3.data.remote

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CoincapRetrofitClient {
    companion object {
        init {
            Log.i(ContentValues.TAG, "CoincapRetrofitClient()")
        }
        private const val BASE_URL = "https://api.coincap.io/v2/"
        fun retrofitInstance(): CoincapApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            Log.i(ContentValues.TAG, "retrofit $retrofit")
            return retrofit.create(CoincapApi::class.java)
        }
    }
}