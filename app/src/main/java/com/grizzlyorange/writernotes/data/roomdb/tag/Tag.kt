package com.grizzlyorange.writernotes.data.roomdb.tag

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tag(
    @PrimaryKey(autoGenerate = true)
    val tagId: Long = 0,
    var name: String = ""
) {
}