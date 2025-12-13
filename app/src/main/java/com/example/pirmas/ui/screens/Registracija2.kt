package com.example.pirmas.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pirmas.R
import com.example.pirmas.ui.theme.PirmasTheme
import com.example.pirmas.viewmodels.RegistrationViewModel

// Data classes to hold the state
data class Workout(@DrawableRes val imageRes: Int, val name: String)
data class ScheduleDay(val dayName: String, var workout: Workout? = null)

@Composable
fun Registracija2(
    modifier: Modifier = Modifier,
    viewModel: RegistrationViewModel = viewModel(),
    onRegistrationComplete: () -> Unit
) {
    val weight by viewModel.weight.collectAsState()
    val height by viewModel.height.collectAsState()
    val schedule by viewModel.schedule.collectAsState()
    val registrationError by viewModel.registrationError.collectAsState()

    // State for the schedule and available workouts
    val availableWorkouts = remember {
        listOf(
            Workout(R.drawable.legs, "Kojos"),
            Workout(R.drawable.push, "Stumimas"),
            Workout(R.drawable.pull, "Traukimas"),
            Workout(R.drawable.poilsis, "Poilsis"),
        )
    }

    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFFF5A49), Color(0xFFFF6AA8), Color(0xFF7A3BE6))
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
                onValueChange = { viewModel.onWeightChange(it) },
                label = { Text("Svoris (kg)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            OutlinedTextField(
                value = height,
                onValueChange = { viewModel.onHeightChange(it) },
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

        // Weekly calendar now reads from the state
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            schedule.forEach { day ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = day.dayName, fontWeight = FontWeight.Bold, color = Color.Black)
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        day.workout?.let {
                            Image(
                                painter = painterResource(id = it.imageRes),
                                contentDescription = it.name,
                                modifier = Modifier.size(40.dp)
                            )
                        }
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

        // Workout placeholders with a click action to test state updates
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            availableWorkouts.take(2).forEach { workout ->
                WorkoutPlaceholder(workout = workout) {
                    // Find first empty day and assign the workout
                    val dayIndex = schedule.indexOfFirst { it.workout == null }
                    if (dayIndex != -1) {
                        val newList = schedule.toMutableList()
                        newList[dayIndex] = schedule[dayIndex].copy(workout = workout)
                        viewModel.onScheduleChange(newList)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            availableWorkouts.drop(2).forEach { workout ->
                WorkoutPlaceholder(workout = workout) {
                    val dayIndex = schedule.indexOfFirst { it.workout == null }
                    if (dayIndex != -1) {
                        val newList = schedule.toMutableList()
                        newList[dayIndex] = schedule[dayIndex].copy(workout = workout)
                        viewModel.onScheduleChange(newList)
                    }
                }
            }
        }

        registrationError?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f)) // Pushes button to bottom

        Button(
            onClick = { viewModel.saveProfileAndSchedule(onRegistrationComplete) },
            enabled = weight.isNotBlank() && height.isNotBlank(),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF000000),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Text(text = "Baigti registraciją", fontSize = 30.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Registracija2Preview() {
    PirmasTheme {
        Registracija2(onRegistrationComplete = {})
    }
}
