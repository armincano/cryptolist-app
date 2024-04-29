package cl.armin20.cryptolist3.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.armin20.cryptolist3.CryptoList2Application

@Composable
//@Preview(showSystemUi = true, device = Devices.NEXUS_6)
fun WelcomeScreen(onItemClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        val welcomeViewModel: WelcomeViewModel = viewModel()
        val textState = remember { mutableStateOf("") }

        OutlinedTextField(
            value = textState.value,
            onValueChange = { newValue ->
                textState.value = newValue
            },
            label = { Text("Write your name") },
            textStyle = MaterialTheme.typography.bodyMedium
        )

        Button(
            enabled = textState.value.isNotEmpty(), onClick = {
                Log.d("userPrefTextstatValue", textState.value)
                welcomeViewModel.saveUserName(
                    textState.value, CryptoList2Application.getAppContext()
                )
                onItemClick()
            }, modifier = Modifier.size(width = 240.dp, height = 60.dp)
        ) {
            Text(
                text = "SAVE YOUR NAME",
                style = MaterialTheme.typography.bodyMedium,
            )
        }


    }
}
