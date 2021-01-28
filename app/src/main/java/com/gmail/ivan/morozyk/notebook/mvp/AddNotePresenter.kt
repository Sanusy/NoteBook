package com.gmail.ivan.morozyk.notebook.mvp

import com.gmail.ivan.morozyk.notebook.App
import com.gmail.ivan.morozyk.notebook.Note
import moxy.MvpPresenter

class AddNotePresenter : MvpPresenter<AddNoteContract.View>(), AddNoteContract.Presenter {
    override fun saveNote(noteTitle: String, noteContent: String) {
        var error = false
        if (noteTitle.isEmpty()) {
            error = true
            viewState.showTitleError()
        }
        if (noteContent.isEmpty()) {
            error = true
            viewState.showContentError()
        }
        if (error) {
            return
        }
        App.noteStorage.add(Note(App.idGenerator, noteTitle, noteContent))
        viewState.back()
    }
}