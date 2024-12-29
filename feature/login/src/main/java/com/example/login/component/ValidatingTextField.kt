package com.example.login.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ValidatingTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    validateCondition: Boolean,
    label: String = "",
    placeholder: String = "",
    errorMessage: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxLines: Int = 1,
    minLines: Int = 1,
) {
    Column {
        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label) },
            placeholder = { Text(text = placeholder) },
            isError = validateCondition,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            maxLines = maxLines,
            minLines = minLines,
        )
        if (validateCondition) {
            Text(
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                text = errorMessage,
                color = Color.Red,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ValidatingTextFieldPreview() {
    ValidatingTextField(
        value = "",
        onValueChange = {},
        validateCondition = true,
        label = "",
        placeholder = "",
        errorMessage = "",
    )
}
