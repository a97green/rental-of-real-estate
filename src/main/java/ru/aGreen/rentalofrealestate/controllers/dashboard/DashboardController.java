package ru.aGreen.rentalofrealestate.controllers.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.aGreen.rentalofrealestate.models.*;
import ru.aGreen.rentalofrealestate.repositories.AboutCompanyReposiroty;
import ru.aGreen.rentalofrealestate.repositories.AdvantageRepository;
import ru.aGreen.rentalofrealestate.repositories.AttractionsRepository;
import ru.aGreen.rentalofrealestate.repositories.HomeHeadersRepository;

import java.util.NoSuchElementException;

@Controller
public class DashboardController {

    private final HomeHeadersRepository homeHeadersRepository;
    private final AdvantageRepository advantageRepository;
    private final AboutCompanyReposiroty aboutCompanyReposiroty;
    private final AttractionsRepository attractionsRepository;

    @Autowired
    public DashboardController(HomeHeadersRepository homeHeadersRepository, AdvantageRepository advantageRepository, AboutCompanyReposiroty aboutCompanyReposiroty, AttractionsRepository attractionsRepository) {
        this.homeHeadersRepository = homeHeadersRepository;
        this.advantageRepository = advantageRepository;
        this.aboutCompanyReposiroty = aboutCompanyReposiroty;
        this.attractionsRepository = attractionsRepository;
    }

    @GetMapping("/dashboard")
    public String mainPage(Model model) {
        for (HomeHeaders headers : homeHeadersRepository.findAll()) {
            model.addAttribute("homeHeaders", headers);
        }
        model.addAttribute("title", "Разделы главной страницы");
        model.addAttribute("descriptions", "На данной странице вы можете просматривать и изменять заголовки, подзаголовки и прочую текстовую информацию, расположенную на главной странице сайта");
        model.addAttribute("advantages", advantageRepository.findAll());
        model.addAttribute("aboutCompanies", aboutCompanyReposiroty.findAll());
        model.addAttribute("attractions", attractionsRepository.findAll());
        return "dashboard/dashboard";
    }

    @PostMapping("/dashboard/begining/save/{id}")
    public String beginingSave(@PathVariable(value = "id") Long id, @RequestParam String title, @RequestParam String subtitle, @RequestParam String descriptions, @RequestParam String rules, Model model) {
        HomeHeaders homeHeaders = homeHeadersRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        homeHeaders.setTitle(title);
        homeHeaders.setSubtitle(subtitle);
        homeHeaders.setDescriptions(descriptions);
        homeHeaders.setRules(rules);
        homeHeadersRepository.save(homeHeaders);
        return "redirect:/dashboard";
    }

    @PostMapping("/dashboard/rent/save/{id}")
    public String rentSave(@PathVariable(value = "id") Long id, @RequestParam String portfolioSubtitle, @RequestParam String portfolioDescriptions, Model model) {
        HomeHeaders homeHeaders = homeHeadersRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        homeHeaders.setPortfolioSubtitle(portfolioSubtitle);
        homeHeaders.setPortfolioDescriptions(portfolioDescriptions);
        homeHeadersRepository.save(homeHeaders);
        return "redirect:/dashboard";
    }

    @PostMapping("/dashboard/contact/save/{id}")
    public String contactSave(@PathVariable(value = "id") Long id, @RequestParam String address, @RequestParam String email, @RequestParam String phoneNumber, @RequestParam String whatsApp, @RequestParam String telegram, @RequestParam String viber, @RequestParam String instagram, Model model) {
        HomeHeaders homeHeaders = homeHeadersRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        homeHeaders.setAddress(address);
        homeHeaders.setEmail(email);
        homeHeaders.setPhoneNumber(phoneNumber);
        homeHeaders.setWhasApp(whatsApp);
        homeHeaders.setTelegram(telegram);
        homeHeaders.setViber(viber);
        homeHeaders.setInstagram(instagram);
        homeHeadersRepository.save(homeHeaders);
        return "redirect:/dashboard";
    }

}
