package com.example.todo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.example.todo.model.TodoItem;

/**
 * Created by krishna_hotha on 3/28/16.
 */
public class InMemoryTodoRepository implements TodoRepository {


    private ConcurrentHashMap<Long,TodoItem> todoItemConcurrentHashMap=new ConcurrentHashMap<>();

    @Override
    public List<TodoItem> findAll() {
        List<TodoItem> todoItems=new ArrayList<>(todoItemConcurrentHashMap.values());
        return todoItems;
    }

    @Override
    public void insert(TodoItem todoItem) {
        todoItemConcurrentHashMap.putIfAbsent(todoItem.getId(),todoItem);
    }

    @Override
    public void update(TodoItem todoItem) {

        todoItemConcurrentHashMap.replace(todoItem.getId(),todoItem);
    }

    @Override
    public void delete(TodoItem todoItem) {

        todoItemConcurrentHashMap.remove(todoItem.getId());
    }

    @Override
    public TodoItem findById(Long id) {
        return todoItemConcurrentHashMap.get(id);
    }
}
