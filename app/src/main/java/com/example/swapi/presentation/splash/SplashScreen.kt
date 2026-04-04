package com.example.swapi.presentation.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.swapi.presentation.designsystem.AppStrings

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    onNavigateNext: (String) -> Unit
) {
    val nextDestination by viewModel.nextDestination.collectAsState()

    LaunchedEffect(nextDestination) {
        nextDestination?.let(onNavigateNext)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = AppStrings.SplashTitle, style = MaterialTheme.typography.headlineLarge)
        CircularProgressIndicator()
    }
}
