package cl.armin20.cryptolist3.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.armin20.cryptolist3.CryptoList2Application
import cl.armin20.cryptolist3.R

@Composable
//@Preview(showSystemUi = true, device = Devices.NEXUS_6)
fun WelcomeScreen(onItemClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
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
            singleLine = true,
            label = { Text("Write your name") },
            textStyle = MaterialTheme.typography.bodyLarge,
            shape = MaterialTheme.shapes.large,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
        )

        Spacer(modifier = Modifier.height(40.dp))

        Row (
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 60.dp)
        ){

            Button(
                enabled = textState.value.isNotEmpty(),
                onClick = {
                    Log.d("userPrefTextstatValue", textState.value)
                    welcomeViewModel.saveUserName(
                        textState.value, CryptoList2Application.getAppContext()
                    )
                    onItemClick()
                },
                modifier = Modifier.size(90.dp, 60.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.save),
                    contentDescription = "Save",
                    tint = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(30.dp))

            Button(
                onClick = {
                    onItemClick()
                },
                modifier = Modifier.size(90.dp, 60.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Home",
                    tint = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.fillMaxSize()
                )
            }

        }


    }
}
