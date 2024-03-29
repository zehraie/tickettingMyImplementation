package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/project")
public class ProjectController {


 ProjectService projectService;
 UserService userService;


    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createProject(Model model){
     model.addAttribute("project",new ProjectDTO());
     model.addAttribute("projects", projectService.findAll());
     model.addAttribute("managers",userService.findManagers());
        return "/project/create";
    }

    @PostMapping("/create")
    public String insertProject(ProjectDTO project){  // ModelAtttribute kullanmaliyiz to carry the data for the "project", spring anlar kullanmasakta

        //project.setProjectStatus(Status.OPEN);  bu businee logic burada kullanilmaz  go projectImp
        projectService.save(project);
        return "redirect:/project/create";
    }

    @GetMapping("/delete/{projectcode}")
    public String deleteProject(@PathVariable("projectcode") String projectcode){
        projectService.deleteById(projectcode);
        return "redirect:/project/create";
    }
     @GetMapping("/complete/{projectcode}")
    public String completeProject(@PathVariable("projectcode") String projectcode){
        projectService.complete(projectService.findById(projectcode));
     return "redirect:/project/create";
    }

    @GetMapping("update/{projectcode}")
    public String editUpdateProject(@PathVariable("projectcode") String projectcode, Model model){
        //all attributes
        model.addAttribute("project",projectService.findById(projectcode));
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("managers",userService.findManagers());
     return "/project/update";
    }

    @PostMapping("update")
    public String UpdateProject(ProjectDTO project){
        projectService.update(project);

        return "redirect:/project/create";
    }

    @GetMapping("/manager/project-status")  // call the view
    public String getProjectByManager(Model model){

        UserDTO manager = userService.findById("john@cydeo.com");

        List<ProjectDTO> projects = projectService.getCountedListOfProjectDTO(manager);

        model.addAttribute("projects",projects);

        return "/manager/project-status";
    }

    @GetMapping("/manager/complete/{projectCode}")
    public String managerCompleteProject(@PathVariable("projectCode") String projectCode) {
        projectService.complete(projectService.findById(projectCode));
        return "redirect:/project/manager/project-status";
    }
}
