package com.example.swapi.presentation.designsystem.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable

@Composable
fun AppScreenScaffold(
    title: String,
    onBack: (() -> Unit)? = null,
    actions: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = title,
                onBack = onBack,
                actions = actions
            )
        },
        content = content
    )
}
