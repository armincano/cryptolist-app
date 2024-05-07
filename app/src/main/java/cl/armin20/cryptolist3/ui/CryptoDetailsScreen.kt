package cl.armin20.cryptolist3.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cl.armin20.cryptolist3.R
import cl.armin20.cryptolist3.ui.utils.cardBgLinearGradient
import cl.armin20.cryptolist3.ui.utils.surfaceBgRadialGradient
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun CryptoDetailsScreen(navController: NavController, onItemClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .surfaceBgRadialGradient()
            .padding(20.dp)
    )
    {

        val cryptoDetailsViewModel: CryptoDetailsViewModel = viewModel()

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(15.dp)
        ) {
            CardHero(cryptoDetailsViewModel)
            Spacer(modifier = Modifier.height(25.dp))
            PriceSection(cryptoDetailsViewModel)
            Spacer(modifier = Modifier.height(35.dp))
            OthersValuesSection(cryptoDetailsViewModel)
        }
        BottomDetailsScreen(cryptoDetailsViewModel, onItemClick, navController)

    }
}

//@Preview(showSystemUi = true, device = Devices.NEXUS_6)
@Composable
fun CardHero(cryptoDetailsViewModel: CryptoDetailsViewModel) {
    Box(
        modifier = Modifier
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
            AsyncImage(
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
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
//                text = "Bitcoin",
                text = cryptoDetailsViewModel.cryptoDetail.value.data.name,
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
//                text = "btc",
                text = cryptoDetailsViewModel.cryptoDetail.value.data.symbol,
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

//@Preview(showSystemUi = true, device = Devices.NEXUS_6)
@Composable
fun PriceSection(cryptoDetailsViewModel: CryptoDetailsViewModel) {
//    val textex1 = "bitcoin"
//    val textex2 = 123.20219
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "${cryptoDetailsViewModel.cryptoDetail.value.data.priceUsd}",
//            text = textex2.toString(),
            style = MaterialTheme.typography.displaySmall,
            overflow = TextOverflow.Clip,
            maxLines = 1
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = "USD",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(bottom = 3.dp),
        )
    }

}

//@Preview(showSystemUi = true, device = Devices.NEXUS_6)
@Composable
fun OthersValuesSection(cryptoDetailsViewModel: CryptoDetailsViewModel) {
//    val textex1 = "bitcoin"
//    val textex2 = 123.20219
    Column(
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = "${cryptoDetailsViewModel.cryptoDetail.value.data.supply}",
//                    text = textex2.toString(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = "Supply",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${cryptoDetailsViewModel.cryptoDetail.value.data.marketCapUsd}",
//                    text = textex2.toString(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = "Market cap",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cryptoDetailsViewModel.cryptoDetail.value.data.changePercent24Hr,
//                    text = textex2.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = "value change in the last 24 hrs.",
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.weight(1f),
            ) {
                if (cryptoDetailsViewModel.cryptoDetail.value.data.changePercent24Hr.contains("-")) {
                    Image(
                        painter = painterResource(id = R.drawable.decrease),
                        contentDescription = "Decrease value icon",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.increase),
                        contentDescription = "Increase value icon",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                    )
                }

            }
        }
    }
}

@Composable
fun BottomDetailsScreen(
    cryptoDetailsViewModel: CryptoDetailsViewModel,
    onItemClick: () -> Unit,
    navController: NavController
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Text(
            text = "Updated on ${cryptoDetailsViewModel.getVersionDate(cryptoDetailsViewModel.cryptoDetail.value.timestamp)} 🔄",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .weight(1f),
        )

        Row(horizontalArrangement = Arrangement.SpaceAround) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.onPrimaryContainer)
                    .clickable { navController.popBackStack() }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Home",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .size(30.dp)
                )
            }

            Spacer(modifier = Modifier.width(30.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.onPrimaryContainer)
                    .clickable {
                        GlobalScope.launch(Dispatchers.IO) {
                            cryptoDetailsViewModel.getDetailCoin(
                                cryptoDetailsViewModel.id
                            )
                        }
                    },
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
}





