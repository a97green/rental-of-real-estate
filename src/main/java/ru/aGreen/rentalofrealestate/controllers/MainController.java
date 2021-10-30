package ru.aGreen.rentalofrealestate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.aGreen.rentalofrealestate.models.*;
import ru.aGreen.rentalofrealestate.repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

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
        List<AboutCompany> aboutCompany = aboutCompanyReposiroty.findAll();
        List<Advantage> advantages = advantageRepository.findAll();
        Iterable<Apartaments> apartaments = apartamentsRepository.findAll();
        Iterable<Attractions> attractions = attractionsRepository.findAll();
        model.addAttribute("aboutCompany", aboutCompany);
        model.addAttribute("advantages", advantages);
        model.addAttribute("apartaments", apartaments);
        model.addAttribute("attractions", attractions);
        return "index-parallax";
    }

    @GetMapping("/apartment/{id}")
    public String apartment(@PathVariable(value = "id") Long id, Model model) {
        Iterable<HomeHeaders> homeHeaders = homeHeadersRepository.findAll();
        for (HomeHeaders headers : homeHeaders) {
            model.addAttribute("homeHeaders", headers);
        }
        Iterable<Apartaments> apartaments = apartamentsRepository.findAll();
        Apartaments apartament = apartamentsRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        List<String> strings = Images.disband(apartament.getPhoto());
        List<Integer> integers = new ArrayList<>();
        AtomicInteger i = new AtomicInteger();
        strings.forEach(e -> integers.add(i.getAndIncrement()));
        model.addAttribute("apartament", apartament);
        model.addAttribute("apartaments", apartaments);
        model.addAttribute("strings", strings);
        model.addAttribute("integers", integers);
        return "index-form";
    }

    @GetMapping("/qqw")
    public String greetinыыывg(Model model) {
        Iterable<HomeHeaders> homeHeaders = homeHeadersRepository.findAll();
        for (HomeHeaders headers : homeHeaders) {
            model.addAttribute("homeHeaders", headers);
        }
        List<AboutCompany> aboutCompany = aboutCompanyReposiroty.findAll();
        List<Advantage> advantages = advantageRepository.findAll();
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