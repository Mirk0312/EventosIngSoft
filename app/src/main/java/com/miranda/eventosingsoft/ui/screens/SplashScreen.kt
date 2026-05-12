package com.miranda.eventosingsoft.ui.screens.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miranda.eventosingsoft.R
import kotlinx.coroutines.delay
import androidx.compose.ui.draw.clip
@Composable
fun SplashScreen(onNavigateToHome: () -> Unit) {
    val startAnimation = remember { mutableStateOf(false) }
    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation.value) 1f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )

    LaunchedEffect(key1 = true) {
        startAnimation.value = true
        delay(4000)
        onNavigateToHome()
    }

    // Fondo con degradado
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFFF3E5F5)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .alpha(alphaAnim), // un poco de animación
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Contenedor del logo
            Surface(
                modifier = Modifier.size(160.dp),
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_ues),
                    contentDescription = "Logo UES",
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "EVENTOS",
                fontSize = 14.sp,
                letterSpacing = 4.sp,
                color = Color(0xFF6750A4),
                fontWeight = FontWeight.Light
            )

            Text(
                text = "Ingeniería en Software",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF311B92),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(60.dp))

            // Nombres del equipo
            Surface(
                color = Color(0xFF6750A4).copy(alpha = 0.05f),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "DESARROLLADO POR EL EQUIPO:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Nombres
                    Text(
                        text = "Martin Durán • Karim Miranda\nEmiliano Vásquez",
                        fontSize = 15.sp,
                        lineHeight = 24.sp,
                        textAlign = TextAlign.Center,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            androidx.compose.material3.LinearProgressIndicator(
                modifier = Modifier.width(100.dp).clip(CircleShape),
                color = Color(0xFFFFC107),
                trackColor = Color(0xFF6750A4).copy(alpha = 0.1f)
            )
        }
    }
}