package cl.armin20.cryptolist3.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "coins")
data class Coins(

    // Creo este campo para poder reemplazar el objeto y reemplazarlo. Porque al usar timestamp siempre es un objeto diferent y no lo reemplaza.
    @PrimaryKey
    @ColumnInfo(name = "numberId")
    val numberId: Int = 0,

    @ColumnInfo(name = "data")
    val data: List<Data>,


    @ColumnInfo(name = "timestamp")
    val timestamp: Long
)

@Keep
data class Data(

    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "symbol")
    val symbol: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "priceUsd")
    val priceUsd: Float,

    @ColumnInfo(name = "supply")
    val supply:Float,

    @ColumnInfo(name = "marketCapUsd")
    val marketCapUsd:Float,

    @ColumnInfo(name = "changePercent24Hr")
    val changePercent24Hr:String
)