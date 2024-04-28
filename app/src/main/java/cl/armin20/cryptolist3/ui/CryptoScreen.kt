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
import cl.armin20.cryptolist3.model.Data
import cl.armin20.cryptolist3.ui.theme.PurpleSoft
import cl.armin20.cryptolist3.ui.theme.SilverSoft
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// @Preview(showSystemUi = true, device = Devices.NEXUS_6)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CryptoScreen(){
    Log.i(TAG, "in CryptoScreen() now")
    //Instanciar el ViewModel de forma correcta, sin entregar parámetros
    val cryptoViewModel: CryptoViewModel = viewModel()
    Log.i(TAG, "example after viewModel() now")
    LazyColumn(
        contentPadding = PaddingValues(
            vertical = 8.dp,
            horizontal = 8.dp
        ),
        modifier = Modifier
            .fillMaxSize()
            .background(PurpleSoft)
    ){

        items(cryptoViewModel.cryptoList.value.data){
            println("example $it")
            CryptoListItem(
                it)

        }
    }

}

//@Preview(showSystemUi = true, device = Devices.NEXUS_6)
@Composable
fun CryptoListItem(item: Data){
    Card(
       // elevation = 4.dp,
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .size(height = 90.dp, width = 0.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = CenterVertically,
            modifier = Modifier.background(SilverSoft)
        ) {
//            CryptoIcon(Icons.Filled.Place, Modifier.weight(0.15f))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://static.coincap.io/assets/icons/${item.symbol.lowercase()}@2x.png")
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .weight(0.27f)
                    .size(60.dp),
//                    .clip(RoundedCornerShape(10.dp)),
//                    .background(Color.White),
//                    .clip(CircleCropTransformation())
//                placeholder = painterResource(R.drawable.ic_baseline_face1),
                contentScale = ContentScale.Fit
            )
            Text(
                text = item.symbol.lowercase(),
                style = MaterialTheme.typography.titleMedium.copy(color = Color.White),
                modifier = Modifier.weight(0.33f)
            )
            Text(
                text = item.priceUsd.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                modifier = Modifier
                    .weight(0.40f)
                    .padding(end = 25.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}



