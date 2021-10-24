package com.grizzlyorange.writernotes.ui.noteslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grizzlyorange.writernotes.ui.dto.Note
import java.util.*

class NotesListViewModel : ViewModel() {
    val notes: LiveData<List<Note>>
        get() = MutableLiveData<List<Note>>(listOf(
            Note("note 1 name",
                "note 1 text",
                Calendar.getInstance().timeInMillis,
                Calendar.getInstance().timeInMillis),
            Note("note 2 name",
                "note 2 text",
                Calendar.getInstance().timeInMillis,
                Calendar.getInstance().timeInMillis)
        ))
}