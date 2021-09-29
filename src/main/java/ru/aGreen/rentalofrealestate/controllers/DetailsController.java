package ru.aGreen.rentalofrealestate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.aGreen.rentalofrealestate.models.Apartaments;
import ru.aGreen.rentalofrealestate.models.HomeHeaders;
import ru.aGreen.rentalofrealestate.models.Images;
import ru.aGreen.rentalofrealestate.repositories.ApartamentsRepository;
import ru.aGreen.rentalofrealestate.repositories.HomeHeadersRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class DetailsController {
    private final HomeHeadersRepository homeHeadersRepository;
    private final ApartamentsRepository apartamentsRepository;

    @Autowired
    public DetailsController(HomeHeadersRepository homeHeadersRepository, ApartamentsRepository apartamentsRepository) {
        this.homeHeadersRepository = homeHeadersRepository;
        this.apartamentsRepository = apartamentsRepository;
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable(value = "id") Long id, Model model) {
        Iterable<HomeHeaders> homeHeaders = homeHeadersRepository.findAll();
        for (HomeHeaders headers : homeHeaders) {
            model.addAttribute("homeHeaders", headers);
        }
        Apartaments apartaments = apartamentsRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        List<String> strings = Images.disband(apartaments.getPhoto());
        model.addAttribute("apartaments", apartaments);
        model.addAttribute("strings", strings);
        return "apartments-details";
    }
}
