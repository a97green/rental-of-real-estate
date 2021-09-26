package ru.aGreen.rentalofrealestate.controllers.dashboard;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("title", "Панель управления");
        model.addAttribute("descriptions", "Добро пожаловать в панель управления");
        return "dashboard/dashboard";
    }



}
