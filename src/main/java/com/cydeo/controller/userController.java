package com.cydeo.controller;

import com.cydeo.dto.UserDTO;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    // I need username so I will get it from view html then sent it to GetMapping below
    @GetMapping("/update/{username}") // path variable, specific user will come, so go to map bring the user to UI
    public String editUser(@PathVariable("username") String username, Model model){   //from the browser I will catch user name and bring it  to my java code.

       //work here
        //define the .attribute
        //user ---> attribute I need to pass information to view
        // roles ---> I need to bind this information as well
        // users --> need to bind

        model.addAttribute("user", new UserDTO());
        model.addAttribute("roles",roleService.findAll());
        model.addAttribute("users", userService.findAll());


        return "/user/update";
                // from UI to view then carry it to code

    }
}
