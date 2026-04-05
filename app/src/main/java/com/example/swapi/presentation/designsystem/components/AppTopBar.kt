package com.example.swapi.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.example.swapi.presentation.designsystem.AppIcons
import com.example.swapi.presentation.designsystem.AppStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    onBack: (() -> Unit)? = null,
    navigationIcon: (@Composable () -> Unit)? = null,
    centerTitle: Boolean = false,
    actions: @Composable () -> Unit = {}
) {
    val navigation: @Composable () -> Unit = when {
        onBack != null -> {
            {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = AppIcons.Back,
                        contentDescription = AppStrings.Back,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
        navigationIcon != null -> navigationIcon
        else -> ({})
    }

    val colors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent,
        titleContentColor = MaterialTheme.colorScheme.onPrimary,
        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.96f),
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.86f)
                    )
                )
            )
            .statusBarsPadding()
    ) {
        if (centerTitle) {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = navigation,
                actions = { actions() },
                colors = colors,
                windowInsets = WindowInsets(0, 0, 0, 0)
            )
        } else {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = navigation,
                actions = { actions() },
                colors = colors,
                windowInsets = WindowInsets(0, 0, 0, 0)
            )
        }
    }
}

@Composable
fun AppTopBarIconAction(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(imageVector = icon, contentDescription = contentDescription, tint = MaterialTheme.colorScheme.onPrimary)
    }
}
