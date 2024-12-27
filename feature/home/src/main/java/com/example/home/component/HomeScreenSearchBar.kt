package com.example.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.home.R

@Composable
fun HomeScreenSearchBar(onTopBarActionButtonClick: () -> Unit, iconId: Int) {
    val textFieldState = remember { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    Box(
        Modifier
            .fillMaxWidth()
            .semantics { isTraversalGroup = true },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SearchBar(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 16.dp)
                    .semantics { traversalIndex = 0f },
                inputField = {
                    SearchBarDefaults.InputField(
                        query = textFieldState.value,
                        onQueryChange = { textFieldState.value = it },
                        onSearch = { expanded = false },
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        placeholder = { Text("검색어를 입력해주세요.") },
                        leadingIcon = {
                            Icon(
                                painterResource(R.drawable.baseline_filter_list_24),
                                contentDescription = null,
                            )
                        },
                        trailingIcon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = null,
                            )
                        },
                    )
                },
                expanded = expanded,
                onExpandedChange = { expanded = it },
            ) {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    repeat(4) { idx ->
                        val resultText = "Suggestion $idx"
                        ListItem(
                            headlineContent = { Text(resultText) },
                            supportingContent = { Text("Additional info") },
                            leadingContent = { Icon(Icons.Filled.Star, contentDescription = null) },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                            modifier =
                            Modifier
                                .clickable {
                                    textFieldState.value = resultText
                                    expanded = false
                                }
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp),
                        )
                    }
                }
            }

            IconButton(onClick = onTopBarActionButtonClick, modifier = Modifier.padding(end = 8.dp)) {
                Icon(
                    painter = painterResource(iconId),
                    contentDescription = "btn_home_top_bar",
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CowGroupSearchBarPreview() {
    HomeScreenSearchBar(
        onTopBarActionButtonClick = {},
        iconId = R.drawable.baseline_login_24,
    )
}
