package com.example.captaingame

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.captaingame.ui.theme.CaptainGameTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CaptainGameTheme {
                Surface {

                    captainGame()
                }
            }
        }
    }
}
@Composable
fun captainGame(){
    val treasuresFound = remember { mutableStateOf(0) }
//    val treasuresFound by remember { mutableStateOf(0) }
    val direction = remember { mutableStateOf("North") }
    val stormOrTreasure = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        Text(text = "Treasures Found ${treasuresFound.value}")
        Text(text = "Treasures Direction ${direction.value}")
        Text(text = "Storm or Treasure ${stormOrTreasure.value}")
        Button(onClick = {
            direction.value = "East"
            if(Random.nextBoolean()){
                treasuresFound.value++
                stormOrTreasure.value = "Storm"
            }else
                stormOrTreasure.value = "Treasure"
        }){
            Text(text = "Sail East")
        }
        Button(onClick = {
            direction.value = "West"
            if(Random.nextBoolean()){
                treasuresFound.value++
                stormOrTreasure.value = "Storm"
            }else
                stormOrTreasure.value = "Treasure"
        }){
            Text(text = "Sail West")
        }
        Button(onClick = {
            direction.value = "North"
            if(Random.nextBoolean()){
                treasuresFound.value++
                stormOrTreasure.value = "Storm"
            }else
                stormOrTreasure.value = "Treasure"
        }){
            Text(text = "Sail North")
        }
        Button(onClick = {
            direction.value = "South"
            if(Random.nextBoolean()){
                treasuresFound.value++
                stormOrTreasure.value = "Storm"
            }else
                stormOrTreasure.value = "Treasure"
        }){
            Text(text = "Sail South")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CaptainGameTheme {
        captainGame()
    }
}