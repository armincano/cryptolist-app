package cl.armin20.cryptolist3.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cl.armin20.cryptolist3.CryptoList2Application
import cl.armin20.cryptolist3.data.local.getFirstRunValue
import cl.armin20.cryptolist3.data.local.writeFirstRun
import cl.armin20.cryptolist3.data.local.writeUserAvatar
import cl.armin20.cryptolist3.data.local.writeUserName
import cl.armin20.cryptolist3.ui.theme.CryptolistTheme
import cl.armin20.cryptolist3.ui.utils.DataStoreUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptolistTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    CryptoListApp()
                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun CryptoListApp() {
    val navController = rememberNavController()
    val startDestination = "splashScreen"

    NavHost(navController, startDestination = startDestination) {

        composable(route = "splashScreen") {
            SplashScreen {string ->
                navController.navigate(string)
            }
        }

        composable(route = "welcomeScreen") {
            WelcomeScreen {
                navController.navigate("splashScreen")
            }
        }

        composable(route = "profileScreen") {
            ProfileScreen { string ->
                navController.navigate(string)
            }
        }

        composable(route = "starredCryptoScreen") {
            StarredCryptoScreen {string ->
                navController.navigate(string)
            }
        } // The StarredCryptoScreen composable has callback 'onItemClick: () -> Unit'
        // with no parameter so it will navigate to the hardcoded string in this file

        composable(route = "cryptocoins") {
            CryptoScreen { string ->
                navController.navigate(string)
            }
        } // The CryptoScreen composable has the callback 'onItemClick: (id: String) -> Unit'
        // with the parameter 'id' so it will navigate to the received argument, here is named "string"

        composable(
            route = "cryptocoins/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) { CryptoDetailsScreen(navController) { navController.navigate("cryptocoins") } }
    }
}