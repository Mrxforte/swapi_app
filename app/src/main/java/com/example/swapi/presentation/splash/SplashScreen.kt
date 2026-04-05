package com.example.swapi.presentation.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swapi.presentation.designsystem.AppIcons
import com.example.swapi.presentation.designsystem.AppStrings
import com.example.swapi.presentation.theme.SolarGold
import kotlin.random.Random

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    onNavigateNext: (String) -> Unit
) {
    val nextDestination by viewModel.nextDestination.collectAsState()

    LaunchedEffect(nextDestination) {
        nextDestination?.let(onNavigateNext)
    }

    val infiniteTransition = rememberInfiniteTransition(label = "stars")
    val twinkle by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1800, easing = LinearEasing), RepeatMode.Reverse),
        label = "twinkle"
    )

    val logoAlpha = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        logoAlpha.animateTo(1f, tween(1200))
    }

    // Generate stable star positions
    val stars = remember {
        (1..80).map {
            Triple(Random.nextFloat(), Random.nextFloat(), Random.nextFloat())
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF070E1A),
                        Color(0xFF0F1C2C),
                        Color(0xFF1A2E4A)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Starfield
        Canvas(modifier = Modifier.fillMaxSize()) {
            stars.forEachIndexed { i, (x, y, size) ->
                val alpha = if (i % 3 == 0) twinkle else if (i % 3 == 1) 1f - twinkle * 0.5f else 0.7f
                drawCircle(
                    color = Color.White.copy(alpha = alpha * 0.9f),
                    radius = (size * 2.5f + 0.5f),
                    center = Offset(x * this.size.width, y * this.size.height)
                )
            }
            // Nebula glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF2A4D69).copy(alpha = 0.35f),
                        Color.Transparent
                    ),
                    center = Offset(this.size.width * 0.5f, this.size.height * 0.45f),
                    radius = this.size.width * 0.6f
                ),
                radius = this.size.width * 0.6f,
                center = Offset(this.size.width * 0.5f, this.size.height * 0.45f)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.16f),
                                Color.White.copy(alpha = 0.04f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = AppIcons.MainTab,
                    contentDescription = null,
                    tint = SolarGold,
                    modifier = Modifier.size(52.dp)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "SWAPI",
                style = MaterialTheme.typography.displayMedium.copy(
                    letterSpacing = 8.sp,
                    fontWeight = FontWeight.Black
                ),
                color = Color.White,
            )

            Text(
                text = "EXPLORER",
                style = MaterialTheme.typography.headlineSmall.copy(
                    letterSpacing = 5.sp,
                    fontWeight = FontWeight.Light
                ),
                color = SolarGold,
            )

            Spacer(modifier = Modifier.height(18.dp))

            Canvas(modifier = Modifier.height(3.dp).padding(horizontal = 48.dp)) {
                drawLine(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color.Transparent, SolarGold, Color.Transparent)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 3f
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = AppStrings.SplashSubtitle,
                style = MaterialTheme.typography.bodyLarge.copy(letterSpacing = 1.sp),
                color = Color.White.copy(alpha = 0.6f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "People  •  Films  •  Planets  •  Species",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.45f),
                textAlign = TextAlign.Center
            )
        }
    }
}
