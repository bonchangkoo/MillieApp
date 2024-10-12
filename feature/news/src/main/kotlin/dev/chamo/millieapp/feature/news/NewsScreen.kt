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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import dev.chamo.millieapp.core.designsystem.component.MillieAppLoadingView
import dev.chamo.millieapp.core.designsystem.component.MillieAppTopAppBar
import dev.chamo.millieapp.core.designsystem.icon.MillieAppIcons
import dev.chamo.millieapp.core.model.TopHeadLine

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
                    val scrollableState = rememberLazyListState()
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
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
    topHeadLine: TopHeadLine,
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
                    maxLines = 4,
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
                TopHeadLine(
                    sourceName = "연합뉴스",
                    author = "홍길동",
                    title = "휠체어 타고 장난감 총 들고 강도짓 벌인 2인조 체포",
                    description = "휠체어를 타고 강도 행각을 벌인 아르헨티나 청년이 공범과 함께 경찰에 붙잡혔다. 범행에 사용한 권총은 장난감 총이었다.",
                    url = "",
                    urlToImage = "urlToImage",
                    publishedAt = "",
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