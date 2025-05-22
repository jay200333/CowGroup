package com.example.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.data.model.SearchHistory
import com.example.home.R

@Composable
fun HomeScreenSearchBar(
    onSearchButtonClick: (String) -> Unit,
    onDeleteSearchTermButtonClick: (String) -> Unit,
    onLogOutButtonClick: () -> Unit,
    recentSearches: List<SearchHistory>,
) {
    val textFieldState = remember { mutableStateOf("") }
    val expanded = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .semantics { isTraversalGroup = true }
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SearchBar(
                    modifier = Modifier.semantics { traversalIndex = 0f },
                    shape = RoundedCornerShape(15.dp),
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = textFieldState.value,
                            onQueryChange = { textFieldState.value = it },
                            onSearch = { expanded.value = false },
                            expanded = expanded.value,
                            onExpandedChange = { expanded.value = it },
                            placeholder = { Text("검색어를 입력해주세요.") },
                            leadingIcon = {
                                Icon(
                                    painterResource(R.drawable.baseline_filter_list_24),
                                    contentDescription = null,
                                )
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        onSearchButtonClick(textFieldState.value)
                                        expanded.value = false
                                    },
                                    modifier = Modifier.padding(end = 8.dp),
                                ) {
                                    Icon(
                                        Icons.Default.Search,
                                        contentDescription = null,
                                    )
                                }
                            },
                        )
                    },
                    expanded = expanded.value,
                    onExpandedChange = { expanded.value = it },
                    content = {
                        if (expanded.value) {
                            LazyColumn {
                                items(recentSearches) { searchHistory ->
                                    ListItem(
                                        headlineContent = { Text(searchHistory.query) },
                                        supportingContent = { Text(searchHistory.timestamp) },
                                        trailingContent = {
                                            Icon(
                                                modifier = Modifier.clickable {
                                                    onDeleteSearchTermButtonClick(
                                                        searchHistory.query
                                                    )
                                                },
                                                imageVector = Icons.Filled.Delete,
                                                contentDescription = null
                                            )
                                        },
                                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                        modifier = Modifier
                                            .clickable {
                                                textFieldState.value = searchHistory.query
                                                expanded.value = false
                                            }
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp, vertical = 4.dp),
                                    )
                                }
                            }
                        }
                    }
                )

                IconButton(
                    onClick = onLogOutButtonClick,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(top = 16.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_login_24),
                        contentDescription = "btn_home_top_bar",
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CowGroupSearchBarPreview() {
    HomeScreenSearchBar(
        onSearchButtonClick = {},
        onLogOutButtonClick = {},
        onDeleteSearchTermButtonClick = {},
        recentSearches = emptyList(),
    )
}
