package com.example.pirmas.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pirmas.R
import com.example.pirmas.ui.theme.PirmasTheme
import com.example.pirmas.viewmodels.ProfileViewModel

@Composable
fun ProfilioRedagavimasScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    viewModel: ProfileViewModel = viewModel()
) {
    val weight by viewModel.weight.collectAsState()
    val height by viewModel.height.collectAsState()
    val schedule by viewModel.schedule.collectAsState()
    val username by viewModel.username.collectAsState()
    val updateStatus by viewModel.updateStatus.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(updateStatus) {
        updateStatus?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    val availableWorkouts = remember {
        listOf(
            Workout(R.drawable.legs, "Kojos"),
            Workout(R.drawable.push, "Stumimas"),
            Workout(R.drawable.pull, "Traukimas"),
            Workout(R.drawable.poilsis, "Poilsis"),
        )
    }

    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF7A3BE6), Color(0xFFFF6AA8), Color(0xFFFF5A49))
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(gradient)
            .systemBarsPadding()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Grįžti atgal",
                    modifier = Modifier.size(36.dp),
                    tint = Color.Black
                )
            }
            Text(
                text = "Redaguoti Profilį",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = {},
            label = { Text("Vartotojo vardas") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true, // Vardo keisti neleidžiame
            colors = OutlinedTextFieldDefaults.colors(
                disabledContainerColor = Color.LightGray,
                disabledTextColor = Color.DarkGray
            )
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Savaitės Tvarkaraštis",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            TextButton(onClick = { viewModel.clearSchedule() }) {
                Text(text = "Išvalyti", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val displaySchedule = if (schedule.isEmpty()) {
                listOf("P", "A", "T", "K", "Pn", "Š", "S").map { ScheduleDay(it) }
            } else {
                schedule
            }
            displaySchedule.forEach { day ->
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            availableWorkouts.forEach { workout ->
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

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { viewModel.saveChanges() },
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 20.dp)
        ) {
            Text(text = "Išsaugoti pakeitimus", fontSize = 22.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfilioRedagavimasScreenPreview() {
    PirmasTheme {
        ProfilioRedagavimasScreen()
    }
}
