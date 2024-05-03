package cl.armin20.cryptolist3.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.armin20.cryptolist3.CryptoList2Application
import cl.armin20.cryptolist3.R
import cl.armin20.cryptolist3.model.Data
import cl.armin20.cryptolist3.ui.theme.bgRadialGradient2
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// @Preview(showSystemUi = true, device = Devices.NEXUS_6)
// To achieve a sticky header with LazyColumn, you can use the experimental StickyHeader component.
// Must use the ExperimentalFoundationApi annotation to use the StickyHeader component.
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CryptoScreen(onItemClick: (id: String) -> Unit) {
    //Instanciar el ViewModel de forma correcta, sin entregar parámetros
    val cryptoViewModel: CryptoViewModel = viewModel()
    Column {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .weight(1f)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            bgRadialGradient2,
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
        ) {
            stickyHeader {
                StickyHeader(cryptoViewModel, onItemClick = { id -> onItemClick(id) })
            }

            items(
                if (cryptoViewModel.searchTextField.value.isEmpty()) {
                    cryptoViewModel.cryptoList.value.data
                } else cryptoViewModel.cryptoList.value.data.filter {
                    it.id.contains(cryptoViewModel.searchTextField.value)
                }
            ) {
                CryptoListItem(it, onItemClick = { id -> onItemClick(id) })
            }

        }
        BottomCryptoScreen(cryptoViewModel)
    }

}

// @Preview(showSystemUi = true, device = Devices.NEXUS_6)
@Composable
fun StickyHeader(cryptoViewModel: CryptoViewModel, onItemClick: (id: String) -> Unit) {
    val welcomeViewModel: WelcomeViewModel = viewModel()
    welcomeViewModel.getFirstUserName("user_name", CryptoList2Application.getAppContext())

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(bottom = 15.dp, top = 10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = "Hey, ${welcomeViewModel.currentUserName}!",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                Text(
                    text = "Good crypto hunt 😄",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.user_1),
                contentDescription = "Button Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.clickable { onItemClick("addUser") }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val textState = remember { mutableStateOf("") }
            cryptoViewModel.searchTextField.value = textState.value

            OutlinedTextField(
                value = textState.value,
                onValueChange = { newValue ->
                    textState.value = newValue
                },
                singleLine = true,
                label = { Text("Search a crypto") },
                textStyle = MaterialTheme.typography.bodyLarge,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
            )
        }
    }
}

// @Preview(showSystemUi = true, device = Devices.NEXUS_6)
@Composable
fun CryptoListItem(item: Data, onItemClick: (id: String) -> Unit) {
    OutlinedCard(
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimaryContainer),
        modifier = Modifier
            .padding(vertical = 5.dp, horizontal = 15.dp)
            .fillMaxWidth()
            .clickable { onItemClick("cryptocoins/${item.id}") }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .padding(8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .weight(0.13f)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://static.coincap.io/assets/icons/${item.symbol.lowercase()}@2x.png")
                        .crossfade(true).build(),
                    contentDescription = "crypto icon",
                    modifier = Modifier
                        .size(60.dp),
//                    .clip(RoundedCornerShape(10.dp)),
//                    .background(Color.White),
//                    .clip(CircleCropTransformation())
//                placeholder = painterResource(R.drawable.ic_baseline_face1),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.width(30.dp))

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .weight(0.2f)
            ) {
                Text(
                    text = "$ ${item.priceUsd}",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(end = 25.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun BottomCryptoScreen(cryptoViewModel: CryptoViewModel) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 15.dp)
    ) {

        Text(
            text = "Scroll the cryptos 👆",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 10.dp)
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(50))
                .background(MaterialTheme.colorScheme.onPrimaryContainer)
                .clickable { GlobalScope.launch(Dispatchers.IO) { cryptoViewModel.getCoins() } },
        ) {
            Image(
                painter = painterResource(id = R.drawable.update),
                contentDescription = "Update",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surface),
                modifier = Modifier
                    .size(30.dp)
            )
        }

    }

}