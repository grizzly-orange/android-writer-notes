package com.grizzlyorange.writernotes.data.roomdb.entities.tag

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    val tagId: Long = 0,
    var name: String = ""
) {
}