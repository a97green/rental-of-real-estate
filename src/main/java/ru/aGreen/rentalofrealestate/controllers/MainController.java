package ru.aGreen.rentalofrealestate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.aGreen.rentalofrealestate.models.*;
import ru.aGreen.rentalofrealestate.repositories.*;

import java.util.List;

@Controller
public class MainController {
    private final HomeHeadersRepository homeHeadersRepository;
    private final AboutCompanyReposiroty aboutCompanyReposiroty;
    private final ApartamentsRepository apartamentsRepository;
    private final AdvantageRepository advantageRepository;
    private final AttractionsRepository attractionsRepository;

    @Autowired
    public MainController(HomeHeadersRepository homeHeadersRepository, AboutCompanyReposiroty aboutCompanyReposiroty, ApartamentsRepository apartamentsRepository, AdvantageRepository advantageRepository, AttractionsRepository attractionsRepository) {
        this.homeHeadersRepository = homeHeadersRepository;
        this.aboutCompanyReposiroty = aboutCompanyReposiroty;
        this.apartamentsRepository = apartamentsRepository;
        this.advantageRepository = advantageRepository;
        this.attractionsRepository = attractionsRepository;
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
        Iterable<Attractions> attractions = attractionsRepository.findAll();
        model.addAttribute("aboutCompany", aboutCompany);
        model.addAttribute("advantages", advantages);
        model.addAttribute("apartaments", apartaments);
        model.addAttribute("attractions", attractions);
        return "index";
    }

    @GetMapping("/error")
    public String error(Model model) {
        return "error";
    }

}