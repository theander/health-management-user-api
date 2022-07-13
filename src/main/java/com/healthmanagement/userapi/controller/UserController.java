package com.healthmanagement.userapi.controller;

import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
@GetMapping("/getuser")
    public String getUser(){
    return "User";
}
}
