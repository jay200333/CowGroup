package com.example.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.home.R
import com.example.model.MemberInfo

@Composable
fun MemberItem(memberInfo: MemberInfo) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium,)
            .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.medium,)
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(32.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = memberInfo.name,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
            )

            Icon(
                modifier = Modifier.padding(start = 8.dp),
                painter = painterResource(
                    if (memberInfo.gender == "MALE") R.drawable.baseline_male_24 else R.drawable.baseline_female_24,
                ),
                contentDescription = "gender_icon",
                tint = if (memberInfo.gender == "MALE") Color(0XFF2E27F9) else Color(0XFFF674D6),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MemberItemPreview() {
    MemberItem(
        MemberInfo("안드로이드", "MALE"),
    )
}
