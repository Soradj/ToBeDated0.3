package com.threegroup.tobedated

data class NewMatch(
    val matchId: String,
    val userId: String,
    val userName: String,
    val userPictures: List<String>
) {
    constructor() : this("","","", emptyList())
}
