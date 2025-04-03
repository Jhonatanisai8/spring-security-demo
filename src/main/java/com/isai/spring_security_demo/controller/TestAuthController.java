package com.isai.spring_security_demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/auth")
@PreAuthorize("denyAll()")
public class TestAuthController {

    @GetMapping("/hello")
    @PreAuthorize("permitAll()")
    public String hello() {
        return "Hello Word.";
    }

    @GetMapping("/helloSecured")
    @PreAuthorize("hasAuthority('READ')")
    public String helloSecured() {
        return "Hello Word Secured.";
    }
    
    @GetMapping("/helloSecured02")
    @PreAuthorize("hasAuthority('CREATED')")
    public String helloSecured02() {
        return "Hello Word Secured.";
    }

}
