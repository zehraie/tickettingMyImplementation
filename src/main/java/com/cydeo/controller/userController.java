package com.cydeo.controller;

import com.cydeo.dto.UserDTO;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class userController {
    RoleService roleService;
    UserService userService;

    public userController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createUser(Model model){

        model.addAttribute("user", new UserDTO());
        //        model.addAttribute("roles",bringmeLISTOFROLES);  // bring me all rolls
        model.addAttribute("roles",roleService.findAll());
        model.addAttribute("users", userService.findAll());

        return "user/create";

    }
 @PostMapping("/create")
    public String insertUser(@ModelAttribute("user") UserDTO user, Model model){

//     model.addAttribute("user", new UserDTO());  // PAss empty form
//     model.addAttribute("roles",roleService.findAll());

     userService.save(user);
    // model.addAttribute("users", userService.findAll());

        return "redirect:/user/create";

    }
}
