package dev.chamo.millieapp.feature.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import dev.chamo.millieapp.core.designsystem.component.MillieAppLoadingView
import dev.chamo.millieapp.core.designsystem.component.MillieAppTopAppBar
import dev.chamo.millieapp.core.designsystem.icon.MillieAppIcons
import dev.chamo.millieapp.core.model.TopHeadline

@Composable
internal fun NewsRoute(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel(),
    onNewsClick: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    val topHeadLinesUiState by viewModel.topHeadLinesUiState.collectAsStateWithLifecycle()

    NewsScreen(
        modifier = modifier,
        topHeadLinesUiState = topHeadLinesUiState,
        onNewsClick = { url ->
            viewModel.setHeadLineSelected(url)
            onNewsClick(url)
        },
        onNavigationClick = onBackClick
    )
}

@Composable
internal fun NewsScreen(
    modifier: Modifier = Modifier,
    topHeadLinesUiState: TopHeadLinesUiState,
    onNewsClick: (String) -> Unit,
    onNavigationClick: () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        MillieAppTopAppBar(
            modifier = Modifier,
            navigationIcon = MillieAppIcons.ArrowBack,
            titleRes = R.string.feature_news_top_headlines,
            onNavigationClick = onNavigationClick
        )

        when (topHeadLinesUiState) {
            TopHeadLinesUiState.Loading -> {
                MillieAppLoadingView()
            }

            TopHeadLinesUiState.Error -> {
                ErrorNewsResultBody()
            }

            is TopHeadLinesUiState.Success -> {
                if (topHeadLinesUiState.isEmpty()) {
                    EmptyNewsResultBody()

                } else {
                    val scrollableState = rememberLazyGridState()
                    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
                    val columns = if (screenWidth >= 600.dp) 3 else 1

                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxSize(),
                        columns = GridCells.Fixed(columns),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        state = scrollableState
                    ) {
                        items(topHeadLinesUiState.topHeadlines) {
                            TopHeadLineItem(
                                topHeadLine = it,
                                onNewsClick = onNewsClick
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun TopHeadLineItem(
    topHeadLine: TopHeadline,
    onNewsClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .clickable(onClick = {
                onNewsClick(topHeadLine.url)
            })
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = topHeadLine.sourceName,
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 4.dp),
                text = stringResource(R.string.feature_news_dot),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = topHeadLine.publishedAt,
                style = MaterialTheme.typography.labelLarge
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = topHeadLine.title,
            style = MaterialTheme.typography.titleLarge.copy(
                color = if (topHeadLine.isSelected) MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.onBackground
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )

        if (topHeadLine.description.isNotBlank()) {
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = topHeadLine.description,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )

                if (topHeadLine.urlToImage.isNotBlank()) {
                    Spacer(modifier = Modifier.width(16.dp))
                    AsyncImage(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        model = topHeadLine.urlToImage,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(thickness = 1.dp)
    }
}

@Composable
internal fun EmptyNewsResultBody() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.feature_news_no_top_headlines),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
internal fun ErrorNewsResultBody() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.align(
                alignment = Alignment.Center
            ),
            text = stringResource(R.string.feature_news_error_occurred),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview
@Composable
private fun NewsScreenPreview() {
    NewsScreen(
        topHeadLinesUiState = TopHeadLinesUiState.Success(
            listOf(
                TopHeadline(
                    sourceName = "New York Post",
                    author = "Whitney Vasquez",
                    title = "Mike Tyson issues bizarre message to Diddy amid sex-trafficking scandal: ‘I wish him all the best’ - New York Post",
                    description = "Mike Tyson had some interesting words for his longtime pal Diddy.",
                    url = "",
                    urlToImage = "https://nypost.com/wp-content/uploads/sites/2/2024/10/i-wish-best-91446631.jpg?quality=75&strip=all&w=1024",
                    publishedAt = "2024-10-11T17:57:00Z",
                    content = ""
                )
            )
        ),
        onNewsClick = {},
        onNavigationClick = {}
    )
}

@Preview(device = Devices.TABLET)
@Composable
private fun NewsScreenTabletPreview() {
    NewsScreen(
        topHeadLinesUiState = TopHeadLinesUiState.Success(
            listOf(
                TopHeadline(
                    sourceName = "New York Post",
                    author = "Whitney Vasquez",
                    title = "Mike Tyson issues bizarre message to Diddy amid sex-trafficking scandal: ‘I wish him all the best’ - New York Post",
                    description = "Mike Tyson had some interesting words for his longtime pal Diddy.",
                    url = "",
                    urlToImage = "https://nypost.com/wp-content/uploads/sites/2/2024/10/i-wish-best-91446631.jpg?quality=75&strip=all&w=1024",
                    publishedAt = "1시간 전",
                    content = ""
                ),
                TopHeadline(
                    sourceName = "The New Republic",
                    author = "Edith Olmsted",
                    title = "TikTok Is Dangerously Addictive—and Its Executives Knew All Along - Yahoo! Voices",
                    description = "It’s easy for children and teenagers to get hooked on TikTok, and the company higher-ups aren’t doing anything about it.",
                    url = "",
                    urlToImage = "https://images.newrepublic.com/a04a596b419fac95af7c8a8dd628184043ec991e.jpeg?w=1200&h=630&crop=faces&fit=crop&fm=jpg",
                    publishedAt = "7시간 전",
                    content = ""
                ),
                TopHeadline(
                    sourceName = "Associated Press",
                    author = "JESSE BEDAYN, MATTHEW BROWN",
                    title = "An elevator mishap at a Colorado tourist mine killed 1 and trapped 12. The cause is still unknown - The Associated Press",
                    description = "Investigators were trying to figure out Friday what led an elevator to malfunction at a former Colorado gold mine, killing one person. Four others were injured and 12 people were trapped for hours at the bottom of the tourist attraction 1,000 feet (305 meters…",
                    url = "",
                    urlToImage = "https://dims.apnews.com/dims4/default/8d74bef/2147483647/strip/true/crop/4565x2568+0+399/resize/1440x810!/quality/90/?url=https%3A%2F%2Fassets.apnews.com%2F24%2F27%2Fdaa2cc3682d6380718cea0efa01d%2F40a86f2b3af0460ea40b54b878a91c2d",
                    publishedAt = "2일 전",
                    content = ""
                )
            )
        ),
        onNewsClick = {},
        onNavigationClick = {}
    )
}

@Preview
@Composable
private fun NewsScreenPreviewNoNews() {
    NewsScreen(
        topHeadLinesUiState = TopHeadLinesUiState.Success(
            emptyList()
        ),
        onNewsClick = {},
        onNavigationClick = {}
    )
}


@Preview
@Composable
private fun NewsScreenPreviewErrorOccurred() {
    NewsScreen(
        topHeadLinesUiState = TopHeadLinesUiState.Error,
        onNewsClick = {},
        onNavigationClick = {}
    )
}