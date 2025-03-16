package com.example.graphqldemo.controller

import com.example.graphqldemo.repository.TodoRepository
import com.example.graphqldemo.model.Todo
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class TodoController(private val todoRepository: TodoRepository) {

    @QueryMapping
    fun todos(): List<Todo> = todoRepository.findAll()

    @QueryMapping
    fun todo(@Argument id: Int): Todo? = todoRepository.findById(id)

    @MutationMapping
    fun addTodo(@Argument title: String): Todo = todoRepository.addTodo(title)

    @MutationMapping
    fun updateTodo(
        @Argument id: Int,
        @Argument title: String?,
        @Argument completed: Boolean?
    ): Todo? = todoRepository.updateTodo(id, title, completed)

    @MutationMapping
    fun deleteTodo(@Argument id: Int): Boolean = todoRepository.deleteTodo(id)
}