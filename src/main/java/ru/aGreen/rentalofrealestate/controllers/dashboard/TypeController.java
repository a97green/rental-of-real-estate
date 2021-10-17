package ru.aGreen.rentalofrealestate.controllers.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.aGreen.rentalofrealestate.models.Apartaments;
import ru.aGreen.rentalofrealestate.models.HousingType;
import ru.aGreen.rentalofrealestate.repositories.ApartamentsRepository;
import ru.aGreen.rentalofrealestate.repositories.HousingTypeRepository;

import java.util.NoSuchElementException;

@Controller
public class TypeController {
    private final ApartamentsRepository apartamentsRepository;
    private final HousingTypeRepository housingTypeRepository;

    @Autowired
    public TypeController(ApartamentsRepository apartamentsRepository, HousingTypeRepository housingTypeRepository) {
        this.apartamentsRepository = apartamentsRepository;
        this.housingTypeRepository = housingTypeRepository;
    }

    @GetMapping("/dashboard/type")
    public String housingType(Model model) {
        Iterable<HousingType> housingTypes = housingTypeRepository.findAll();
        model.addAttribute("title", "Список типов жилья");
        model.addAttribute("descriptions", "Это список всех добавленых типов жилья");
        model.addAttribute("housingTypes", housingTypes);
        return "dashboard/types";
    }

    @GetMapping("/dashboard/type/add")
    public String getHousingTypeAdd(Model model) {
        Iterable<Apartaments> apartaments = apartamentsRepository.findAll();
        Iterable<HousingType> housingTypes = housingTypeRepository.findAll();
        model.addAttribute("title", "Добавить тип жилья");
        model.addAttribute("descriptions", "На данной странице вы можете добавить новый тип жилья");
        model.addAttribute("housingTypes", housingTypes);
        model.addAttribute("apartaments", apartaments);
        return "dashboard/typeAdd";
    }

    @PostMapping("/dashboard/type/add")
    public String setHousingTypeAdd(@RequestParam String title, Model model) {
        if (!title.equals("")) {
            HousingType housingType = new HousingType(title);
            housingTypeRepository.save(housingType);
        }
        return "redirect:/dashboard/type";
    }

    @PostMapping("/dashboard/type/{id}/remove")
    public String housingTypeRemove(@PathVariable(value = "id") Long id, Model model) {
        HousingType housingType = housingTypeRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        Iterable<Apartaments> apartaments = apartamentsRepository.findAll();
        for (Apartaments apartaments1 : apartaments) {
            if (apartaments1.getHousingType().equals(housingType)) {
                return "redirect:/dashboard/type";
            }
        }
        housingTypeRepository.delete(housingType);
        return "redirect:/dashboard/type";
    }
}
