package com.example.todo.model;

import java.util.Comparator;

/**
 * Created by krishna_hotha on 3/28/16.
 */
public class TodoItem implements Comparator<TodoItem>{



    private Long id;

    public TodoItem() {
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private boolean completed;

    @Override
    public int compare(TodoItem o1, TodoItem o2) {
        return o1.id.compareTo(o2.id);
    }

    public TodoItem(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", completed=" + completed +
                '}';
    }
}
