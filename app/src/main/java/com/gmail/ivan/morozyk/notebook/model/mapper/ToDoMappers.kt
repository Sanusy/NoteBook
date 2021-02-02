package com.gmail.ivan.morozyk.notebook.model.mapper

import com.gmail.ivan.morozyk.notebook.model.data.ToDo
import com.gmail.ivan.morozyk.notebook.model.data.ToDoDto

class ToDoMapper : BaseMapper<ToDo, ToDoDto> {

    override fun map(input: ToDo): ToDoDto = with(input) { ToDoDto(userId, id, title, completed) }
}

class ToDoDtoMapper : BaseMapper<ToDoDto, ToDo> {

    override fun map(input: ToDoDto): ToDo = with(input) { ToDo(userId, id, title, completed) }
}