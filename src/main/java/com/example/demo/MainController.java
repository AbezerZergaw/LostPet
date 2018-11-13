package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class MainController {


    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;
    @Autowired
    PetRepo petRepo;


    @Autowired
    private UserService userService;


    @RequestMapping("/")
    public String homePage(){

        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }

    @RequestMapping("/register")
    public String signUp(Model model) {

        model.addAttribute("user", new User());


        return "registerform";
    }

    @PostMapping("/processform")
    public String processRegistration(@Valid @ModelAttribute("user") User user, BindingResult result) {

        if (result.hasErrors()) {
            return "registerform";
        }

        userService.saveUser(user);

        return "redirect:/";
    }
    @GetMapping("/addpet")
    public String addMessage(Model model) {

        model.addAttribute("pet", new Pet());
        return "addpetlost";

    }
    @PostMapping("/processpet")
    public String processPet(@Valid @ModelAttribute("message") Pet pet, @AuthenticationPrincipal User user, BindingResult result) {

        if (result.hasErrors()) {
            return "addpetlost";

        }

        pet.setUser(user);
        petRepo.save(pet);
        return "redirect:/";
    }

}
