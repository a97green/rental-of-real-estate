package ru.aGreen.rentalofrealestate.controllers.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.aGreen.rentalofrealestate.models.Advantage;
import ru.aGreen.rentalofrealestate.models.Images;
import ru.aGreen.rentalofrealestate.repositories.AdvantageRepository;

import java.util.NoSuchElementException;

@Controller
public class AdvantageController {

    private final AdvantageRepository advantageRepository;

    @Autowired
    public AdvantageController(AdvantageRepository advantageRepository) {
        this.advantageRepository = advantageRepository;
    }

    @GetMapping("/dashboard/advantage/edit/{id}")
    public String advantage(@PathVariable(value = "id") Long id, Model model) {
        Advantage advantage = advantageRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        model.addAttribute("advantage", advantage);
        model.addAttribute("title", advantage.getTitle());
        model.addAttribute("descriptions", "На данной странице вы можете изменить заголовок преимущества и изображение над ним");
        return "dashboard/advantageEdit";
    }

    @PostMapping("/dashboard/advantage/save/{id}")
    public String saveAdvantage(@PathVariable(value = "id") Long id, @RequestParam String title, Model model) {
        Advantage advantage = advantageRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        advantage.setTitle(title);
        if (!Images.getImage().equals("")) {
            advantage.setImage(Images.getImage());
        }
        advantageRepository.save(advantage);
        Images.setImage("");
        return "redirect:/dashboard";
    }

    @PostMapping("/dashboard/advantage/remove/{id}")
    public String removeAdvantage(@PathVariable(value = "id") Long id, Model model) {
        Advantage advantage = advantageRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        advantageRepository.delete(advantage);
        return "redirect:/dashboard";
    }

    @PostMapping("/dashboard/advantage/load")
    public String apartamentsLoad(@RequestBody String url, Model model) {
        System.out.println(url);
        Images.setImage(url);
        return "redirect:/dashboard";
    }
}
