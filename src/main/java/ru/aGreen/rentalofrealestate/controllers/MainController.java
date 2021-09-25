package ru.aGreen.rentalofrealestate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.aGreen.rentalofrealestate.models.*;
import ru.aGreen.rentalofrealestate.repositories.AboutCompanyReposiroty;
import ru.aGreen.rentalofrealestate.repositories.AdvantageRepository;
import ru.aGreen.rentalofrealestate.repositories.ApartamentsRepository;
import ru.aGreen.rentalofrealestate.repositories.HomeHeadersRepository;

@Controller
public class MainController {
    private final HomeHeadersRepository homeHeadersRepository;
    private final AboutCompanyReposiroty aboutCompanyReposiroty;
    private final ApartamentsRepository apartamentsRepository;
    private final AdvantageRepository advantageRepository;

    @Autowired
    public MainController(HomeHeadersRepository homeHeadersRepository, AboutCompanyReposiroty aboutCompanyReposiroty, ApartamentsRepository apartamentsRepository, AdvantageRepository advantageRepository) {
        this.homeHeadersRepository = homeHeadersRepository;
        this.aboutCompanyReposiroty = aboutCompanyReposiroty;
        this.apartamentsRepository = apartamentsRepository;
        this.advantageRepository = advantageRepository;
    }

    @GetMapping("/")
    public String greeting(Model model) {
        Iterable<HomeHeaders> homeHeaders = homeHeadersRepository.findAll();
        for (HomeHeaders headers : homeHeaders) {
            model.addAttribute("homeHeaders", headers);
        }
        Iterable<AboutCompany> aboutCompany = aboutCompanyReposiroty.findAll();
        Iterable<Advantage> advantages = advantageRepository.findAll();
        Iterable<Apartaments> apartaments = apartamentsRepository.findAll();
        model.addAttribute("aboutCompany", aboutCompany);
        model.addAttribute("advantages", advantages);
        model.addAttribute("apartaments", apartaments);
        return "index";
    }

}