package com.gmail.ivan.morozyk.notebook.model.mapper

interface BaseMapper<I, O> {

    fun map(input: I): O
}