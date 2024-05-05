package cl.armin20.cryptolist3.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//Preference Name
const val PREFERENCE_NAME = "userDataStore"

// Create the dataStore and give it a name same as shared preferences
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

//key under which the data is stored.
val FIRST_RUN_TIME_KEY = booleanPreferencesKey("first_run_time")
val USER_NAME_KEY = stringPreferencesKey("user_name")
val USER_AVATAR_KEY = stringPreferencesKey("user_avatar")

// suspend function that provides a MutablePreferences object inside its lambda,
// which you can use to modify the data in the DataStore.
//userName is the value that is being stored.
// is indeed replacing the value associated with the USER_NAME_KEY each time it's called.
suspend fun writeUserName(userName: String, context: Context) {
    context.dataStore.edit {
        it[USER_NAME_KEY] = userName
    }
}

suspend fun writeUserAvatar(userAvatar: String, context: Context) {
    context.dataStore.edit {
        it[USER_AVATAR_KEY] = userAvatar
    }
}

suspend fun writeFirstRun(context: Context) {
    context.dataStore.edit {
        it[FIRST_RUN_TIME_KEY] = false
    }
}

fun getFirstRunValue(context: Context): Flow<Boolean> {
    return context.dataStore.data.map {
        it[FIRST_RUN_TIME_KEY] ?: true
    }
}


// This function will return a flow with the value associated with the key USER_NAME_KEY.
fun getUserValue(userKey: String, context: Context): Flow<String> {
    return context.dataStore.data.map {
        it[stringPreferencesKey(userKey)] ?: ""
    }
}



