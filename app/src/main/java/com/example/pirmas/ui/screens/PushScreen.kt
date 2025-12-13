package com.example.pirmas.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pirmas.R
import com.example.pirmas.ui.theme.PirmasTheme

@Composable
fun PushScreen(modifier: Modifier = Modifier, onBackClick: () -> Unit = {}) {
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF7A3BE6), Color(0xFFFF6AA8), Color(0xFFFF5A49))
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(gradient)
            .systemBarsPadding()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "ŠIANDIEN PUSH DAY, TODĖL TRENIRUOK ŠIUOS RAUMENIS:",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                lineHeight = 36.sp
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "○ KRŪTINĘ", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(text = "○ PEČIUS", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(text = "○ TRICEPSUS", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(32.dp))
            Image(
                painter = painterResource(id = R.drawable.push),
                contentDescription = "Push treniruotė",
                modifier = Modifier
                    .size(120.dp)
                    .clickable { onBackClick() }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PushScreenPreview() {
    PirmasTheme {
        PushScreen(onBackClick = {})
    }
}
