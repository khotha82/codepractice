package com.example.todo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

import com.example.todo.model.TodoItem;
import com.example.todo.repository.InMemoryTodoRepository;
import com.example.todo.repository.TodoRepository;

/**
 * Created by krishna_hotha on 3/28/16.
 */
public class CommandLineInputHandler {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private TodoRepository TodoRepository = new InMemoryTodoRepository();
    AtomicLong id=new AtomicLong();
    public void printOptions() {
        System.out.println("\n--- To Do Application ---");
        System.out.println("Please make a choice:");
        System.out.println("(a)ll items");
        System.out.println("(f)ind a specific item");
        System.out.println("(i)nsert a new item");
        System.out.println("(u)pdate an existing item");
        System.out.println("(d)elete an existing item");
        System.out.println("(e)xit");
    }

    public String readInput() throws IOException {
        return br.readLine();
    }

    public void processInput(CommandLineInput input) throws IOException {
        if (input == null) {
            handleUnknownInput();
        } else {
            switch (input) {
                case FIND_ALL:
                    printAllTodoItems();
                    break;
                case FIND_BY_ID:
                    printTodoItem();
                    break;
                case INSERT:
                    insertTodoItem();
                    break;
                case UPDATE:
                    updateTodoItem();
                    break;
                case DELETE:
                    deleteTodoItem();
                    break;
                case EXIT:
                    break;
                default:
                    handleUnknownInput();
            }
        }
    }

    private Long askForItemId() throws IOException {
        System.out.println("Please enter the item ID:");
        String input = readInput();
        return Long.parseLong(input);
    }

    private TodoItem askForNewTodoAction() throws IOException {

        System.out.println("Please enter the name of the item:");
        TodoItem TodoItem = new TodoItem(id.incrementAndGet(),readInput());

        return TodoItem;
    }

    private void printAllTodoItems() {
        Collection<TodoItem> TodoItems = TodoRepository.findAll();

        if (TodoItems.isEmpty()) {
            System.out.println("Nothing to do. Go relax!");
        } else {
            for (TodoItem TodoItem : TodoItems) {
                System.out.println(TodoItem);
            }
        }
    }

    private void printTodoItem() throws IOException {
        TodoItem TodoItem = findTodoItem();

        if (TodoItem != null) {
            System.out.println(TodoItem);
        }
    }

    private TodoItem findTodoItem() throws IOException {
        Long id = askForItemId();
        TodoItem TodoItem = TodoRepository.findById(id);

        if (TodoItem == null) {
            System.err.println("To do item with ID " + id + " could not be found.");
        }

        return TodoItem;
    }

    private void insertTodoItem() throws IOException {
        TodoItem TodoItem = askForNewTodoAction();
        TodoItem.setId(id.incrementAndGet());
        TodoRepository.insert(TodoItem);
        System.out.println("Successfully inserted to do item with ID " + id + ".");
    }

    private void updateTodoItem() throws IOException {
        TodoItem TodoItem = findTodoItem();

        if (TodoItem != null) {
            System.out.println(TodoItem);
            System.out.println("Please enter the name of the item:");
            TodoItem.setName(readInput());
            System.out.println("Please enter the done status the item:");
            TodoItem.setCompleted(Boolean.parseBoolean(readInput()));
            TodoRepository.update(TodoItem);
            System.out.println("Successfully updated to do item with ID " + TodoItem.getId() + ".");
        }
    }

    private void deleteTodoItem() throws IOException {
        TodoItem TodoItem = findTodoItem();

        if (TodoItem != null) {
            TodoRepository.delete(TodoItem);
            System.out.println("Successfully deleted to do item with ID " + TodoItem.getId() + ".");
        }
    }

    private void handleUnknownInput() {
        System.out.println("Please select a valid option!");
    }
}
