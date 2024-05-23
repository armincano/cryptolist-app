package cl.armin20.cryptolist3.ui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import cl.armin20.cryptolist3.CryptoList2Application
import cl.armin20.cryptolist3.R
import cl.armin20.cryptolist3.model.Data
import cl.armin20.cryptolist3.ui.theme.cardBgGradientExt
import cl.armin20.cryptolist3.ui.utils.surfaceBgRadialGradient
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
    val navController = rememberNavController()

    LaunchedEffect(navController) {
        cryptoViewModel.getAllStarredCryptos()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .surfaceBgRadialGradient()
            .padding(20.dp)
    ) {

        Header(cryptoViewModel, onItemClick = { id -> onItemClick(id) })
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.onPrimaryContainer,
                    RoundedCornerShape(18.dp)
                )
                .clip(RoundedCornerShape(18.dp))
        ) {
            LazyColumn(
            ) {
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
        }
        Spacer(modifier = Modifier.height(10.dp))
        BottomCryptoScreen(cryptoViewModel, onItemClick = { id -> onItemClick(id) })
    }
}


// @Preview(showSystemUi = true, device = Devices.NEXUS_6)
@Composable
fun Header(cryptoViewModel: CryptoViewModel, onItemClick: (id: String) -> Unit) {

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Hey, ${cryptoViewModel.currentUserName.value}!",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                Text(
                    text = "You have ${cryptoViewModel.starredCryptoList.value.size} ⭐️ cryptos",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }

            val context = LocalContext.current
            val drawableName = cryptoViewModel.currentUserAvatar.value
            val resourceId = context.resources.getIdentifier(
                drawableName,
                "drawable",
                context.packageName
            )
            AsyncImage(
                model = resourceId,
                contentDescription = "user avatar",
                modifier = Modifier
                    .size(60.dp)
                    .clickable { onItemClick("profileScreen") },
                contentScale = ContentScale.Fit
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 9.dp, start = 16.dp, end = 16.dp)
            .clickable { onItemClick("cryptocoins/${item.id}") },
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimaryContainer),
        colors = CardDefaults.cardColors(
            containerColor = cardBgGradientExt,
//            disabledContainerColor = Color.DarkGray,
//            contentColor = Color.Black
        ),
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
fun BottomCryptoScreen(cryptoViewModel: CryptoViewModel, onItemClick: (id: String) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
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
                .clickable { },
        ) {
            Image(
                painter = painterResource(id = R.drawable.star_profile),
                contentDescription = "Starred cryptos",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surface),
                modifier = Modifier
                    .size(30.dp)
            )
        }

    }

}