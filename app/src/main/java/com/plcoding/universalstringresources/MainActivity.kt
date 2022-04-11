package com.plcoding.universalstringresources

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plcoding.universalstringresources.ui.theme.UniversalStringResourcesTheme
import kotlinx.coroutines.flow.collect

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniversalStringResourcesTheme {
                val viewModel = viewModel<MyViewModel>()
                val scaffoldState = rememberScaffoldState()
                val context = LocalContext.current
                LaunchedEffect(key1 = scaffoldState) {
                    viewModel.errors.collect { error ->
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = error.asString(context)
                        )
                    }
                }
                Scaffold(
                    scaffoldState = scaffoldState
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        TextField(
                            value = viewModel.name,
                            onValueChange = viewModel::onNameChange,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { viewModel.validateInputs() },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text(text = "Validate")
                        }
                    }
                }
            }
        }
    }
}
