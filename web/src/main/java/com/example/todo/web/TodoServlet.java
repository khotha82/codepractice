package com.example.todo.web;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.todo.model.TodoItem;
import com.example.todo.repository.InMemoryTodoRepository;
import com.example.todo.repository.TodoRepository;

/**
 * Created by krishna_hotha on 3/28/16.
 */
public class TodoServlet extends HttpServlet {

    private TodoRepository todoRepository = new InMemoryTodoRepository();

    public static final String FIND_ALL_SERVLET_PATH = "/all";
    public static final String INDEX_PAGE = "/jsp/todo-list.jsp";
    private TodoRepository TodoRepository = new InMemoryTodoRepository();
    AtomicLong id=new AtomicLong();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        String view = processRequest(servletPath, request);
        RequestDispatcher dispatcher = request.getRequestDispatcher(view);
        dispatcher.forward(request, response);
    }

    private String processRequest(String servletPath, HttpServletRequest request) {
        if (servletPath.equals(FIND_ALL_SERVLET_PATH)) {
            List<TodoItem> TodoItems = TodoRepository.findAll();
            request.setAttribute("TodoItems", TodoItems);
            request.setAttribute("stats", determineStats(TodoItems));
            request.setAttribute("filter", "all");
            return INDEX_PAGE;
        } else if (servletPath.equals("/active")) {
            List<TodoItem> TodoItems = TodoRepository.findAll();
            request.setAttribute("TodoItems", filterBasedOnStatus(TodoItems, true));
            request.setAttribute("stats", determineStats(TodoItems));
            request.setAttribute("filter", "active");
            return INDEX_PAGE;
        } else if (servletPath.equals("/completed")) {
            List<TodoItem> TodoItems = TodoRepository.findAll();
            request.setAttribute("TodoItems", filterBasedOnStatus(TodoItems, false));
            request.setAttribute("stats", determineStats(TodoItems));
            request.setAttribute("filter", "completed");
            return INDEX_PAGE;
        }
        if (servletPath.equals("/insert")) {
            TodoItem TodoItem = new TodoItem();
            TodoItem.setId(id.incrementAndGet());
            TodoItem.setName(request.getParameter("name"));
            TodoRepository.insert(TodoItem);
            return "/" + request.getParameter("filter");
        } else if (servletPath.equals("/update")) {
            TodoItem TodoItem = TodoRepository.findById(Long.parseLong(request.getParameter("id")));

            if (TodoItem != null) {
                TodoItem.setName(request.getParameter("name"));
                TodoRepository.update(TodoItem);
            }

            return "/" + request.getParameter("filter");
        } else if (servletPath.equals("/delete")) {
            TodoItem TodoItem = TodoRepository.findById(Long.parseLong(request.getParameter("id")));

            if (TodoItem != null) {
                TodoRepository.delete(TodoItem);
            }

            return "/" + request.getParameter("filter");
        } else if (servletPath.equals("/toggleStatus")) {
            TodoItem TodoItem = TodoRepository.findById(Long.parseLong(request.getParameter("id")));

            if (TodoItem != null) {
                boolean completed = "on".equals(request.getParameter("toggle")) ? true : false;
                TodoItem.setCompleted(completed);
                TodoRepository.update(TodoItem);
            }

            return "/" + request.getParameter("filter");
        } else if (servletPath.equals("/clearCompleted")) {
            List<TodoItem> TodoItems = TodoRepository.findAll();

            for (TodoItem TodoItem : TodoItems) {
                if (TodoItem.isCompleted()) {
                    TodoRepository.delete(TodoItem);
                }
            }

            return "/" + request.getParameter("filter");
        }

        return FIND_ALL_SERVLET_PATH;
    }

    private List<TodoItem> filterBasedOnStatus(List<TodoItem> TodoItems, boolean active) {
        List<TodoItem> filteredTodoItems = new ArrayList<TodoItem>();

        for (TodoItem TodoItem : TodoItems) {
            if (TodoItem.isCompleted() != active) {
                filteredTodoItems.add(TodoItem);
            }
        }

        return filteredTodoItems;
    }

    private TodoListStats determineStats(List<TodoItem> TodoItems) {
        TodoListStats TodoListStats = new TodoListStats();

        for (TodoItem TodoItem : TodoItems) {
            if (TodoItem.isCompleted()) {
                TodoListStats.addCompleted();
            } else {
                TodoListStats.addActive();
            }
        }

        return TodoListStats;
    }

    public class TodoListStats {
        private int active;
        private int completed;

        private void addActive() {
            active++;
        }

        private void addCompleted() {
            completed++;
        }

        public int getActive() {
            return active;
        }

        public int getCompleted() {
            return completed;
        }

        public int getAll() {
            return active + completed;
        }
    }
}
