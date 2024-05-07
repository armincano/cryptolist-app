package cl.armin20.cryptolist3.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.armin20.cryptolist3.CryptoList2Application
import cl.armin20.cryptolist3.R
import cl.armin20.cryptolist3.ui.utils.surfaceBgRadialGradient

@Composable
//@Preview(showSystemUi = true, device = Devices.NEXUS_6)
fun ProfileScreen(onItemClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .surfaceBgRadialGradient()
            .padding(20.dp)
    )
    {
        val profileViewModel: ProfileViewModel = viewModel()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
        ) {

            val createAccountTextState = remember { mutableStateOf("") }
            val createAccountImg = remember { mutableStateOf("avatar_default") }

            Text(
                text = "Create a profile",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 20.dp)
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
                /*Text(
                    text = "Choose an avatar",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.height(10.dp))*/
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
                        .background(
                            if (createAccountTextState.value.isNotEmpty()) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onPrimaryContainer.copy(
                                alpha = 0.5f
                            )
                        )
                        .clickable(enabled = createAccountTextState.value.isNotEmpty()) {
                            profileViewModel.saveUserDB(
                                createAccountTextState.value,
                                createAccountImg.value
                            )
                            profileViewModel.saveUserDataStore(
                                createAccountTextState.value,
                                createAccountImg.value,
                                CryptoList2Application.getAppContext()
                            )
                            onItemClick()
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

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Change user",
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

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(modifier = Modifier.padding(horizontal = 26.dp)) {
                    items(profileViewModel.users.value) {
                        Row(
                            modifier = Modifier
                                .padding(10.dp)
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.onPrimaryContainer,
                                    RoundedCornerShape(18.dp)
                                )
                                .clip(RoundedCornerShape(18.dp))
                                .clickable {
                                    profileViewModel.selectedUserInChangeProfile.value = it
                                }
                        ) {
                            Text(
                                text = it.firstName,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }

                Text(
                    text = if (profileViewModel.selectedUserInChangeProfile.value.id != null) "${profileViewModel.selectedUserInChangeProfile.value.firstName} selected" else "Select a name and press",
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(10.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(50))
                        .background(
                            if (profileViewModel.selectedUserInChangeProfile.value.id != null) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onPrimaryContainer.copy(
                                alpha = 0.5f
                            )
                        )
                        .clickable(enabled = profileViewModel.selectedUserInChangeProfile.value.id != null) {
                            profileViewModel.saveUserDataStore(
                                profileViewModel.selectedUserInChangeProfile.value.firstName,
                                profileViewModel.selectedUserInChangeProfile.value.avatar,
                                CryptoList2Application.getAppContext()
                            )
                            onItemClick()
                        },
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.shoe),
                        contentDescription = "Save",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surface),
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }


        }
        BottomProfileScreen(profileViewModel, onItemClick)

    }
}

@Composable
fun BottomProfileScreen(
    profileViewModel: ProfileViewModel,
    onItemClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .weight(1f),
        ) {

            Text(
                text = "${profileViewModel.currentUserName.value} Profile",
                style = MaterialTheme.typography.bodyLarge,
            )

            Text(
                text = "You have ${profileViewModel.currentUserName.value} ⭐️ cryptos",
                style = MaterialTheme.typography.labelSmall,
            )

        }

        Row(horizontalArrangement = Arrangement.SpaceAround) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.onPrimaryContainer)
                    .clickable { onItemClick() }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Home",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .size(30.dp)
                )
            }

        }

    }
}
