package ru.aGreen.rentalofrealestate.controllers.dashboard;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("title", "Главная страница");
        model.addAttribute("descriptions", "Добро пожаловать в панель управления");
        return "dashboard/dashboard";
    }

    @GetMapping("/hz")
    public String hz(Model model) {
        model.addAttribute("title", "Главная страница");
        model.addAttribute("descriptions", "Добро пожаловать в панель управления");
        return "dashboard/drop";
    }
}
