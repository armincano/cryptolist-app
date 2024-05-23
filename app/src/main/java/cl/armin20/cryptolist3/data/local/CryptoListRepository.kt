package cl.armin20.cryptolist3.data.local

import android.app.Application
import android.widget.Toast
import cl.armin20.cryptolist3.CryptoList2Application
import cl.armin20.cryptolist3.NetworkPing
import cl.armin20.cryptolist3.db.CoinsDb
import cl.armin20.cryptolist3.model.CoinDetailItem
import cl.armin20.cryptolist3.model.Coins
import cl.armin20.cryptolist3.model.Data
import cl.armin20.cryptolist3.data.remote.CoincapRetrofitClient
import cl.armin20.cryptolist3.model.StarredCoin
import cl.armin20.cryptolist3.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

class CryptoListRepository(application: Application) : CryptoListRepositoryInterface {

    private var coincapApiService = CoincapRetrofitClient.retrofitInstance()
    private var coinsDao = CoinsDb.getDaoInstance(CryptoList2Application.getAppContext())

    override suspend fun addUser(user: User) {
        coinsDao.upsertUser(user)
    }

    override suspend fun getAllUsers(): List<User> {
        return coinsDao.getAllUsers()
    }

    override suspend fun addStarredCoin(starredCoin: StarredCoin) {
        coinsDao.addStarredCoin(starredCoin)
    }

    override suspend fun removeStarredCoin(starredCoin: StarredCoin) {
        coinsDao.removeStarredCoin(starredCoin)
    }

    override suspend fun getAllStarredCoins(): List<StarredCoin> {
        return coinsDao.getAllStarredCoin()
    }

    override suspend fun isSingleCoinStarred(id: String): Int {
        return coinsDao.isSingleCoinStarred(id)
    }

    override suspend fun getAllCoins(): Coins {
        try {
            if (NetworkPing.getStatus("https://www.google.com/")) {
                val coins = coincapApiService.getAllCoins()
                coinsDao.addAllCoins(coins)
                return coinsDao.getAllCoins()
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        CryptoList2Application.getAppContext(),
                        "No internet connection",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException,
                is ConnectException,
                is HttpException -> {
                }

                else -> throw e
            }
        }
        // TODO return empty list first time usage, if db is empty
        return Coins(0, emptyList(), 0)
    }

    override suspend fun getSingleCoin(id: String): CoinDetailItem {
        try {
            if (NetworkPing.getStatus("https://www.google.com/")) {
                val detailCoins = coincapApiService.getSingleCoin(id)
                detailCoins.coinId = id
                coinsDao.addSingleCoin(detailCoins)
                return coinsDao.getSingleCoin(id)
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        CryptoList2Application.getAppContext(),
                        "No internet connection",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException,
                is ConnectException,
                is HttpException -> {
                }

                else -> throw e
            }
        }
        return CoinDetailItem(
            "0",
            Data("offline", "...", "Connecting...", 0f, 0f, 0f, "offline"),
            0
        )
    }

    companion object {
        private var INSTANCE: CryptoListRepository? = null
        fun get(application: Application): CryptoListRepository {
            if (INSTANCE == null) {
                INSTANCE = CryptoListRepository(application)
            }
            return INSTANCE
                ?: throw IllegalStateException("Instance of CryptoListRepository is null")
        }
    }

}