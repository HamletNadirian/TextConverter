package com.example.text.tabs


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.text.components.CipherSelector
import com.example.text.viewmodel.CodecViewModel
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CodecScreen(viewModel: CodecViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.ime.asPaddingValues()),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Верхнее поле — пол‑экрана
        OutlinedTextField(
            value = state.inputText,
            onValueChange = { viewModel.encryptFromInput(it) },
            label = { Text("Enter text") },
            modifier = Modifier
                .weight(1f)       // занимает 1 часть
                .fillMaxWidth(),  // по ширине весь экран
            singleLine = false
        )

        // Селектор занимает ровно столько, сколько нужно ему по высоте
        CipherSelector(
            selected = state.selectedCipher,
            onSelected = viewModel::onCipherSelected,

        )

        // Нижнее поле — пол‑экрана

        OutlinedTextField(
            value = state.resultText,
            onValueChange = { viewModel.decryptFromOutput(it) },
            label = { Text("Result") },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            singleLine = false
        )
    }
}

