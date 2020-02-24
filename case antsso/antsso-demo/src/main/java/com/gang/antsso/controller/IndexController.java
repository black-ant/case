package com.gang.antsso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class IndexController {

    @Autowired
    private UserController userController;

    @GetMapping("/")
    public Object index(Authentication authentication) {
        return userController.getCurrentUser(authentication);
    }
}
