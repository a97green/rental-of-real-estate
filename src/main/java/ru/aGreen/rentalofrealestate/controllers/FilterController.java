package ru.aGreen.rentalofrealestate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.aGreen.rentalofrealestate.models.Apartaments;
import ru.aGreen.rentalofrealestate.models.HomeHeaders;
import ru.aGreen.rentalofrealestate.repositories.ApartamentsRepository;
import ru.aGreen.rentalofrealestate.repositories.HomeHeadersRepository;

@Controller
public class FilterController {
    private final HomeHeadersRepository homeHeadersRepository;
    private final ApartamentsRepository apartamentsRepository;

    @Autowired
    public FilterController(HomeHeadersRepository homeHeadersRepository, ApartamentsRepository apartamentsRepository) {
        this.homeHeadersRepository = homeHeadersRepository;
        this.apartamentsRepository = apartamentsRepository;
    }

    @GetMapping("/apartments")
    public String apartaments(Model model) {
        Iterable<HomeHeaders> homeHeaders = homeHeadersRepository.findAll();
        for (HomeHeaders headers : homeHeaders) {
            model.addAttribute("homeHeaders", headers);
        }
        Iterable<Apartaments> apartaments = apartamentsRepository.findAll();
        model.addAttribute("apartaments", apartaments);
        return "apartments";
    }
}
