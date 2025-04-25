package com.example.tiptime

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.example.tiptime.ui.theme.TipTimeTheme
import androidx.compose.runtime.saveable.rememberSaveable
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipTimeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipTimeScreen()
                }
            }
        }
    }
}

@Composable
fun TipTimeScreen() {
    var amountInput by rememberSaveable { mutableStateOf("") }
    var tipPercentage by rememberSaveable { mutableDoubleStateOf(0.20) }
    var roundUp by rememberSaveable { mutableStateOf(false) }
    var tipResult by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        OutlinedTextField(
            value = amountInput,
            onValueChange = { amountInput = it },
            label = { Text("Cost of Service") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("How was the service?")
        Spacer(modifier = Modifier.height(8.dp))

        val radioOptions = listOf(
            "Amazing (20%)" to 0.20,
            "Good (18%)" to 0.18,
            "Okay (15%)" to 0.15
        )

        radioOptions.forEach { (label, value) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = tipPercentage == value,
                    onClick = { tipPercentage = value }
                )
                Text(label)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Round up tip?")
            Switch(
                checked = roundUp,
                onCheckedChange = { roundUp = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val amount = amountInput.toDoubleOrNull()

                if (amount == null || amount <= 0) {
                    Toast.makeText(context, "Harus di isi dan berupa angka!", Toast.LENGTH_SHORT).show()
                } else {
                    var tip = amount * tipPercentage
                    if (roundUp) {
                        tip = ceil(tip)
                    }
                    tipResult = "Tip Amount: $%.2f".format(tip)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("CALCULATE")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (tipResult.isEmpty()) "Tip Amount:" else tipResult,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}
