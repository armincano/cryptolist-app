package cl.armin20.cryptolist3.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cl.armin20.cryptolist3.ui.theme.CryptolistTheme

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

    NavHost(navController, startDestination = "cryptocoins") {
        composable(route = "addUser") {
            WelcomeScreen {
                navController.navigate("cryptocoins")
            }
        }
        composable(route = "cryptocoins") {
            CryptoScreen {
                navController.navigate(it)
            }
        }
        composable(
            route = "cryptocoins/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) { CryptoDetailsScreen{ navController.navigate("cryptocoins")}}
    }
}