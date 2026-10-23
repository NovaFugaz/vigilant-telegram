package com.bexai.bexainventory.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    error: String? = null,
    placeholder: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
){
    OutlinedTextField(
        value = value,
        onValueChange =  onValueChange,
        label = { Text(label) },
        placeholder = {
            if(placeholder != null){
                Text(text = placeholder)
            }
        },
        isError = error != null,

        // para ponerlo en rojito
        supportingText = {
            if (error != null){
                Text(text = error, color = MaterialTheme.colorScheme.error)
            }
        },
        visualTransformation = visualTransformation,
        modifier = Modifier.width(300.dp),

        // este evita saltos de linea con el enter
        singleLine = true
    )
}