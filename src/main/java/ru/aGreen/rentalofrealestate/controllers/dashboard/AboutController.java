package ru.aGreen.rentalofrealestate.controllers.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.aGreen.rentalofrealestate.models.AboutCompany;
import ru.aGreen.rentalofrealestate.models.Images;
import ru.aGreen.rentalofrealestate.repositories.AboutCompanyReposiroty;

import java.util.NoSuchElementException;

@Controller
public class AboutController {

    private final AboutCompanyReposiroty aboutCompanyReposiroty;

    @Autowired
    public AboutController(AboutCompanyReposiroty aboutCompanyReposiroty) {
        this.aboutCompanyReposiroty = aboutCompanyReposiroty;
    }

    @GetMapping("/dashboard/about")
    public String getAbout(Model model) {
        model.addAttribute("title", "Добавить блок");
        model.addAttribute("descriptions", "На данной странице вы можете добавить блок с заголовком, описанием и изображением");
        return "dashboard/about";
    }

    @PostMapping("/dashboard/about/add")
    public String addAbout(@RequestParam String title, @RequestParam String description, Model model) {
        AboutCompany aboutCompany = new AboutCompany();
        aboutCompany.setTitle(title);
        aboutCompany.setDescriptions(description);
        aboutCompany.setImage(Images.getImage());
        aboutCompanyReposiroty.save(aboutCompany);
        Images.setImage("");
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard/about/edit/{id}")
    public String editAbout(@PathVariable(value = "id") Long id, Model model) {
        AboutCompany aboutCompany = aboutCompanyReposiroty.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        model.addAttribute("aboutCompany", aboutCompany);
        model.addAttribute("title", aboutCompany.getTitle());
        model.addAttribute("descriptions", "На данной странице вы можете изменить заголовок преимущества и изображение над ним");
        return "dashboard/aboutEdit";
    }

    @PostMapping("/dashboard/about/save/{id}")
    public String aboutSave(@PathVariable(value = "id") Long id, @RequestParam String title, @RequestParam String descriptions, Model model) {
        AboutCompany aboutCompany = aboutCompanyReposiroty.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        aboutCompany.setTitle(title);
        aboutCompany.setDescriptions(descriptions);
        aboutCompanyReposiroty.save(aboutCompany);
        return "redirect:/dashboard";
    }

    @PostMapping("/dashboard/about/remove/{id}")
    public String aboutRemove(@PathVariable(value = "id") Long id, Model model) {
        AboutCompany aboutCompany = aboutCompanyReposiroty.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        aboutCompanyReposiroty.delete(aboutCompany);
        return "redirect:/dashboard";
    }
}
