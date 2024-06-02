package cl.armin20.cryptolist3.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "starred_coin")
data class StarredCoin(
    @PrimaryKey
    val user: String,

    val starredCoins: MutableSet<String>,
)
