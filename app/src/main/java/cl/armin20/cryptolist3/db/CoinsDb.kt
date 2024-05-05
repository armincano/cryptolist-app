package cl.armin20.cryptolist3.db


import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cl.armin20.cryptolist3.db.Converters
import cl.armin20.cryptolist3.model.CoinDetailItem
import cl.armin20.cryptolist3.model.Coins
import cl.armin20.cryptolist3.model.User


@Database(
    entities = [Coins::class, CoinDetailItem::class, User::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CoinsDb : RoomDatabase() {
    init {
        Log.i(ContentValues.TAG, "CoinsDb()")
    }
    abstract val dao: CoinsDao

    companion object {
        @Volatile
        private var INSTANCE: CoinsDao? = null

        fun getDaoInstance(context: Context): CoinsDao {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = buildDatabase(context).dao
                    INSTANCE = instance
                }
                return instance
            }
        }

        private fun buildDatabase(context: Context): CoinsDb =
            Room.databaseBuilder(
                context.applicationContext,
                CoinsDb::class.java,
                "coins_database")
                .fallbackToDestructiveMigration()
                .build()
    }
}