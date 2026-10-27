package com.bexai.bexaiinventory.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    error: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { if (placeholder != null) Text(placeholder) },
        isError = error != null,

        supportingText = {
            if (error != null) Text(text = error, color = MaterialTheme.colorScheme.error)
        },

        visualTransformation = visualTransformation,
        modifier = modifier.fillMaxWidth(),
        // este es para que no le hagan enter a los textfield
        singleLine = true
    )
}