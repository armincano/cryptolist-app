package cl.armin20.cryptolist3.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.armin20.cryptolist3.R
import cl.armin20.cryptolist3.ui.utils.surfaceBgRadialGradient
import kotlinx.coroutines.delay

@Composable
//@Preview(showSystemUi = true, device = Devices.NEXUS_6)
fun SplashScreen(onItemClick: (route: String) -> Unit) {

    val splashViewModel:SplashViewModel = viewModel()

    LaunchedEffect(splashViewModel.isFirstRunDataStoreGot.value) {
        if (splashViewModel.isFirstRun.value) {
            onItemClick("welcomeScreen")
        } else {
            onItemClick("cryptocoins")
        }
    }
    // Why de delay? In MainActivity.kt we must check a DataStore value to set startDestination
    //depending on the value. This delay is the time it takes to check the value.

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
        .fillMaxSize()
        .surfaceBgRadialGradient()
        .padding(20.dp)){

        Image(
            painter = painterResource(id = R.drawable.cryptolist_logo),
            contentDescription = "cryptolist logo",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer),
            modifier = Modifier
                .size(369.dp)
        )

        Text(
            text = "CryptoList",
            style = MaterialTheme.typography.displayMedium,
        )

    }
}