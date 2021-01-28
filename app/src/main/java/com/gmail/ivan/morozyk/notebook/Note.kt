package com.gmail.ivan.morozyk.notebook

data class Note(val id: Int, val title: String, val content: String) {
    var favorite = false
}
