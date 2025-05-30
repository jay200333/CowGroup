package com.example.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designsystem.component.CowGroupDialog
import com.example.home.viewmodel.CommentUIState
import com.example.home.viewmodel.CommentViewModel
import com.example.model.Comment

@Composable
fun CommentBottomSheetContent(
    viewModel: CommentViewModel = hiltViewModel(),
    postId: Int?
) {
    val uiState: CommentUIState by viewModel.uiState.collectAsState()

    CommentBottomSheetContent(
        updateComment = viewModel::updateComment,
        postComment = viewModel::postComment,
        deleteComment = viewModel::deleteComment,
        postId = postId,
        comment = uiState.comment,
        commentList = uiState.commentList,
        isEditMode = uiState.isEditMode,
        sendButtonEnabled = uiState.sendButtonEnabled
    )

    LaunchedEffect(postId) {
        if (postId != null) {
            viewModel.getCommentList(postId)
        }
    }
}

@Composable
fun CommentBottomSheetContent(
    updateComment: (String) -> Unit,
    postComment: (Int) -> Unit,
    deleteComment: (Int?, Int) -> Unit,
    postId: Int?,
    comment: String,
    commentList: List<Comment>,
    isEditMode: Boolean,
    sendButtonEnabled: Boolean
) {
    var showCommentDialog by remember { mutableStateOf(false) }
    var selectedCommentId by remember { mutableStateOf<Int?>(null) }

    if (showCommentDialog) {
        CowGroupDialog(
            title = "댓글 삭제",
            confirmButtonMessage = "확인",
            dismissButtonMessage = "취소",
            onDismissRequest = {
                showCommentDialog = false
            },
            onDismiss = {
                showCommentDialog = false
            },
            content = {
                Text(
                    text = "댓글을 삭제하시겠습니까?",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            },
            onConfirm = {
                deleteComment(postId, selectedCommentId!!)
                showCommentDialog = false
            }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "댓글",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "${commentList.size}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        LazyColumn(
            modifier = Modifier.height(200.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            items(commentList.size) { index ->
                val commentInfo = commentList[index]
                CommentItem(
                    comment = commentInfo,
                    onDeleteButtonClick = {
                        showCommentDialog = true
                        selectedCommentId = commentInfo.id
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp)),
                shape = RoundedCornerShape(10.dp),
                textStyle = MaterialTheme.typography.titleMedium,
                value = comment,
                onValueChange = updateComment,
                label = { Text(text = "댓글을 입력해주세요.") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.onTertiary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.onTertiary
                )
            )

            Button(
                onClick = { postComment(postId!!) },
                shape = RoundedCornerShape(10.dp),
                enabled = sendButtonEnabled,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = if (isEditMode) "수정" else "전송",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun CommentBottomSheetContentPreview() {
//    CowGroupTheme {
//        CommentBottomSheetContent()
//    }
//}