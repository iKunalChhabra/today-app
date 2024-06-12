package com.kunalchhabra.today.data

fun motivationalToast(): String {
    val messages = listOf(
        "You are doing great!",
        "You are amazing!",
        "You are awesome!",
        "You are the best!",
        "You are a star!",
        "You are a champion!",
        "You are a winner!",
        "You are a superhero!",
        "You are a legend!",
        "You are a rockstar!",
        "You are a genius!",
        "You are a master!",
        "You are a wizard!",
        "You are a ninja!",
        "You are a warrior"
    )
    return messages.random()
}

fun addTodoToast():String{
    return "One step at a time"
}