package ru.aGreen.rentalofrealestate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.aGreen.rentalofrealestate.models.*;
import ru.aGreen.rentalofrealestate.repositories.*;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class DashboardController {
    private final ApartamentsRepository apartamentsRepository;
    private final DistrictRepository districtRepository;
    private final HousingClassRepository housingClassRepository;
    private final HousingTypeRepository housingTypeRepository;

    @Autowired
    public DashboardController(ApartamentsRepository apartamentsRepository, DistrictRepository districtRepository, HousingClassRepository housingClassRepository, HousingTypeRepository housingTypeRepository) {
        this.apartamentsRepository = apartamentsRepository;
        this.districtRepository = districtRepository;
        this.housingClassRepository = housingClassRepository;
        this.housingTypeRepository = housingTypeRepository;
    }

    @GetMapping("/dashboard/apartaments")
    public String dashboard(Model model) {
        Iterable<Apartaments> apartaments = apartamentsRepository.findAll();
        model.addAttribute("title", "Список квартир");
        model.addAttribute("apartaments", apartaments);
        return "dashboard/dashboard-apartaments";
    }
    @GetMapping("/dashboard/apartaments/{id}")
    public String apartamentsDetails(@PathVariable(value = "id") Long id, Model model) {
        Apartaments apartaments = apartamentsRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        Iterable<District> districts = districtRepository.findAll();
        Iterable<HousingClass> housingClasses = housingClassRepository.findAll();
        Iterable<HousingType> housingTypes = housingTypeRepository.findAll();
        List<String> strings = Images.disband(apartaments.getPhoto());
        model.addAttribute("title", "Список квартир - Подробнее");
        model.addAttribute("apartaments", apartaments);
        model.addAttribute("districts", districts);
        model.addAttribute("housingClasses", housingClasses);
        model.addAttribute("housingTypes", housingTypes);
        model.addAttribute("strings", strings);
        return "dashboard/dashboard-apartaments-details";
    }
    @PostMapping("/dashboard/apartaments/{id}/save")
    public String apartamentsSave(@PathVariable(value = "id") Long id, @RequestParam String title, @RequestParam String address, @RequestParam String description, @RequestParam Long district, @RequestParam Long housingType, @RequestParam Long housingClass, @RequestParam String bathroom, @RequestParam int floor, @RequestParam int bedsNumber, @RequestParam double totalArea, @RequestParam double price, @RequestParam String parking, Model model) {
        District district1 = districtRepository.findById(district).orElseThrow(() -> new NoSuchElementException(""));
        HousingType housingType1 = housingTypeRepository.findById(housingType).orElseThrow(() -> new NoSuchElementException(""));
        HousingClass housingClass1 = housingClassRepository.findById(housingClass).orElseThrow(() -> new NoSuchElementException(""));
        Apartaments apartaments = apartamentsRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        apartaments.setTitle(title);
        apartaments.setAddress(address);
        apartaments.setDescription(description);
        apartaments.setDistrict(district1);
        apartaments.setHousingType(housingType1);
        apartaments.setHousingClass(housingClass1);
        apartaments.setBathroom(bathroom);
        apartaments.setFloor(floor);
        apartaments.setBedsNumber(bedsNumber);
        apartaments.setTotalArea(totalArea);
        apartaments.setPrice(price);
        apartaments.setParking(parking);
        apartamentsRepository.save(apartaments);
        return "redirect:/dashboard/apartaments/{id}";
    }
    @PostMapping("/dashboard/apartaments/{id}/remove")
    public String apartamentsRemove(@PathVariable(value = "id") Long id, Model model) {
        Apartaments apartaments = apartamentsRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        apartamentsRepository.delete(apartaments);
        return "redirect:/dashboard/apartaments";
    }

    @GetMapping("/dashboard/apartaments/add")
    public String apartaments(Model model) {
        Iterable<District> districts = districtRepository.findAll();
        Iterable<HousingClass> housingClasses = housingClassRepository.findAll();
        Iterable<HousingType> housingTypes = housingTypeRepository.findAll();
        model.addAttribute("title", "Добавить квартиру");
        model.addAttribute("districts", districts);
        model.addAttribute("housingClasses", housingClasses);
        model.addAttribute("housingTypes", housingTypes);
        return "dashboard/dashboard-apartaments-add";
    }
    @PostMapping("/dashboard/apartaments/add")
    public String apartamentsAdd(@RequestParam String title, @RequestParam String address, @RequestParam String description, @RequestParam Long district, @RequestParam Long housingType, @RequestParam Long housingClass, @RequestParam String bathroom, @RequestParam int floor, @RequestParam int bedsNumber, @RequestParam double totalArea, @RequestParam double price, @RequestParam String parking, Model model) {
        District district1 = districtRepository.findById(district).orElseThrow(() -> new NoSuchElementException(""));
        HousingType housingType1 = housingTypeRepository.findById(housingType).orElseThrow(() -> new NoSuchElementException(""));
        HousingClass housingClass1 = housingClassRepository.findById(housingClass).orElseThrow(() -> new NoSuchElementException(""));
        String photo = Images.generate();
        Apartaments apartaments = new Apartaments(title, price, totalArea, floor, bedsNumber, parking, description, address, bathroom, photo, district1, housingType1, housingClass1);
        apartamentsRepository.save(apartaments);
        Images.setStrings(new ArrayList<>());
        return "redirect:/dashboard/apartaments/add";
    }
    @PostMapping("/dashboard/apartaments/load")
    public String loadImg(@RequestBody String url, Model model) {
        Images.getStrings().add(url);
        return "redirect:/dashboard/apartaments/add";
    }

    @GetMapping("/dashboard/type/add")
    public String housingType(Model model) {
        Iterable<Apartaments> apartaments = apartamentsRepository.findAll();
        Iterable<HousingType> housingTypes = housingTypeRepository.findAll();
        model.addAttribute("title", "Добавить тип жилья");
        model.addAttribute("housingTypes", housingTypes);
        model.addAttribute("apartaments", apartaments);
        return "dashboard/dashboard-type-add";
    }
    @PostMapping("/dashboard/type/add")
    public String housingTypeAdd(@RequestParam String title, Model model) {
        if (!title.equals("")) {
            HousingType housingType = new HousingType(title);
            housingTypeRepository.save(housingType);
        }
        return "redirect:/dashboard/type/add";
    }
    @PostMapping("/dashboard/type/{id}/remove")
    public String housingTypeRemove(@PathVariable(value = "id") Long id, Model model) {
        HousingType housingType = housingTypeRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        Iterable<Apartaments> apartaments = apartamentsRepository.findAll();
        for (Apartaments apartaments1 : apartaments) {
            if (apartaments1.getHousingType().equals(housingType)) {
                return "redirect:/dashboard/type/add";
            }
        }
        housingTypeRepository.delete(housingType);
        return "redirect:/dashboard/type/add";
    }

    @GetMapping("/dashboard/class/add")
    public String housingClass(Model model) {
        Iterable<Apartaments> apartaments = apartamentsRepository.findAll();
        Iterable<ru.aGreen.rentalofrealestate.models.HousingClass> housingClasses = housingClassRepository.findAll();
        model.addAttribute("title", "Добавить класс жилья");
        model.addAttribute("housingClasses", housingClasses);
        model.addAttribute("apartaments", apartaments);
        return "dashboard/dashboard-class-add";
    }
    @PostMapping("/dashboard/class/add")
    public String housingClassAdd(@RequestParam String title, Model model) {
        if (!title.equals("")) {
            ru.aGreen.rentalofrealestate.models.HousingClass housingClass = new ru.aGreen.rentalofrealestate.models.HousingClass(title);
            housingClassRepository.save(housingClass);
        }
        return "redirect:/dashboard/class/add";
    }
    @PostMapping("/dashboard/class/{id}/remove")
    public String housingClassRemove(@PathVariable(value = "id") Long id, Model model) {
        ru.aGreen.rentalofrealestate.models.HousingClass housingClass = housingClassRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        Iterable<Apartaments> apartaments = apartamentsRepository.findAll();
        for (Apartaments apartaments1 : apartaments) {
            if (apartaments1.getHousingClass().equals(housingClass)) {
                return "redirect:/dashboard/class/add";
            }
        }
        housingClassRepository.delete(housingClass);
        return "redirect:/dashboard/class/add";
    }

    @GetMapping("/dashboard/district/add")
    public String district(Model model) {
        Iterable<Apartaments> apartaments = apartamentsRepository.findAll();
        Iterable<District> districts = districtRepository.findAll();
        model.addAttribute("title", "Добавить район");
        model.addAttribute("districts", districts);
        model.addAttribute("apartaments", apartaments);
        return "dashboard/dashboard-district-add";
    }
    @PostMapping("/dashboard/district/add")
    public String districtAdd(@RequestParam String title, Model model) {
        if (!title.equals("")) {
            District district = new District(title);
            districtRepository.save(district);
        }
        return "redirect:/dashboard/district/add";
    }
    @PostMapping("/dashboard/district/{id}/remove")
    public String districtRemove(@PathVariable(value = "id") Long id, Model model) {
        District district = districtRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        Iterable<Apartaments> apartaments = apartamentsRepository.findAll();
        for (Apartaments apartaments1 : apartaments) {
            if (apartaments1.getDistrict().equals(district)) {
                return "redirect:/dashboard/district/add";
            }
        }
        districtRepository.delete(district);
        return "redirect:/dashboard/district/add";
    }
}
