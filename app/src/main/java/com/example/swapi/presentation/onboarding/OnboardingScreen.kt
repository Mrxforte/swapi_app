package com.example.swapi.presentation.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.background
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.swapi.presentation.designsystem.AppStrings
import com.example.swapi.presentation.designsystem.Dimens
import com.example.swapi.presentation.designsystem.components.AppScreenScaffold
import kotlinx.coroutines.launch

private data class OnboardingPage(
    val headline: String,
    val description: String
)

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel,
    onBack: () -> Unit,
    onFinish: () -> Unit
) {
    val pages = listOf(
        OnboardingPage(
            headline = AppStrings.OnboardingHeadline1,
            description = AppStrings.OnboardingDescription1
        ),
        OnboardingPage(
            headline = AppStrings.OnboardingHeadline2,
            description = AppStrings.OnboardingDescription2
        ),
        OnboardingPage(
            headline = AppStrings.OnboardingHeadline3,
            description = AppStrings.OnboardingDescription3
        )
    )
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    fun completeOnboarding() {
        viewModel.completeOnboarding()
        onFinish()
    }

    AppScreenScaffold(
        title = AppStrings.OnboardingTitle,
        onBack = onBack
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(Dimens.ScreenPaddingLarge),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingLarge)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) {
                val page = pages[pagerState.currentPage]
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = page.headline,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = page.description,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = Dimens.SpacingMedium)
                    )
                }
            }

            DotsIndicator(
                totalDots = pages.size,
                selectedIndex = pagerState.currentPage
            )

            val isLastPage = pagerState.currentPage == pages.lastIndex
            if (isLastPage) {
                Button(
                    onClick = { completeOnboarding() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(AppStrings.OnboardingAction)
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = { completeOnboarding() }) {
                        Text(AppStrings.OnboardingSkip)
                    }

                    Button(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    ) {
                        Text(AppStrings.OnboardingNext)
                    }
                }
            }
        }
    }
}

@Composable
private fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(totalDots) { index ->
            val isSelected = index == selectedIndex
            Box(
                modifier = Modifier
                    .padding(horizontal = Dimens.SpacingSmall)
                    .size(if (isSelected) Dimens.SpacingLarge else Dimens.SpacingMedium)
                    .clip(CircleShape)
                    .background(
                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray,
                    shape = CircleShape
                )
            )
        }
    }
}
