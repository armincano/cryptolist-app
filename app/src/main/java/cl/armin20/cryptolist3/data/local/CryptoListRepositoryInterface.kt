package cl.armin20.cryptolist3.data.local

import cl.armin20.cryptolist3.model.CoinDetailItem
import cl.armin20.cryptolist3.model.Coins
import cl.armin20.cryptolist3.model.StarredCoin
import cl.armin20.cryptolist3.model.User

interface CryptoListRepositoryInterface {
    suspend fun getAllCoins():Coins
    suspend fun getSingleCoin(id: String): CoinDetailItem

    suspend fun addUser(user:User)
    suspend fun getAllUsers():List<User>

    suspend fun addStarredCoin(user: String, coinId: String)
    suspend fun removeStarredCoin(user: String, coinId: String)
    suspend fun getStarredCoin(user: String):StarredCoin
    suspend fun isSingleCoinStarred(user: String, coinId: String):Int
}