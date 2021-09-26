package ru.aGreen.rentalofrealestate.controllers.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.aGreen.rentalofrealestate.models.*;
import ru.aGreen.rentalofrealestate.repositories.ApartamentsRepository;
import ru.aGreen.rentalofrealestate.repositories.DistrictRepository;
import ru.aGreen.rentalofrealestate.repositories.HousingClassRepository;
import ru.aGreen.rentalofrealestate.repositories.HousingTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class ApartamentsController {
    private final ApartamentsRepository apartamentsRepository;
    private final DistrictRepository districtRepository;
    private final HousingClassRepository housingClassRepository;
    private final HousingTypeRepository housingTypeRepository;

    @Autowired
    public ApartamentsController(ApartamentsRepository apartamentsRepository, DistrictRepository districtRepository, HousingClassRepository housingClassRepository, HousingTypeRepository housingTypeRepository) {
        this.apartamentsRepository = apartamentsRepository;
        this.districtRepository = districtRepository;
        this.housingClassRepository = housingClassRepository;
        this.housingTypeRepository = housingTypeRepository;
    }

    @GetMapping("/dashboard/apartaments")
    public String apartaments(Model model) {
        Iterable<Apartaments> apartaments = apartamentsRepository.findAll();
        model.addAttribute("title", "Список квартир");
        model.addAttribute("descriptions", "Это список всех добавленых квартир");
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
        model.addAttribute("title", apartaments.getTitle());
        model.addAttribute("descriptions", "На данной странице вы можете порсматривать подробную информацию о квартире, а также изменять её");
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
    public String getApartamentsAdd(Model model) {
        Iterable<District> districts = districtRepository.findAll();
        Iterable<HousingClass> housingClasses = housingClassRepository.findAll();
        Iterable<HousingType> housingTypes = housingTypeRepository.findAll();
        model.addAttribute("title", "Добавить квартиру");
        model.addAttribute("descriptions", "На данной странице вы можете добавлять новые квартиры");
        model.addAttribute("districts", districts);
        model.addAttribute("housingClasses", housingClasses);
        model.addAttribute("housingTypes", housingTypes);
        return "dashboard/dashboard-apartaments-add";
    }
    @PostMapping("/dashboard/apartaments/add")
    public String setApartamentsAdd(@RequestParam String title, @RequestParam String address, @RequestParam String description, @RequestParam Long district, @RequestParam Long housingType, @RequestParam Long housingClass, @RequestParam String bathroom, @RequestParam int floor, @RequestParam int bedsNumber, @RequestParam double totalArea, @RequestParam double price, @RequestParam String parking, Model model) {
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
    public String apartamentsLoad(@RequestBody String url, Model model) {
        System.out.println(url);
        Images.getStrings().add(url);
        model.addAttribute("success", "Готово");
        return "redirect:/dashboard/apartaments/add";
    }
}
