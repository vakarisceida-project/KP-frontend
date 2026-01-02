package com.example.pirmas.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// Bendros duomenų klasės, naudojamos keliuose ekranuose
data class Workout(@DrawableRes val imageRes: Int, val name: String)
data class ScheduleDay(val dayName: String, var workout: Workout? = null)

/**
 * Atvaizduoja pasirenkamos treniruotės piktogramą ir pavadinimą.
 */
@Composable
fun WorkoutPlaceholder(workout: Workout, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = workout.imageRes),
            contentDescription = workout.name,
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )
        Text(text = workout.name, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}
