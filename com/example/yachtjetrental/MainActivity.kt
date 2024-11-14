package com.example.yachtjetrental

import activity.ActivityAvis
import activity.ActivityEvaluationProprietaire
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yachtjetrental.ui.theme.YachtJetRentalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            YachtJetRentalTheme {
                // Utilisation d'un Scaffold pour structurer l'interface
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        onNavigateToAvis = {
                            // Ouvre l'activité ActivityAvis
                            startActivity(Intent(this, ActivityAvis::class.java))
                        },
                        onNavigateToEvaluations = {
                            // Ouvre l'activité ActivityEvaluationProprietaire
                            startActivity(Intent(this, ActivityEvaluationProprietaire::class.java))
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    onNavigateToAvis: () -> Unit,
    onNavigateToEvaluations: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Structure de l'écran principal avec deux boutons de navigation
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onNavigateToAvis,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text(text = "Voir les Avis")
        }
        Button(
            onClick = onNavigateToEvaluations,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text(text = "Voir les Évaluations des Propriétaires")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    YachtJetRentalTheme {
        MainScreen(
            onNavigateToAvis = {},
            onNavigateToEvaluations = {}
        )
    }
}
