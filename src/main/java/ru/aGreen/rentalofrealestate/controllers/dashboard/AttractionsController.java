package ru.aGreen.rentalofrealestate.controllers.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.aGreen.rentalofrealestate.models.Attractions;
import ru.aGreen.rentalofrealestate.models.Images;
import ru.aGreen.rentalofrealestate.repositories.AttractionsRepository;

import java.util.NoSuchElementException;

@Controller
public class AttractionsController {

    private final AttractionsRepository attractionsRepository;

    @Autowired
    public AttractionsController(AttractionsRepository attractionsRepository) {
        this.attractionsRepository = attractionsRepository;
    }

    @GetMapping("/dashboard/attractions")
    public String getAttractions(Model model) {
        model.addAttribute("title", "Добавить блок");
        model.addAttribute("descriptions", "На данной странице вы можете добавить блок с заголовком, описанием и изображением");
        return "dashboard/attractions";
    }

    @PostMapping("/dashboard/attractions/add")
    public String addAttractions(@RequestParam String title, @RequestParam String description, Model model) {
        Attractions attractions = new Attractions();
        attractions.setTitle(title);
        attractions.setDescriptions(description);
        attractions.setImage(Images.getImage());
        attractionsRepository.save(attractions);
        Images.setImage("");
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard/attractions/edit/{id}")
    public String attractions(@PathVariable(value = "id") Long id, Model model) {
        Attractions attractions = attractionsRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        model.addAttribute("attractions", attractions);
        model.addAttribute("title", attractions.getTitle());
        model.addAttribute("descriptions", "На данной странице вы можете изменить заголовок, описание достопримечательности и изображение над ним");
        return "dashboard/attractionsEdit";
    }

    @PostMapping("/dashboard/attractions/save/{id}")
    public String attractionssSave(@PathVariable(value = "id") Long id, @RequestParam String title, @RequestParam String descriptions, Model model) {
        Attractions attractions = attractionsRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        attractions.setTitle(title);
        attractions.setDescriptions(descriptions);
        if (!Images.getImage().equals("")) {
            attractions.setImage(Images.getImage());
        }
        attractionsRepository.save(attractions);
        Images.setImage("");
        return "redirect:/dashboard";
    }

    @PostMapping("/dashboard/attractions/remove/{id}")
    public String attractionsRemove(@PathVariable(value = "id") Long id, Model model) {
        Attractions attractions = attractionsRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        attractionsRepository.delete(attractions);
        return "redirect:/dashboard";
    }

    @PostMapping("/dashboard/attractions/load")
    public String attractionsLoad(@RequestBody String url, Model model) {
        System.out.println(url);
        Images.setImage(url);
        return "redirect:/dashboard";
    }
}
