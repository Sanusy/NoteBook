package com.gmail.ivan.morozyk.notebook.mvp.presenter

import com.gmail.ivan.morozyk.notebook.App
import com.gmail.ivan.morozyk.notebook.mvp.contract.NoteListContract
import moxy.MvpPresenter

class NoteListPresenter : MvpPresenter<NoteListContract.View>(), NoteListContract.Presenter {

    override fun attachView(view: NoteListContract.View?) {
        super.attachView(view)

        if (App.noteStorage.isEmpty()) viewState.showEmpty() else viewState.showNotes(App.noteStorage)
    }

    override fun addToFavorite(noteId: Int) {
        App.noteStorage.find { it.id == noteId }?.let {
            it.favorite = true
            viewState.showNotes(App.noteStorage)
        }
    }
}