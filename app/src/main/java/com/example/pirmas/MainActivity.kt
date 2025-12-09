package com.example.pirmas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pirmas.ui.theme.PirmasTheme
import com.example.pirmas.ui.theme.Registracija
import com.example.pirmas.ui.theme.Registracija2

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PirmasTheme {
                Navigation()
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "pradzia") {
        composable("pradzia") {
            Pradzia(onStartClick = { navController.navigate("prisijungimas") })
        }
        composable("prisijungimas") {
            Prisijungimas(onNeturiPaskyrosClick = { navController.navigate("registracija") })
        }
        composable("registracija") {
            Registracija(onTestiClick = {navController.navigate("registracija2")})
        }
        composable("registracija2") {
            Registracija2()
        }
    }
}
