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

    suspend fun addStarredCoin(starredCoin: StarredCoin)
    suspend fun removeStarredCoin(starredCoin: StarredCoin)
    suspend fun getAllStarredCoins():List<StarredCoin>
    suspend fun isSingleCoinStarred(id: String):Int


}