package cl.armin20.cryptolist3

import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class NetworkPing {
    companion object {
        @Throws(IOException::class)
        fun getStatus(url: String): Boolean {
            var result: Boolean
            val code: Int
            try {
                val siteURL = URL(url)
                val connection: HttpURLConnection = siteURL.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 1000
                connection.connect()
                code = connection.responseCode
                result = code == 200


            } catch (e: Exception) {
                result = false
            }
            println("$url\t\tStatus:$result")
            return result
        }
    }
}