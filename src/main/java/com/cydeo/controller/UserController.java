package com.cydeo.controller;

import com.cydeo.dto.UserDTO;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    RoleService roleService;
    UserService userService;

    public UserController(RoleService roleService, UserService userService) {
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
 @PostMapping("/create")  // submit button, yukarian buraya ModelAttribute ile tasidim
    public String insertUser(@ModelAttribute("user") UserDTO user, Model model){

//     model.addAttribute("user", new UserDTO());  // PAss empty form
//     model.addAttribute("roles",roleService.findAll());

     userService.save(user);
    // model.addAttribute("users", userService.findAll());

        return "redirect:/user/create";  // redirect sayesinde tekrar yazmama gerek yok

    }
    // I need username so I will get it from view html then sent it to GetMapping below
    @GetMapping("/update/{username}") // path variable, specific user will come, so go to map bring the user to UI
    public String editUser(@PathVariable("username") String username, Model model){   //from the browser I will catch user name and bring it  to my java code.

       //work here
        //define the .attribute
        //user ---> attribute I need to pass information to view
        // roles ---> I need to bind this information as well
        // users --> need to bind

        model.addAttribute("user", userService.findById(username));
        model.addAttribute("roles",roleService.findAll());
        model.addAttribute("users", userService.findAll());


        return "/user/update";
                // from UI to view then carry it to code

    }
    @PostMapping("/update")
    public String updateUser(UserDTO user){ //@PathVariable("username") String username,  attim bunu endpoint den username sildim
        userService.update(user);

        return "redirect:/user/create";
    }

    @GetMapping("/delete/{username}")
    public String deleteById(@PathVariable("username") String username){
        userService.deleteById(username);
        return "redirect:/user/create";

    }
}
