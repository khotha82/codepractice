package com.example.todo.repository;

import java.util.List;

import com.example.todo.model.TodoItem;

/**
 * Created by krishna_hotha on 3/28/16.
 */
public interface TodoRepository {

    List<TodoItem> findAll();
    void insert(TodoItem todoItem);
    void update(TodoItem todoItem);
    void delete(TodoItem todoItem);
    TodoItem findById(Long id);
}
