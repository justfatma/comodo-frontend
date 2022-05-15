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

@Controller
public class GroupController {

  @Autowired
  RestTemplate restTemplate;

  @GetMapping("/groups")
  public String showGroupList(Model model) {

    ResponseEntity<List<TodoGroupIO>> responseEntity =
        restTemplate.exchange("http://localhost:9001/groups", HttpMethod.GET, null,
            new ParameterizedTypeReference<List<TodoGroupIO>>() {});
    List<TodoGroupIO> groupList = responseEntity.getBody();

    model.addAttribute("groupList", groupList);

    return "groups";
  }

  @GetMapping("/groups/new")
  public String showNewForm(Model model) {

    model.addAttribute("group", new TodoGroupIO());
    model.addAttribute("pageTitle", "Add New Group");
    return "group_form";

  }

  @PostMapping("/groups/save")
  public String saveGroup(TodoGroupIO todoGroupIO, RedirectAttributes ra) {

    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    HttpEntity<TodoGroupIO> entity = new HttpEntity<TodoGroupIO>(todoGroupIO, headers);

    TodoGroupIO result = restTemplate.exchange("http://localhost:9001/groups", HttpMethod.POST,
        entity, TodoGroupIO.class, String.class).getBody();

    if (result != null) {
      ra.addFlashAttribute("message", "The group has been saved successfully");
    }

    return "redirect:/groups";

  }

  @GetMapping("/groups/edit/{id}")
  public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {

    TodoGroupIO todoGroupIO =
        restTemplate.getForObject("http://localhost:9001/groups/" + id, TodoGroupIO.class);

    model.addAttribute("group", todoGroupIO);
    model.addAttribute("pageTitle", "Edit Group (ID: " + id + ")");

    if (todoGroupIO != null) {
      return "group_form";
    } else {
      ra.addFlashAttribute("message", "Group is not found!");
      return "redirect:/groups";
    }

  }

  @GetMapping("/groups/delete/{id}")
  public String deleteGroup(@PathVariable("id") Long id, RedirectAttributes ra) {

    String entityUrl = "http://localhost:9001/groups/" + id;
    restTemplate.delete(entityUrl);

    return "redirect:/groups";
  }

}
