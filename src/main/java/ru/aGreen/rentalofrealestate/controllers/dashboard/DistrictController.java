package ru.aGreen.rentalofrealestate.controllers.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.aGreen.rentalofrealestate.models.Apartaments;
import ru.aGreen.rentalofrealestate.models.District;
import ru.aGreen.rentalofrealestate.repositories.ApartamentsRepository;
import ru.aGreen.rentalofrealestate.repositories.DistrictRepository;

import java.util.NoSuchElementException;

@Controller
public class DistrictController {
    private final ApartamentsRepository apartamentsRepository;
    private final DistrictRepository districtRepository;

    @Autowired
    public DistrictController(ApartamentsRepository apartamentsRepository, DistrictRepository districtRepository) {
        this.apartamentsRepository = apartamentsRepository;
        this.districtRepository = districtRepository;
    }

    @GetMapping("/dashboard/district")
    public String district(Model model) {
        Iterable<Apartaments> apartaments = apartamentsRepository.findAll();
        Iterable<District> districts = districtRepository.findAll();
        model.addAttribute("title", "Список районов");
        model.addAttribute("descriptions", "Это список всех добавленых районов");
        model.addAttribute("districts", districts);
        return "dashboard/dashboard-district";
    }

    @GetMapping("/dashboard/district/add")
    public String getDistrictAdd(Model model) {
        Iterable<Apartaments> apartaments = apartamentsRepository.findAll();
        Iterable<District> districts = districtRepository.findAll();
        model.addAttribute("title", "Добавить район");
        model.addAttribute("descriptions", "На данной странице вы можете добавить новый район");
        model.addAttribute("districts", districts);
        model.addAttribute("apartaments", apartaments);
        return "dashboard/dashboard-district-add";
    }
    @PostMapping("/dashboard/district/add")
    public String setDistrictAdd(@RequestParam String title, Model model) {
        if (!title.equals("")) {
            District district = new District(title);
            districtRepository.save(district);
        }
        return "redirect:/dashboard/district";
    }
    @PostMapping("/dashboard/district/{id}/remove")
    public String districtRemove(@PathVariable(value = "id") Long id, Model model) {
        District district = districtRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        Iterable<Apartaments> apartaments = apartamentsRepository.findAll();
        for (Apartaments apartaments1 : apartaments) {
            if (apartaments1.getDistrict().equals(district)) {
                return "redirect:/dashboard/district";
            }
        }
        districtRepository.delete(district);
        return "redirect:/dashboard/district";
    }
}
