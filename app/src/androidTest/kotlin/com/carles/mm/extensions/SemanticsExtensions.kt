package com.carles.mm.ui

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText

fun SemanticsNodeInteractionsProvider.onIconWithContentDescription(description: String):
        SemanticsNodeInteraction =
    onNode(
        matcher = SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Image) and hasContentDescription(description),
        useUnmergedTree = true
    )

fun SemanticsNodeInteractionsProvider.onDialogNodeWithText(text: String): SemanticsNodeInteraction =
    onNode(
        matcher = hasText(text) and hasAnyAncestor(SemanticsMatcher.expectValue(SemanticsProperties.IsDialog, Unit)),
        useUnmergedTree = true
    )