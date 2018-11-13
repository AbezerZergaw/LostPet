package com.example.demo;


import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class MainController {


    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;
    @Autowired
    PetRepo petRepo;

    @Autowired
    CloudinaryConfig cloudinaryConfig;


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
    public String processPet(@ModelAttribute Pet pet, @RequestParam("file")MultipartFile file){


        if(file.isEmpty()){
            return "redirect:/processpet";
        }
        try{
            Map uploadResult = cloudinaryConfig.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            pet.setImg(uploadResult.get("url").toString());
            petRepo.save(pet);

        }catch (IOException e){
            e.printStackTrace();
            return "redirect:/processpet";

        }
        return "redirect:/";


       /* if (result.hasErrors()) {
            return "addpetlost";

        }

        pet.setUser(user);
        petRepo.save(pet);
        return "redirect:/";*/
    }

}
