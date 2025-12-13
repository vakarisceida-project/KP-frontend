package com.example.pirmas.ui.screens

// build.gradle (Module) turi būti Material3 + Compose įjungtas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
fun Pradzia(
    onStartClick: () -> Unit = {}
) {
    // Red → pink → purple → blue gradient (panašiai kaip tavo makete)
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFF5A49), // coral/red top
            Color(0xFFFF6AA8), // pink
            Color(0xFF7A3BE6), // purple
            Color(0xFF2D29FF)  // blue bottom
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(horizontal = 20.dp)
            .systemBarsPadding() // kad niekas nelįstų po status bar
    ) {
        // Pavadinimas viršuje


        Image(
            painter = painterResource(id = R.drawable.gymify),
            contentDescription = "Gymify Logo",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 50.dp)
        )

        // Slogan / aprašymas per vidurį
        Text(
            text = "Sukurk savo sporto planą vos per kelias minutes!",
            textAlign = TextAlign.Center,
            lineHeight = 32.sp,
            color = Color.Black,
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 100.dp)
        )

        // „Pradėti“ mygtukas apačioje
        Button(
            onClick = onStartClick,
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF000000),
                contentColor = Color.White
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(75.dp)
                .padding(bottom = 0.dp)
        ) {
            Text(text = "Pradėti", fontSize = 30.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PradziaPreview() {
    PirmasTheme {
        Pradzia()
    }
}
