package ru.aGreen.rentalofrealestate.controllers.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.aGreen.rentalofrealestate.models.Apartaments;
import ru.aGreen.rentalofrealestate.models.HousingClass;
import ru.aGreen.rentalofrealestate.repositories.ApartamentsRepository;
import ru.aGreen.rentalofrealestate.repositories.HousingClassRepository;

import java.util.NoSuchElementException;

@Controller
public class ClassController {
    private final ApartamentsRepository apartamentsRepository;
    private final HousingClassRepository housingClassRepository;

    @Autowired
    public ClassController(ApartamentsRepository apartamentsRepository, HousingClassRepository housingClassRepository) {
        this.apartamentsRepository = apartamentsRepository;
        this.housingClassRepository = housingClassRepository;
    }

    @GetMapping("/dashboard/classes")
    public String housingClass(Model model) {
        Iterable<HousingClass> housingClasses = housingClassRepository.findAll();
        model.addAttribute("title", "Список классов жилья");
        model.addAttribute("descriptions", "Это список всех добавленых классов жилья");
        model.addAttribute("housingClasses", housingClasses);
        return "dashboard/classes";
    }

    @GetMapping("/dashboard/classes/add")
    public String getHousingClassAdd(Model model) {
        Iterable<Apartaments> apartaments = apartamentsRepository.findAll();
        Iterable<ru.aGreen.rentalofrealestate.models.HousingClass> housingClasses = housingClassRepository.findAll();
        model.addAttribute("title", "Добавить класс жилья");
        model.addAttribute("descriptions", "На данной странице вы можете добавить новый класс жилья");
        model.addAttribute("housingClasses", housingClasses);
        model.addAttribute("apartaments", apartaments);
        return "dashboard/classAdd";
    }

    @PostMapping("/dashboard/classes/add")
    public String setHousingClassAdd(@RequestParam String title, Model model) {
        if (!title.equals("")) {
            ru.aGreen.rentalofrealestate.models.HousingClass housingClass = new ru.aGreen.rentalofrealestate.models.HousingClass(title);
            housingClassRepository.save(housingClass);
        }
        return "redirect:/dashboard/classes";
    }

    @PostMapping("/dashboard/classes/{id}/remove")
    public String housingClassRemove(@PathVariable(value = "id") Long id, Model model) {
        ru.aGreen.rentalofrealestate.models.HousingClass housingClass = housingClassRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        Iterable<Apartaments> apartaments = apartamentsRepository.findAll();
        for (Apartaments apartaments1 : apartaments) {
            if (apartaments1.getHousingClass().equals(housingClass)) {
                return "redirect:/dashboard/classes";
            }
        }
        housingClassRepository.delete(housingClass);
        return "redirect:/dashboard/classes";
    }
}
