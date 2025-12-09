package com.example.pirmas.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pirmas.R

@Composable
fun Registracija2(modifier: Modifier = Modifier) {
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFF5A49),
            Color(0xFFFF6AA8),
            Color(0xFF7A3BE6),
        )
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(gradient)
            .systemBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tavo Profilis",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Svoris (kg)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            OutlinedTextField(
                value = height,
                onValueChange = { height = it },
                label = { Text("Ūgis (cm)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Savaitės Tvarkaraštis",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Savaitės kalendoriaus išdėstymas
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val days = listOf("P", "A", "T", "K", "Pn", "Š", "S")
            days.forEach { day ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = day, fontWeight = FontWeight.Bold, color = Color.Black)
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        // Čia bus įkelta treniruotė
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Pasirink Treniruotę",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Velkamų treniruočių piktogramos (placeholder'iai)
        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            WorkoutPlaceholder(imageRes = R.drawable.legs, contentDescription = "Kojos")
            WorkoutPlaceholder(imageRes = R.drawable.push, contentDescription = "Stumimas")

            // Įdėkite daugiau treniruočių tipų pagal poreikį
        }
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {

            WorkoutPlaceholder(imageRes = R.drawable.pull, contentDescription = "Traukimas")
            WorkoutPlaceholder(imageRes = R.drawable.poilsis, contentDescription = "Poilsis")
            // Įdėkite daugiau treniruočių tipų pagal poreikį
        }

        Spacer(modifier = Modifier.height(32.dp))


        Button(
            onClick = { /* TODO: Implement login logic */ },
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF000000),
                contentColor = Color.White
            ),
            modifier = Modifier
                // .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 20.dp, vertical = 12.dp)

        ) {
            Text(text = "Tęsti", fontSize = 30.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun WorkoutPlaceholder(@DrawableRes imageRes: Int, contentDescription: String?) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = contentDescription,
        modifier = Modifier
            .size(90.dp)
            .clip(CircleShape)
            .border(2.dp, Color.Gray, CircleShape) // Add a border for better visibility
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Registracija2Preview() {
    PirmasTheme {
        Registracija2()
    }
}
