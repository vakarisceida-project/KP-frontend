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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pirmas.R
import com.example.pirmas.ui.theme.PirmasTheme
import com.example.pirmas.viewmodels.ScheduleViewModel

@Composable
fun Tvarkarastis(
    modifier: Modifier = Modifier,
    viewModel: ScheduleViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onDayClick: (String) -> Unit = {}
) {
    val schedule by viewModel.schedule.collectAsState()

    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF7A3BE6), Color(0xFFFF6AA8), Color(0xFFFF5A49))
    )

    val dayNames = listOf(
        "Pirmadienis", "Antradienis", "Trečiadienis", "Ketvirtadienis", "Penktadienis", "Šeštadienis", "Sekmadienis"
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
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                dayNames.forEach { dayName ->
                    val workout = schedule.find { it.day == dayName }?.workout
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { workout?.let { onDayClick(it) } }
                    ) {
                        Image(
                            painter = painterResource(
                                id = when (workout) {
                                    "Kojos" -> R.drawable.legs
                                    "Stumimas" -> R.drawable.push
                                    "Traukimas" -> R.drawable.pull
                                    "Poilsis" -> R.drawable.poilsis
                                    else -> R.drawable.poilsis // Default or empty day icon
                                }
                            ),
                            contentDescription = workout,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = dayName.uppercase(),
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
