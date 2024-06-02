package cl.armin20.cryptolist3.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.armin20.cryptolist3.model.CoinDetailItem
import cl.armin20.cryptolist3.model.Coins
import cl.armin20.cryptolist3.model.StarredCoin
import cl.armin20.cryptolist3.model.User

@Dao
interface CoinsDao {
    @Query("SELECT * FROM coins")
    suspend fun getAllCoins(): Coins

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllCoins(coins: Coins)

    @Query("SELECT * FROM coin_detail_item where coinId = :coinId")
    suspend fun getSingleCoin(coinId: String): CoinDetailItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSingleCoin(coinDetail: CoinDetailItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUser(user: User)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT CASE WHEN EXISTS( SELECT * FROM users WHERE firstName = :user ) THEN 1 ELSE 0 END")
    suspend fun isUserExists(user: String): Int

    @Query("SELECT * FROM starred_coin where user = :user")
    suspend fun getStarredCoin(user: String): StarredCoin

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStarredCoin(starredCoin: StarredCoin)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun removeStarredCoin(starredCoin: StarredCoin)

    @Query("SELECT CASE WHEN EXISTS( SELECT * FROM starred_coin WHERE user = :user AND starredCoins LIKE '%' || :coinId || '%') THEN 1 ELSE 0 END")
    suspend fun isSingleCoinStarred(user: String, coinId: String): Int

}