package com.shubh.rest.webServices.restfulwebservices.user;

import com.shubh.rest.webServices.restfulwebservices.Post.Post;
import com.shubh.rest.webServices.restfulwebservices.Post.PostRepository;
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
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class userJPAResource {

        @Autowired
        private userDAO service;
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private PostRepository postRepository;

        @GetMapping("/jpa/users")
        public List<User> findUser(){
            return userRepository.findAll();
        }

        @GetMapping("/jpa/users/{id}")
        private EntityModel<User> retrieveUser(@PathVariable int id){
            Optional<User> user = userRepository.findById(id);
            if(!user.isPresent())
            {
                throw new UserNotFoundException("------");
            }

            EntityModel<User> model= EntityModel.of(user.get());

            WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).findUser());
            model.add(linkToUsers.withRel("all-users"));

            return model;
        }

        @PostMapping("/jpa/users")
        private ResponseEntity<Object> createUser(@Valid @RequestBody User user){
            User savedUser = userRepository.save(user);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
            return ResponseEntity.created(location).build();
        }

        @DeleteMapping("/jpa/users/{id}")
        private void deleteUser(@PathVariable int id){
            userRepository.deleteById(id);
        }

        @GetMapping("/jpa/users/{id}/posts")
        public List<Post> findPost(@PathVariable int id){
            Optional<User> option = userRepository.findById(id);
            if(!option.isPresent())
            {
                throw new UserNotFoundException("User is not present in out database");
            }
            return option.get().getPosts();
        }

        @PostMapping("/jpa/users/{id}/post")
        private ResponseEntity<Object> createPost(@PathVariable int id, @Valid @RequestBody Post post){
            Optional<User> option = userRepository.findById(id);
            if(!option.isPresent())
            {
                throw new UserNotFoundException("User is not present in out database");
            }
            User user = option.get();

            post.setUser(user);

            postRepository.save(post);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
            return ResponseEntity.created(location).build();
        }

    @DeleteMapping("/jpa/post/{id}")
    private void deletePost(@PathVariable int id){
        postRepository.deleteById(id);
    }

    @DeleteMapping("/jpa/users/{id}/posts")
    private void deletePostByUserId(@PathVariable int id){
        postRepository.deletePosts(id);
    }
}

