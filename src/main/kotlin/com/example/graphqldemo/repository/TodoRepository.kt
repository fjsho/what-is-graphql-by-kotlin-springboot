package com.example.graphqldemo.repository

import com.example.graphqldemo.model.Todo
import org.springframework.stereotype.Repository

@Repository
class TodoRepository {
    private val todos = mutableListOf<Todo>()
    private var idCounter = 0

    init {
        // サンプルデータを追加
        addTodo("GraphQLについて学ぶ")
        addTodo("KotlinでSpring Bootアプリケーションを作る")
        addTodo("GraphQLのクエリを試す")
    }

    fun findAll(): List<Todo> = todos.toList()

    fun findById(id: Int): Todo? = todos.find { it.id == id }

    fun addTodo(title: String): Todo {
        val newTodo = Todo(++idCounter, title, false)
        todos.add(newTodo)
        return newTodo
    }

    fun updateTodo(id: Int, title: String?, completed: Boolean?): Todo? {
        val todo = findById(id) ?: return null
        val updatedTodo = todo.copy(
            title = title ?: todo.title,
            completed = completed ?: todo.completed
        )
        val index = todos.indexOfFirst { it.id == id }
        if (index >= 0) {
            todos[index] = updatedTodo
        }
        return updatedTodo
    }

    fun deleteTodo(id: Int): Boolean {
        val todo = findById(id) ?: return false
        return todos.remove(todo)
    }
}
