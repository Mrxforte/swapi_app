package com.example.swapi.presentation.designsystem.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.swapi.presentation.designsystem.AppIcons
import com.example.swapi.presentation.designsystem.AppStrings

@Composable
fun AppTopBar(
    title: String,
    onBack: (() -> Unit)? = null,
    actions: @Composable () -> Unit = {}
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = AppIcons.Back, contentDescription = AppStrings.Back)
                }
            }
        },
        actions = { actions() }
    )
}

@Composable
fun AppTopBarIconAction(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(imageVector = icon, contentDescription = contentDescription)
    }
}
