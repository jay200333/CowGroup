package com.example.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.LocalExtendedColors

@Composable
fun ValidatingTextField(
    modifier: Modifier = Modifier.fillMaxWidth(),
    shape: Shape = RoundedCornerShape(10.dp),
    value: String,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit,
    validateCondition: Boolean,
    label: @Composable (() -> Unit)? = null,
    placeholder: String = "",
    errorMessage: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = false,
    readOnly: Boolean = false,
    maxLines: Int = 1,
    minLines: Int = 1,
    textStyle: TextStyle = LocalTextStyle.current
) {
    OutlinedTextField(
        modifier = modifier.clip(shape),
        shape = shape,
        value = value,
        enabled = enabled,
        onValueChange = onValueChange,
        label = label,
        placeholder = { Text(text = placeholder) },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        readOnly = readOnly,
        textStyle = textStyle,
        supportingText = {
            if (value.isNotEmpty()) {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = errorMessage,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (validateCondition.not()) {
                        MaterialTheme.colorScheme.error
                    } else {
                        LocalExtendedColors.current.success
                    }
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ValidatingTextFieldPreview() {
    ValidatingTextField(
        modifier = Modifier.fillMaxWidth(),
        value = "",
        onValueChange = {},
        validateCondition = true,
        label = null,
        placeholder = "",
        errorMessage = "",
    )
}
