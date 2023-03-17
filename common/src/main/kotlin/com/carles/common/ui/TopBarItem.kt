package com.carles.common.ui

import androidx.compose.ui.graphics.vector.ImageVector
import com.carles.common.ui.navigation.Navigate

class TopBarItem(val icon: ImageVector, val description: Int, val action: (Navigate) -> Unit)