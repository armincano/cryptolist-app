package cl.armin20.cryptolist3.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val firstName: String,
    val avatar: String
)

data class Users (
    val data: List<User>,
)
