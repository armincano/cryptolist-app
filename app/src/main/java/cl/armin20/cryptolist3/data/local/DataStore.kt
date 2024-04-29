package cl.armin20.cryptolist3.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

    //Preference Name
    const val PREFERENCE_NAME = "userDataStore"
    // for saving and retrieving data from the preference Data Store
    // Create the dataStore and give it a name same as shared preferences
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

    // Create some keys we will use them to store and retrieve the data
    //3. Create keys for the key part of the key-value pairs (preferencesKeys)
    val USER_AGE_KEY = intPreferencesKey("user_age")
    val USER_NAME_KEY = stringPreferencesKey("user_name")

    // Store user data we can store values using the keys
    // The edit() function is a suspend function
    // Inside the lambda we have access to MutablePreferences, so we can change the value under the specified key.
    suspend fun writeUserName(userName: String, context: Context) {
    context.dataStore.edit {
            it[USER_NAME_KEY] = userName
        }
    }

    // This function will return a flow with every data in the
    // datastore, so we need to read the data with the key
    suspend fun getPreferences(context: Context): Flow<Preferences> {
        return context.dataStore.data
    }

    // This function will map the Flow<Preferences> into a Flow<Int>
    fun getUserName(key: String, context: Context): Flow<String> {
        return context.dataStore.data.map {
            it[stringPreferencesKey(key)] ?: ""
        }
    }



