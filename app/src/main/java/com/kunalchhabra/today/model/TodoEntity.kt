package com.kunalchhabra.today.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class TodoEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val title: String,
    var isDone: Boolean
)
