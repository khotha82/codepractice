package com.example.todo.repository;

import com.example.todo.model.TodoItem;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by krishna_hotha on 4/6/16.
 */
public class InMemoryTodoRepositoryTest {

    private TodoRepository inMemoryRepository;


    @Before
    public void setup(){

        inMemoryRepository=new InMemoryTodoRepository();
    }
    @Test
    public void insertTodoItem(){

        TodoItem newTodoItem=new TodoItem();
        newTodoItem.setName("My item");
        newTodoItem.setId(123l);
        inMemoryRepository.insert(newTodoItem);

        TodoItem persistedItem=inMemoryRepository.findById(123l);
        assertNotNull(persistedItem);
        assertEquals(newTodoItem,persistedItem);
    }
}
