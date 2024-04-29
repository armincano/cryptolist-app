package cl.armin20.cryptolist3.ui


import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.armin20.cryptolist3.CryptoList2Application
import cl.armin20.cryptolist3.model.Data
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
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        stickyHeader {
            StickyHeader(cryptoViewModel, onItemClick = { id -> onItemClick(id) })
        }

        items(if (cryptoViewModel.searchTextField.value.isEmpty()) {
            cryptoViewModel.cryptoList.value.data
        } else cryptoViewModel.cryptoList.value.data.filter {
            it.id.contains(cryptoViewModel.searchTextField.value)
        }) {
            CryptoListItem(it, onItemClick = { id -> onItemClick(id) })
        }
    }

}

@Composable
fun StickyHeader(cryptoViewModel: CryptoViewModel, onItemClick: (id: String) -> Unit) {
    val welcomeViewModel: WelcomeViewModel = viewModel()

    welcomeViewModel.getName("user_name", CryptoList2Application.getAppContext())
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                onClick = { onItemClick("addUser") },
                modifier = Modifier
                    .size(width = 1900.dp, height = 47.dp)
            ) {

                Text(
                    text = "You are ${welcomeViewModel.userName}.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )

                Icon(
                    imageVector = Icons.Filled.Create, contentDescription = null
                )

            }
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
                label = { Text("Hi! Write to search") },
                textStyle = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(0.7f)
            )

            Button(
                onClick = { GlobalScope.launch(Dispatchers.IO) { cryptoViewModel.getCoins() } },
                modifier = Modifier.size(width = 80.dp, height = 50.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = null,
                )
            }
        }
    }
}

// @Preview(showSystemUi = true, device = Devices.NEXUS_6)
@Composable
fun CryptoListItem(item: Data, onItemClick: (id: String) -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 22.dp)
            .fillMaxWidth()
            .clickable { onItemClick("cryptocoins/${item.id}") }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(8.dp)
        ) {
//            CryptoIcon(Icons.Filled.Place, Modifier.weight(0.15f))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://static.coincap.io/assets/icons/${item.symbol.lowercase()}@2x.png")
                    .crossfade(true).build(),
                contentDescription = null,
                modifier = Modifier
                    .weight(2f)
                    .size(50.dp),
//                    .clip(RoundedCornerShape(10.dp)),
//                    .background(Color.White),
//                    .clip(CircleCropTransformation())
//                placeholder = painterResource(R.drawable.ic_baseline_face1),
                contentScale = ContentScale.Fit
            )
            Text(
                text = item.symbol.lowercase(),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(2f)
            )
            Text(
                text = item.priceUsd.toString(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .weight(2.5f)
                    .padding(end = 25.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}



