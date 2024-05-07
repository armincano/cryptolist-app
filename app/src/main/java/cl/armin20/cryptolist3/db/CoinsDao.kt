package cl.armin20.cryptolist3.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.armin20.cryptolist3.model.CoinDetailItem
import cl.armin20.cryptolist3.model.Coins
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
    suspend fun addSingleCoin(coin_detail: CoinDetailItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUser(user: User)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>

}