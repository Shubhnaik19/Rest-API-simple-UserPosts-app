package com.shubh.rest.webServices.restfulwebservices.user;

import com.shubh.rest.webServices.restfulwebservices.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {
    @Autowired
    private userDAO service;

    @GetMapping("/users")
    public List<User> findUser(){
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    private EntityModel<User> retrieveUser(@PathVariable int id){
        User user = service.findUserWithId(id);
        if(user == null)
        {
            throw new UserNotFoundException("------");
        }

        EntityModel<User> model= EntityModel.of(user);

        WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).findUser());
        model.add(linkToUsers.withRel("all-users"));

        return model;
    }

    @PostMapping("/users")
    private ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        User savedUser = service.save(user);
        URI location =ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    private ResponseEntity<User> deleteUser(@PathVariable int id){
        return new ResponseEntity(service.remove(id), HttpStatus.NO_CONTENT);
    }
}
