package com.threegroup.tobedated.shareclasses.models

data class Match(
    val matchId: String,
    val userAge: Int,
    val userId: String,
    val userName: String,
    val userPicture: String,
    val formattedDate: String,
    val lastMessage: String? // might not need could get last message from the last index of list of chats
)
