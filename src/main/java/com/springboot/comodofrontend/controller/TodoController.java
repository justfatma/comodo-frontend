package com.springboot.comodofrontend.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.springboot.comodofrontend.model.TodoGroupIO;
import com.springboot.comodofrontend.model.TodoIO;
import com.springboot.comodofrontend.model.TodoStatus;

@Controller
public class TodoController {

  @Autowired
  RestTemplate restTemplate;

  @GetMapping("/todos")
  public String showTodoList(Model model) {

    ResponseEntity<List<TodoIO>> responseEntity =
        restTemplate.exchange("http://localhost:9002/todos", HttpMethod.GET, null,
            new ParameterizedTypeReference<List<TodoIO>>() {});
    List<TodoIO> todoList = responseEntity.getBody();

    model.addAttribute("todoList", todoList);

    return "todos";
  }

  @GetMapping("/todos/new")
  public String showNewForm(Model model) {

    model.addAttribute("todo", new TodoIO());
    model.addAttribute("pageTitle", "Add New Todo");
    return "todo_form";

  }

  @PostMapping("/todos/save")
  public String saveTodo(TodoIO todoIO, RedirectAttributes ra) {

    TodoGroupIO todoGroupIO = new TodoGroupIO();
    todoGroupIO.setId(10002L);
    todoIO.setTodoGroupIO(todoGroupIO);

    todoIO.setStatus(TodoStatus.ACTIVE);

    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    HttpEntity<TodoIO> entity = new HttpEntity<TodoIO>(todoIO, headers);

    TodoIO result = restTemplate.exchange("http://localhost:9002/todos", HttpMethod.POST, entity,
        TodoIO.class, String.class).getBody();

    if (result != null) {
      ra.addFlashAttribute("message", "The todo has been saved successfully");
    }

    return "redirect:/todos";

  }

  @GetMapping("/todos/edit/{id}")
  public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {

    TodoIO todoIO = restTemplate.getForObject("http://localhost:9002/todos/" + id, TodoIO.class);

    model.addAttribute("todo", todoIO);
    model.addAttribute("pageTitle", "Edit Todo (ID: " + id + ")");

    if (todoIO != null) {
      return "todo_form";
    } else {
      ra.addFlashAttribute("message", "Todo is not found!");
      return "redirect:/todos";
    }

  }

  @GetMapping("/todos/delete/{id}")
  public String deleteTodo(@PathVariable("id") Long id, RedirectAttributes ra) {

    String entityUrl = "http://localhost:9002/todos/" + id;
    restTemplate.delete(entityUrl);

    ra.addFlashAttribute("message", "The todo is deleted!");
    return "redirect:/todos";
  }

  // Completed Todo Methods

  @GetMapping("/completed-todos")
  public String showCompletedTodoList(Model model) {

    ResponseEntity<List<TodoIO>> responseEntity =
        restTemplate.exchange("http://localhost:9002/completedtodos", HttpMethod.GET, null,
            new ParameterizedTypeReference<List<TodoIO>>() {});
    List<TodoIO> completedTodoList = responseEntity.getBody();

    model.addAttribute("completedTodoList", completedTodoList);

    return "completed-todos";
  }

  @GetMapping("/completed-todos/activate/{id}")
  public String activateCompletedTodo(@PathVariable("id") Long id, RedirectAttributes ra) {


    String entityUrl = "http://localhost:9002/completedtodos/" + id;

    ResponseEntity<TodoIO> exchange =
        restTemplate.exchange(entityUrl, HttpMethod.PUT, null, TodoIO.class);

    ra.addFlashAttribute("message",
        "The completed todo is activated. You can see it on the active todo page.");

    return "redirect:/completed-todos";
  }

  @GetMapping("/completed-todos/delete/{id}")
  public String deleteCompletedTodo(@PathVariable("id") Long id, RedirectAttributes ra) {

    String entityUrl = "http://localhost:9002/todos/" + id;
    restTemplate.delete(entityUrl);

    ra.addFlashAttribute("message", "The completed todo is deleted!");
    return "redirect:/completed-todos";
  }
}
