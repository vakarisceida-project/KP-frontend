package com.example.pirmas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pirmas.ui.screens.KojosScreen
import com.example.pirmas.ui.screens.NustatymaiScreen
import com.example.pirmas.ui.screens.Pagrindinis
import com.example.pirmas.ui.screens.PoilsisScreen
import com.example.pirmas.ui.screens.Pradzia
import com.example.pirmas.ui.screens.Prisijungimas
import com.example.pirmas.ui.screens.ProfilioRedagavimasScreen
import com.example.pirmas.ui.screens.PullScreen
import com.example.pirmas.ui.screens.PushScreen
import com.example.pirmas.ui.screens.Registracija
import com.example.pirmas.ui.screens.Registracija2
import com.example.pirmas.ui.screens.Tvarkarastis
import com.example.pirmas.ui.theme.PirmasTheme

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
            Prisijungimas(
                onNeturiPaskyrosClick = { navController.navigate("registracija") },
                onLoginSuccess = {
                    navController.navigate("pagrindinis") {
                        popUpTo("pradzia") { inclusive = true }
                    }
                }
            )
        }
        composable("registracija") {
            Registracija(onTestiClick = { username, password ->
                navController.navigate("registracija2/$username/$password")
            })
        }
        composable(
            route = "registracija2/{username}/{password}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType },
                navArgument("password") { type = NavType.StringType }
            )
        ) {
            backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            val password = backStackEntry.arguments?.getString("password") ?: ""
            Registracija2(
                username = username,
                password = password,
                onRegistrationComplete = {
                    navController.navigate("pagrindinis") {
                        popUpTo("pradzia") { inclusive = true }
                    }
                }
            )
        }
        composable("pagrindinis") {
            Pagrindinis(
                onTvarkarastisClick = { navController.navigate("tvarkarastis") },
                onNustatymaiClick = { navController.navigate("nustatymai") }
            )
        }
        composable("tvarkarastis") {
            Tvarkarastis(onBackClick = { navController.popBackStack() }) {
                workout ->
                when (workout) {
                    "Kojos" -> navController.navigate("kojos")
                    "Stumimas" -> navController.navigate("push")
                    "Traukimas" -> navController.navigate("pull")
                    "Poilsis" -> navController.navigate("poilsis")
                }
            }
        }
        composable("nustatymai") {
            NustatymaiScreen(
                onBackClick = { navController.popBackStack() },
                onLogoutClick = {
                    navController.navigate("pradzia") {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                },
                onProfileClick = { navController.navigate("profilio_redagavimas") }
            )
        }
        composable("profilio_redagavimas") {
            ProfilioRedagavimasScreen(onBackClick = { navController.popBackStack() })
        }
        composable("kojos") { KojosScreen(onBackClick = { navController.popBackStack() }) }
        composable("push") { PushScreen(onBackClick = { navController.popBackStack() }) }
        composable("pull") { PullScreen(onBackClick = { navController.popBackStack() }) }
        composable("poilsis") { PoilsisScreen(onBackClick = { navController.popBackStack() }) }
    }
}
