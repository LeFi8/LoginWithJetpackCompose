package com.example.loginjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.loginjetpackcompose.authentication.Authentication
import com.example.loginjetpackcompose.presentation.LoggedIn
import com.example.loginjetpackcompose.presentation.Login
import com.example.loginjetpackcompose.ui.theme.LoginJetpackComposeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isLoggedIn = remember { mutableStateOf(false) }
            val authenticationService = Authentication()
            LoginJetpackComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (isLoggedIn.value) {
                        LoggedIn(onLogoutClick = {
                            isLoggedIn.value = false
                            authenticationService.logout()
                        })
                    } else {
                        Login(
                            onLoginClick = { email, password ->
                            lifecycleScope.launch {
                                isLoggedIn.value = authenticationService.login(
                                    email, password, this@MainActivity
                                )
                            }
                        },
                            onNoAccountClick = {
                                // TODO("change screens")
                            }
                        )
                    }
                }
            }
        }
    }
}
