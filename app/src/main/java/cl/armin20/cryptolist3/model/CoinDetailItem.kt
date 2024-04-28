package cl.armin20.cryptolist3.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "coin_detail_item")
data class CoinDetailItem(

    @PrimaryKey
    @ColumnInfo(name = "coinId")
    var coinId: String,

    @ColumnInfo(name = "data_detail")
    val data: Data,

    @ColumnInfo(name = "data_timestamp")
    val timestamp: Long
)