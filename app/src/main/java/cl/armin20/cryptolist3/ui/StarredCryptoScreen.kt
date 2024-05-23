package cl.armin20.cryptolist3.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.armin20.cryptolist3.CryptoList2Application
import cl.armin20.cryptolist3.R
import cl.armin20.cryptolist3.ui.utils.cardBgLinearGradient
import cl.armin20.cryptolist3.ui.utils.surfaceBgRadialGradient
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
//@Preview(showSystemUi = true, device = Devices.NEXUS_6)
fun StarredCryptoScreen(onItemClick: (route: String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .surfaceBgRadialGradient()
            .padding(20.dp)
    )
    {
        val starredCryptoViewModel: StarredCryptoViewModel = viewModel()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
        ) {


            Text(
                text = "Armin cryptos",
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
                LazyColumn(modifier = Modifier.padding(horizontal = 26.dp)) {
                    items(starredCryptoViewModel.starredCryptoList.value) {
                        CardHeroStarred(it.id, starredCryptoViewModel)
                    }
                }

            }

        }
        BottomStarredCryptoScreen(starredCryptoViewModel, onItemClick)

    }
}

//@Preview(showSystemUi = true, device = Devices.NEXUS_6)
@Composable
fun CardHeroStarred(item:String, starredCryptoViewModel: StarredCryptoViewModel) {

    Box(
        modifier = Modifier
            .padding(vertical = 9.dp)
            .cardBgLinearGradient()
            .size(280.dp)
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimaryContainer),
                shape = RoundedCornerShape(30.dp)
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(25.dp)
        ) {
            /* AsyncImage(
                 model = ImageRequest.Builder(LocalContext.current)
                     .data("https://static.coincap.io/assets/icons/${cryptoDetailsViewModel.cryptoDetail.value.data.symbol.lowercase()}@2x.png")
                     .crossfade(true)
                     .build(),
                 contentDescription = "crypto icon",
                 modifier = Modifier
                     .size(125.dp)
                     .padding(top = 7.dp),
 //                            error = painterResource(R.drawable.ic_baseline_broken_image),
                 contentScale = ContentScale.Fit
             )*/

            Spacer(modifier = Modifier.height(10.dp))

            Text(
//                text = "Bitcoin",
                text = item,
                style = MaterialTheme.typography.displaySmall.copy(
                    shadow = Shadow(
                        color = MaterialTheme.colorScheme.onPrimary,
                        blurRadius = 40f,
                    )
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = "btc",
//                text = cryptoDetailsViewModel.cryptoDetail.value.data.symbol,
                style = MaterialTheme.typography.headlineMedium.copy(
                    shadow = Shadow(
                        color = MaterialTheme.colorScheme.onPrimary,
                        blurRadius = 20f,
                    )
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }

    }
}

@Composable
fun BottomStarredCryptoScreen(
    starredCryptoViewModel: StarredCryptoViewModel,
    onItemClick: (route: String) -> Unit,
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
                text = "${starredCryptoViewModel.starredCryptoList.value.size} ⭐️",
                style = MaterialTheme.typography.bodyLarge,
            )

            Text(
                text = "Updated on  🔄",
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
                    .clickable { onItemClick("cryptocoins") }
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