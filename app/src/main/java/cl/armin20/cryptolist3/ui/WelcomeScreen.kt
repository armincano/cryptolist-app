package cl.armin20.cryptolist3.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.armin20.cryptolist3.CryptoList2Application
import cl.armin20.cryptolist3.R
import cl.armin20.cryptolist3.data.local.writeFirstRun
import cl.armin20.cryptolist3.ui.utils.surfaceBgRadialGradient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
//@Preview(showSystemUi = true, device = Devices.NEXUS_6)
fun WelcomeScreen(onItemClick: () -> Unit) {

    val welcomeViewModel: WelcomeViewModel = viewModel()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .surfaceBgRadialGradient()
            .padding(10.dp)
    ) {

        val createAccountTextState = remember { mutableStateOf("") }
        val createAccountImg = remember { mutableStateOf("avatar_default") }

        Text(
            text = "Create an account",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(20.dp)
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.onPrimaryContainer,
                    RoundedCornerShape(18.dp)
                )
                .clip(RoundedCornerShape(18.dp))
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Choose an avatar",
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.avatar_alien),
                    contentDescription = "user 1 avatar",
                    modifier = Modifier
                        .size(80.dp)
                        .clickable { createAccountImg.value = "avatar_alien" }
                )

                Image(
                    painter = painterResource(id = R.drawable.avatar_girl),
                    contentDescription = "user 1 avatar",
                    modifier = Modifier
                        .size(80.dp)
                        .clickable { createAccountImg.value = "avatar_girl" }
                )

                Image(
                    painter = painterResource(id = R.drawable.avatar_robot),
                    contentDescription = "user 1 avatar",
                    modifier = Modifier
                        .size(80.dp)
                        .clickable { createAccountImg.value = "avatar_robot" }
                )

            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = createAccountTextState.value,
                onValueChange = { newValue ->
                    createAccountTextState.value = newValue
                },
                singleLine = true,
                label = { Text("Write your name") },
                textStyle = MaterialTheme.typography.bodyLarge,
                shape = MaterialTheme.shapes.large,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
            )
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.onPrimaryContainer)
                    .clickable(enabled = createAccountTextState.value.isNotEmpty()) {
                        welcomeViewModel.saveFirstRunAndUserDBAndUserDataStore(
                            CryptoList2Application.getAppContext(),
                            createAccountTextState.value,
                            createAccountImg.value,
                            onItemClick
                        )
                    },
            ) {
                Image(
                    painter = painterResource(id = R.drawable.save),
                    contentDescription = "Save",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .size(30.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
