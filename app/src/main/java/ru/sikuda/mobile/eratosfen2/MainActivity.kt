package ru.sikuda.mobile.eratosfen2

import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.sikuda.mobile.eratosfen2.ui.theme.Eratosfen2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Eratosfen2Theme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainView(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var textClickButton by remember { mutableStateOf(getString(context, R.string.click_me)) }
    val viewModel = viewModel<MainViewModel>()
    val timeText by viewModel.text.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {

        Button(onClick = {
            textClickButton = "Clickme-" + Math.random().toString()
        }) {
            Text(text = textClickButton)
        }
        Box(modifier.fillMaxHeight(0.2F))
        Text(
            text = timeText,
            modifier = modifier
        )
        Button(onClick = {
            val listener = DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> viewModel.runCalcSync()
                    DialogInterface.BUTTON_NEGATIVE,
                    DialogInterface.BUTTON_NEUTRAL -> {
                        Toast.makeText(context, "May be next time", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            val dialog = AlertDialog.Builder(context)
                .setCancelable(true)
                .setIcon(R.mipmap.ic_launcher_round)
                .setTitle(R.string.result_title)
                .setMessage(R.string.result_message)
                .setPositiveButton(R.string.action_yes, listener)
                .setNeutralButton(R.string.action_ignore, listener)
                .setOnDismissListener {
                    Log.d(ContentValues.TAG, "Dialog dismissed")
                }
                .create()

            dialog.show()
        }) {
            Text(text = getString(context, R.string.count))
        }
        Button(
            onClick = { viewModel.runCalc() }) {
            Text(text = getString(context,R.string.count_async))
        }

    }

}

