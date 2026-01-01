package com.example.pirmas.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pirmas.R
import com.example.pirmas.ui.theme.PirmasTheme
import com.example.pirmas.viewmodels.ProfileViewModel

@Composable
fun Tvarkarastis(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onDayClick: (String) -> Unit = {}
) {
    val schedule by viewModel.schedule.collectAsState()
    val error by viewModel.updateStatus.collectAsState() // Stebime klaidas

    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF7A3BE6), Color(0xFFFF6AA8), Color(0xFFFF5A49))
    )

    val fullDayNames = mapOf(
        "P" to "PIRMADIENIS",
        "A" to "ANTRADIENIS",
        "T" to "TREČIADIENIS",
        "K" to "KETVIRTADIENIS",
        "Pn" to "PENKTADIENIS", // Pataisyta
        "Š" to "ŠEŠTADIENIS",
        "S" to "SEKMADIENIS"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(gradient)
            .systemBarsPadding()
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onBackClick() },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "TVARKARAŠTIS",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Image(
                    painter = painterResource(id = R.drawable.ziuri),
                    contentDescription = "Tvarkaraštis icon",
                    modifier = Modifier.size(60.dp)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))

            // Klaidos pranešimas
            error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                val displaySchedule = if (schedule.isEmpty()) {
                    listOf("P", "A", "T", "K", "Pn", "Š", "S").map { ScheduleDay(it) }
                } else {
                    schedule
                }

                displaySchedule.forEach { day ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onDayClick(day.workout?.name ?: "Poilsis") } // Pataisyta
                    ) {
                        Image(
                            painter = painterResource(
                                id = day.workout?.imageRes ?: R.drawable.poilsis
                            ),
                            contentDescription = day.workout?.name,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = fullDayNames[day.dayName] ?: day.dayName,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TvarkarastisPreview() {
    PirmasTheme {
        Tvarkarastis(onBackClick = {}, onDayClick = {})
    }
}
