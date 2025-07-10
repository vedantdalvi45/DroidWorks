package com.example.unitconverter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitconverter.ui.theme.UnitConverterTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UnitConverter(Modifier.padding(innerPadding))
                }
            }
        }
    }
}



@Composable
fun UnitConverter(modifier: Modifier = Modifier) {
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf("Meter") }
    var outputUnit by remember { mutableStateOf("Meter") }
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }
    var conversionFactor = remember { mutableStateOf(1.00) }
    var oConversionFactor = remember { mutableStateOf(1.00) }

    val customTextTyle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color(96, 16, 235),
        fontFamily = FontFamily.SansSerif
    )

    fun convertUnits() {
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result = (inputValueDouble * conversionFactor.value * 100.0 / oConversionFactor.value ).roundToInt()/ 100.0
        outputValue = result.toString() +"  "+ outputUnit
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Unit Converter",
            style = customTextTyle
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
            },
            label = {Text(text = "Enter Value")}
        )
        Spacer(modifier = Modifier.height(30.dp))
        Row {
            Box {
                Button(onClick = {iExpanded = true}) {
                    Text(text = inputUnit)
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Arrow Down"
                    )
                }
                DropdownMenu(expanded = iExpanded , onDismissRequest = {iExpanded = false}) {
                    DropdownMenuItem(
                        text = { Text(text = "Kilometer") },
                        onClick = {
                            iExpanded = false
                            inputUnit = "Kilometer"
                            conversionFactor.value = 1000.00
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Meter") },
                        onClick = {
                            iExpanded = false
                            inputUnit = "Meter"
                            conversionFactor.value = 1.00
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Centimeter") },
                        onClick = {
                            iExpanded = false
                            inputUnit = "Centimeter"
                            conversionFactor.value = 0.01
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Millimeter") },
                        onClick = {
                            iExpanded = false
                            inputUnit = "Millimeter"
                            conversionFactor.value = 0.001
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Mile") },
                        onClick = {
                            iExpanded = false
                            inputUnit = "Mile"
                            conversionFactor.value = 1609.35
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Yard") },
                        onClick = {
                            iExpanded = false
                            inputUnit = "Yard"
                            conversionFactor.value = 0.9144
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Foot") },
                        onClick = {
                            iExpanded = false
                            inputUnit = "Foot"
                            conversionFactor.value = 0.3048
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Inch") },
                        onClick = {
                            iExpanded = false
                            inputUnit = "Inch"
                            conversionFactor.value = 0.0254
                            convertUnits()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Box {
                Button(onClick = {oExpanded = true}) {
                    Text(text = outputUnit)
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Arrow Down"
                    )
                }
                DropdownMenu(expanded = oExpanded , onDismissRequest = {oExpanded = false}) {
                    DropdownMenuItem(
                        text = { Text(text = "Kilometer") },
                        onClick = {
                            oExpanded = false
                            outputUnit = "Kilometer"
                            oConversionFactor.value = 1000.00
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Meter") },
                        onClick = {
                            oExpanded = false
                            outputUnit = "Meter"
                            oConversionFactor.value = 1.00
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Centimeter") },
                        onClick = {
                            oExpanded = false
                            outputUnit = "Centimeter"
                            oConversionFactor.value = 0.01
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Millimeter") },
                        onClick = {
                            oExpanded = false
                            outputUnit = "Millimeter"
                            oConversionFactor.value = 0.001
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Mile") },
                        onClick = {
                            oExpanded = false
                            outputUnit = "Mile"
                            oConversionFactor.value = 1609.35
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Yard") },
                        onClick = {
                            oExpanded = false
                            outputUnit = "Yard"
                            oConversionFactor.value = 0.9144
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Foot") },
                        onClick = {
                            oExpanded = false
                            outputUnit = "Foot"
                            oConversionFactor.value = 0.3048
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Inch") },
                        onClick = {
                            oExpanded = false
                            outputUnit = "Inch"
                            oConversionFactor.value = 0.0254
                            convertUnits()
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Result : ${outputValue} ${outputUnit}",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UnitConverterTheme {
        UnitConverter()
    }
}