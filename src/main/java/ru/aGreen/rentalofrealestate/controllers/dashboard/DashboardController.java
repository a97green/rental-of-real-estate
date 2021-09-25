package ru.aGreen.rentalofrealestate.controllers.dashboard;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.aGreen.rentalofrealestate.models.HomeHeaders;
import ru.aGreen.rentalofrealestate.repositories.HomeHeadersRepository;

import java.util.NoSuchElementException;

@Controller
public class DashboardController {
    private final HomeHeadersRepository homeHeadersRepository;

    @Autowired
    public DashboardController(HomeHeadersRepository homeHeadersRepository) {
        this.homeHeadersRepository = homeHeadersRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Iterable<HomeHeaders> homeHeaders = homeHeadersRepository.findAll();
        for (HomeHeaders headers : homeHeaders) {
            model.addAttribute("homeHeaders", headers);
        }
        model.addAttribute("title", "Главная страница");
        model.addAttribute("descriptions", "Добро пожаловать в панель управления");
        return "dashboard/dashboard";
    }

    @PostMapping("/dashboard/{id}/save")
    public String dashboardSave(@PathVariable(value = "id") Long id,
                                @RequestParam String title,
                                @RequestParam String subtitle,
                                @RequestParam String descriptions,
                                @RequestParam String portfolioSubtitle,
                                @RequestParam String portfolioDescriptions,
                                @RequestParam String advantageDescriptions,
                                @RequestParam String address,
                                @RequestParam String email,
                                @RequestParam String phoneNumber, Model model) {
        HomeHeaders homeHeaders = homeHeadersRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        homeHeaders.setTitle(title);
        homeHeaders.setSubtitle(subtitle);
        homeHeaders.setDescriptions(descriptions);
        homeHeaders.setPortfolioSubtitle(portfolioSubtitle);
        homeHeaders.setDescriptions(portfolioDescriptions);
        homeHeaders.setAdvantageDescriptions(advantageDescriptions);
        homeHeaders.setAddress(address);
        homeHeaders.setEmail(email);
        homeHeaders.setPhoneNumber(phoneNumber);
        homeHeadersRepository.save(homeHeaders);
        return "redirect:/dashboard";
    }


}
