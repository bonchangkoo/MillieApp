package dev.chamo.millieapp.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chamo.millieapp.core.designsystem.icon.MillieAppIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MillieAppTopAppBar(
    modifier: Modifier = Modifier,
    navigationIcon: ImageVector,
    @StringRes titleRes: Int,
    onNavigationClick: () -> Unit = {},
) {
    Column {
        CenterAlignedTopAppBar(
            modifier = modifier,
            title = {
                Text(text = stringResource(id = titleRes))
            },
            navigationIcon = {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = null
                    )
                }
            },
        )
        HorizontalDivider(
            thickness = 1.dp,
        )
    }

}

@Preview
@Composable
private fun MillieAppTopAppBarPreview() {
    MillieAppTopAppBar(
        navigationIcon = MillieAppIcons.ArrowBack,
        titleRes = android.R.string.untitled,
    )
}