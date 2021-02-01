package com.gmail.ivan.morozyk.notebook.mvp.contract

import com.gmail.ivan.morozyk.notebook.model.data.ToDo
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ToDoContract {

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface View : MvpView {
        fun showTODOs(toDoList: List<ToDo>)

        fun showLoadingError()

        fun showUpdateError()

        fun showEmpty()
    }

    interface Presenter {
        fun markAsCompletedButtonClicked(toDo: ToDo)
    }
}