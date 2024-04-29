package cl.armin20.cryptolist3.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.armin20.cryptolist3.R
import coil.compose.AsyncImage
import coil.request.ImageRequest
import cl.armin20.cryptolist3.ui.theme.decreaseColor
import cl.armin20.cryptolist3.ui.theme.increaseColor

@Composable
fun CryptoDetailsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
//
        Image(
            painter = painterResource(R.drawable.bg_details),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter),
            contentScale = ContentScale.FillWidth,
        )

        Column {
            SymbolSection()
            Spacer(modifier = Modifier.height(25.dp))
            CardSection()
            Spacer(modifier = Modifier.height(35.dp))
            OthersValuesSection()
        }

    }
}

@Composable
fun SymbolSection() {
    val cryptoDetailsViewModel: CryptoDetailsViewModel = viewModel()
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 25.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(start = 5.dp)
        ) {
            Text(
//                text = "btc",
                text = cryptoDetailsViewModel.cryptoDetail.value.data.symbol.lowercase(),
                style = MaterialTheme.typography.displaySmall,
                overflow = TextOverflow.Ellipsis
            )
            Text(
//                text = "bitcoin",
                text = cryptoDetailsViewModel.cryptoDetail.value.data.name,
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis,
            )
        }
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://static.coincap.io/assets/icons/${cryptoDetailsViewModel.cryptoDetail.value.data.symbol.lowercase()}@2x.png")
//                .data("https://static.coincap.io/assets/icons/bitcoin@2x.png")
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(85.dp)
                .padding(top = 7.dp),
//                    .clip(RoundedCornerShape(10.dp)),
//                    .background(Color.White),
//                    .clip(CircleCropTransformation())
//            error = painterResource(R.drawable.ic_baseline_broken_image),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun CardSection() {
    val cryptoDetailsViewModel: CryptoDetailsViewModel = viewModel()
//    val textex1 = "bitcoin"
//    val textex2 = 123.20219
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .fillMaxWidth()
        ) {
            Text(
                text = "${cryptoDetailsViewModel.cryptoDetail.value.data.priceUsd}",
//            text = textex2.toString(),
                style = MaterialTheme.typography.headlineLarge,
                overflow = TextOverflow.Clip,
                maxLines = 1
            )

            Text(
                text = "US Dollar",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "${cryptoDetailsViewModel.getDateTime(cryptoDetailsViewModel.cryptoDetail.value.timestamp)} hrs.",
//                text = "12:23:23 hrs.",
                style = MaterialTheme.typography.bodyLarge
            )
        }

    }
}

@Composable
fun OthersValuesSection() {
    val cryptoDetailsViewModel: CryptoDetailsViewModel = viewModel()
//    val textex1 = "bitcoin"
//    val textex2 = 123.20219
    Column(
        modifier = Modifier
            .padding(horizontal = 40.dp)
    ) {
        Card(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
        ) {
            Row(modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer).padding(start = 10.dp, top = 15.dp, bottom = 10.dp)) {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = "Supply",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    modifier = Modifier
                        .weight(2f),
                    text = cryptoDetailsViewModel.cryptoDetail.value.data.supply.toString(),
//                text = textex2.toString(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Row(modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer).padding(bottom = 20.dp, start = 10.dp)) {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = "Market cap",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    modifier = Modifier
                        .weight(2f),
                    text = cryptoDetailsViewModel.cryptoDetail.value.data.marketCapUsd.toString(),
//                text = textex2.toString(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(
                    if (cryptoDetailsViewModel.cryptoDetail.value.data.changePercent24Hr.contains("-")) {
                        decreaseColor
                    } else {
                        increaseColor
                    }
                ),
        ) {
            Text(
                text = cryptoDetailsViewModel.cryptoDetail.value.data.changePercent24Hr,
//            text = textex2.toString(),
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "value change in the last 24 hrs.",
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
    }
}





