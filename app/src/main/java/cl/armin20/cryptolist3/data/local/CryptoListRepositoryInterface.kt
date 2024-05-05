package cl.armin20.cryptolist3.data.local

import cl.armin20.cryptolist3.model.CoinDetailItem
import cl.armin20.cryptolist3.model.Coins
import cl.armin20.cryptolist3.model.User
import cl.armin20.cryptolist3.model.Users

interface CryptoListRepositoryInterface {
    suspend fun getCoinsAll():Coins
    suspend fun getCoinsSingle(id: String): CoinDetailItem

    suspend fun addUser(user:User)
    suspend fun getUsers(): Users


}