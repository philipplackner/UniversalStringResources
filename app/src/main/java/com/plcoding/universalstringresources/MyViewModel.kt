package com.plcoding.universalstringresources

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MyViewModel: ViewModel() {

    private val errorChannel = Channel<String>()
    val errors = errorChannel.receiveAsFlow()

    var name by mutableStateOf("")

    fun onNameChange(newName: String) {
        name = newName
    }

    fun validateInputs() {
        viewModelScope.launch {
            if(name.length < MIN_NAME_LENGTH) {
                errorChannel.send(
                    "The name must be at least $MIN_NAME_LENGTH characters long"
                )
            }
        }
    }

    companion object {
        const val MIN_NAME_LENGTH = 3
    }
}