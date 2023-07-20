/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.practica6_200749.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.practica6_200749.R
import com.example.practica6_200749.presentation.theme.Practica6_200749Theme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {
    private lateinit var clockTextView: TextView
    private lateinit var saludoTextView: TextView
    private lateinit var handler: Handler
    private lateinit var updateTimeRunnable: Runnable



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        clockTextView = findViewById(R.id.TVclock)
        saludoTextView = findViewById(R.id.saludo)
        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val saludo: String = when(hourOfDay) {
            in 6..11 -> "Buenos días"
            in 12..18 -> "Buenas tardes"
            else -> "Buenas noches"
        }
        saludoTextView.text = saludo

        handler = Handler()
        updateTimeRunnable = object : Runnable {
            override fun run() {
                val currentTime = Calendar.getInstance().time
                val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                val formattedTime = dateFormat.format(currentTime)
                clockTextView.text = formattedTime

                // Se actualiza después de 1 segundo
                saludoTextView.text = saludo
                handler.postDelayed(this, 1000)
            }
        }
    }
    override fun onResume() {
        super.onResume()
        handler.post(updateTimeRunnable)
    }
    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(updateTimeRunnable)
    }
}

@Composable
fun WearApp(greetingName: String) {
    Practica6_200749Theme{
        /* If you have enough items in your list, use [ScalingLazyColumn] which is an optimized
         * version of LazyColumn for wear devices with some added features. For more information,
         * see d.android.com/wear/compose.
         */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            verticalArrangement = Arrangement.Center
        ) {
            Greeting(greetingName = greetingName)
        }
    }
}

@Composable
fun Greeting(greetingName: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primary,
        text = stringResource(R.string.hello_world, greetingName)
    )
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp("Preview Android")
}
