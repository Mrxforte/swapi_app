package com.example.swapi.presentation.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.swapi.presentation.designsystem.Dimens

@Composable
fun DetailSectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
fun DetailInfoCard(
    label: String,
    value: String
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(Dimens.ItemPadding),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSmall)
        ) {
            Text(text = label, style = MaterialTheme.typography.titleMedium)
            Text(text = value, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun DetailMessageCard(message: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = message,
            modifier = Modifier.padding(Dimens.ItemPadding),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
