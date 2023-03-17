package com.carles.hyrule.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.carles.common.ui.composables.CenteredProgressIndicator
import com.carles.common.ui.extensions.debounceClickable
import com.carles.common.ui.extensions.disableSplitMotionEvents
import com.carles.hyrule.Monster
import com.carles.hyrule.R

@Composable
fun MonstersScreen(viewModel: MonstersViewModel, onMonsterClick: (Int) -> Unit) {
    val uiState: MonstersUiState by viewModel.uiState.collectAsStateWithLifecycle()
    MonstersScreen(uiState, onMonsterClick) { viewModel.retry() }
}

@Composable
fun MonstersScreen(state: MonstersUiState, onMonsterClick: (Int) -> Unit, retry: () -> Unit) {
    when (state) {
        is MonstersUiState.Loading -> CenteredProgressIndicator()
        is MonstersUiState.Error -> ErrorDialog(state.message, retry)
        is MonstersUiState.Data -> MonstersList(state.monsters, onMonsterClick)
    }
}

@Composable
private fun MonstersList(monsters: List<Monster>, onMonsterClick: (Int) -> Unit, modifier: Modifier = Modifier) {
    val state = rememberLazyListState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .disableSplitMotionEvents()
            .testTag(stringResource(R.string.tag_monsters_list)),
        state = state,
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        itemsIndexed(
            items = monsters,
            key = { _: Int, monster: Monster -> monster.id }) { index: Int, monster: Monster ->
            MonstersRow(monster, index < monsters.lastIndex, onMonsterClick)
        }
    }
}

@Composable
private fun MonstersRow(monster: Monster, showDivider: Boolean, onMonsterClick: (Int) -> Unit, modifier: Modifier = Modifier) {
    Text(
        text = monster.name,
        style = MaterialTheme.typography.headlineSmall,
        modifier = modifier
            .debounceClickable { onMonsterClick(monster.id) }
            .padding(16.dp)
            .fillMaxWidth(),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.primary,
    )
    if (showDivider) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.outline,
            thickness = 1.dp
        )
    }
}