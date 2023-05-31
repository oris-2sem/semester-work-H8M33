package com.example.orisimpl.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/site")
public class WebController {

    @GetMapping("")
    public String getMainPage(){
        return "main";
    }

    @GetMapping("/registration")
    public String getRegisterPage(){
        return "register";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/activation")
    public String getActivationPage(){return "activation";}

    @GetMapping("/users")
    public String getUsersPage(){
        return "users";
    }

    @GetMapping("/check")
    public String getCheckPage(){ return "check";}

    @GetMapping("/chats")
    public String getChatsPage(){
        return "chats";
    }

    @GetMapping("/groups")
    public String getGroupsPage(){
        return "groups";
    }

    @GetMapping("/posts")
    public String getPostsPage(){
        return "posts";
    }

    @GetMapping("/messages")
    public String getMessagesPage(){
        return "messages";
    }

}
