package com.shubh.rest.webServices.restfulwebservices.helloWorld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping("/hello-world")
    public String helloWorld(){
        return "Hello world";
    }

    @GetMapping("/hello")
    public HelloWorldBean WorldBean(){
        return new HelloWorldBean("Message through bean");
    }
}
