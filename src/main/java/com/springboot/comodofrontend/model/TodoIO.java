package com.springboot.comodofrontend.model;

import java.sql.Date;

public class TodoIO {

  private Long id;
  private String explanation;
  private Priority priority;
  private TodoGroupIO todoGroupIO;
  private Date dueDate;
  private TodoStatus status;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getExplanation() {
    return explanation;
  }

  public void setExplanation(String explanation) {
    this.explanation = explanation;
  }

  public Priority getPriority() {
    return priority;
  }

  public void setPriority(Priority priority) {
    this.priority = priority;
  }


  public Date getDueDate() {
    return dueDate;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

  public TodoStatus getStatus() {
    return status;
  }

  public void setStatus(TodoStatus status) {
    this.status = status;
  }

  public TodoGroupIO getTodoGroupIO() {
    return todoGroupIO;
  }

  public void setTodoGroupIO(TodoGroupIO todoGroupIO) {
    this.todoGroupIO = todoGroupIO;
  }

  @Override
  public String toString() {
    return "TodoIO [id=" + id + ", explanation=" + explanation + ", priority=" + priority
        + ", todoGroupIO=" + todoGroupIO + ", dueDate=" + dueDate + ", status=" + status + "]";
  }

}
