package com.threegroup.tobedated

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.threegroup.tobedated.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val colors = listOf("dating", "friend", "casual" )
        setContent {
            AppTheme(
                activity = colors.random()
            ) {
                PolkaDotCanvas()
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(50.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    var numberOf by remember{ mutableStateOf("") }
                    var startNumber by remember{ mutableStateOf("") }
                    var isEnabled by remember { mutableStateOf(false) }
                    Text(text = "Number of profiles\nAfter 25 it might not work", style = AppTheme.typography.titleLarge, color = AppTheme.colorScheme.onBackground)
                    TextField(value = numberOf, onValueChange = {
                        value -> numberOf = value
                        isEnabled = numberOf.isDigitsOnly() && startNumber.isDigitsOnly() },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                    Text(text = "Start number + 100", style = AppTheme.typography.titleLarge, color = AppTheme.colorScheme.onBackground)
                    TextField(value = startNumber, onValueChange = {value -> startNumber = value
                        isEnabled = numberOf.isDigitsOnly() && startNumber.isDigitsOnly()},
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                    Button(onClick = {
                        makeProfiles(this@MainActivity, numberOf.toInt(), startNumber.toInt())
                    },
                        enabled = isEnabled,
                        colors = ButtonDefaults.buttonColors().copy(
                            disabledContentColor = Color.Red,
                            disabledContainerColor = Color.Red
                        )
                    ) {
                        Text(text = "Make Profiles")
                    }
                }
            }
        }
        //makeProfiles(this)
    }

}