package com.bexai.bexaiinventory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bexai.bexaiinventory.presentation.ui.theme.BexAInventarioTheme
import com.bexai.bexaiinventory.presentation.ui.screens.LoginScreen
import com.bexai.bexaiinventory.presentation.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BexAInventarioTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LoginScreen(viewModel = LoginViewModel())

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BexAInventarioTheme {
        Greeting("Android")
    }
}