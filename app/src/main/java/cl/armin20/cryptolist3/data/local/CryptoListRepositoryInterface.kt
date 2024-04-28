package cl.armin20.cryptolist3.data.local

import cl.armin20.cryptolist3.model.CoinDetailItem
import cl.armin20.cryptolist3.model.Coins

interface CryptoListRepositoryInterface {
    suspend fun getAll():Coins
    suspend fun getSingle(id: String): CoinDetailItem
}