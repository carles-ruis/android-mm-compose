package com.carles.mm.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import com.carles.common.ui.TopBarItem
import com.carles.common.ui.navigation.Destination
import com.carles.mm.R
import org.w3c.dom.Text

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun TopBar(
    title: String,
    showBackButton: Boolean,
    menuItems: List<TopBarItem>,
    modifier: Modifier = Modifier,
    navigateTo: (Destination) -> Unit,
) {
    TopAppBar(
        title = {
            AnimatedContent(
                targetState = title,
                transitionSpec = { fadeIn(tween()) with fadeOut(tween()) }) { target ->
                Text(
                    text = target,
                    style = MaterialTheme.typography.headlineMedium.merge(
                        TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
                    ),
                    modifier = Modifier.testTag(stringResource(R.string.tag_top_bar_title))
                )
            }
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(
                    onClick = { navigateTo(Destination.Back) }) {
                    Icon(Icons.Filled.ArrowBack, stringResource(R.string.description_back_arrow))
                }
            }
        },
        actions = {
            menuItems.forEach { menuItem ->
                IconButton(
                    onClick = { navigateTo(menuItem.destination) }) {
                    Icon(menuItem.icon, stringResource(menuItem.description))
                }
            }
        },
        modifier = modifier.testTag(stringResource(R.string.tag_top_bar))
    )
}