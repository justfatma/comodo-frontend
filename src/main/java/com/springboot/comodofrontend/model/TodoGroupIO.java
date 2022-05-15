package com.springboot.comodofrontend.model;

public class TodoGroupIO {

  private Long id;
  private String name;
  private TodoUserIO todoUserIO;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public TodoUserIO getTodoUserIO() {
    return todoUserIO;
  }

  public void setTodoUserIO(TodoUserIO todoUserIO) {
    this.todoUserIO = todoUserIO;
  }


}
