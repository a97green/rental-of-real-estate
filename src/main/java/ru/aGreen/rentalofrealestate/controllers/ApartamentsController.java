package ru.aGreen.rentalofrealestate.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApartamentsController {

    @GetMapping("/apartments")
    public String apartments(Model model) {
        return "apartments";
    }


}
