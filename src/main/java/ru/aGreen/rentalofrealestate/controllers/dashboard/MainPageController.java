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
public class MainPageController {
    private final HomeHeadersRepository homeHeadersRepository;
    private final AdvantageRepository advantageRepository;
    private final AboutCompanyReposiroty aboutCompanyReposiroty;
    private final AttractionsRepository attractionsRepository;

    @Autowired
    public MainPageController(HomeHeadersRepository homeHeadersRepository, AdvantageRepository advantageRepository, AboutCompanyReposiroty aboutCompanyReposiroty, AttractionsRepository attractionsRepository) {
        this.homeHeadersRepository = homeHeadersRepository;
        this.advantageRepository = advantageRepository;
        this.aboutCompanyReposiroty = aboutCompanyReposiroty;
        this.attractionsRepository = attractionsRepository;
    }

    @GetMapping("/dashboard/main")
    public String mainPage(Model model) {
        Iterable<HomeHeaders> homeHeaders = homeHeadersRepository.findAll();
        for (HomeHeaders headers : homeHeaders) {
            model.addAttribute("homeHeaders", headers);
        }
        Iterable<Advantage> advantages = advantageRepository.findAll();
        Iterable<AboutCompany> aboutCompanies = aboutCompanyReposiroty.findAll();
        Iterable<Attractions> attractions = attractionsRepository.findAll();
        model.addAttribute("title", "Разделы главной страницы");
        model.addAttribute("descriptions", "На данной странице вы можете просматривать и изменять заголовки, подзаголовки и прочую текстовую информацию, расположенную на главной странице сайта");
        model.addAttribute("advantages", advantages);
        model.addAttribute("aboutCompanies", aboutCompanies);
        model.addAttribute("attractions", attractions);
        return "dashboard/dashboard-main";
    }


    @PostMapping("/dashboard/main/begining/{id}/save")
    public String beginingSave(@PathVariable(value = "id") Long id, @RequestParam String title, @RequestParam String subtitle, @RequestParam String descriptions, @RequestParam String rules, Model model) {
        HomeHeaders homeHeaders = homeHeadersRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        homeHeaders.setTitle(title);
        homeHeaders.setSubtitle(subtitle);
        homeHeaders.setDescriptions(descriptions);
        homeHeaders.setRules(rules);
        homeHeadersRepository.save(homeHeaders);
        return "redirect:/dashboard/main";
    }


    @PostMapping("/dashboard/main/rent/{id}/save")
    public String rentSave(@PathVariable(value = "id") Long id, @RequestParam String portfolioSubtitle, @RequestParam String portfolioDescriptions, Model model) {
        HomeHeaders homeHeaders = homeHeadersRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        homeHeaders.setPortfolioSubtitle(portfolioSubtitle);
        homeHeaders.setPortfolioDescriptions(portfolioDescriptions);
        homeHeadersRepository.save(homeHeaders);
        return "redirect:/dashboard/main";
    }


    @GetMapping("/dashboard/main/about/add")
    public String getAboutAdd(Model model) {
        model.addAttribute("title", "Добавить блок");
        model.addAttribute("descriptions", "На данной странице вы можете добавить блок с заголовком, описанием и изображением");
        return "/dashboard/dashboard-about-add";
    }
    @PostMapping("/dashboard/main/about/add")
    public String setAboutAdd(@RequestParam String title, @RequestParam String description, Model model) {
        AboutCompany aboutCompany = new AboutCompany();
        aboutCompany.setTitle(title);
        aboutCompany.setDescriptions(description);
        aboutCompany.setImage(Images.getImage());
        aboutCompanyReposiroty.save(aboutCompany);
        Images.setImage("");
        return "redirect:/dashboard/main";
    }
    @GetMapping("/dashboard/main/about/{id}")
    public String about(@PathVariable(value = "id") Long id, Model model) {
        AboutCompany aboutCompany = aboutCompanyReposiroty.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        model.addAttribute("aboutCompany", aboutCompany);
        model.addAttribute("title", aboutCompany.getTitle());
        model.addAttribute("descriptions", "На данной странице вы можете изменить заголовок преимущества и изображение над ним");
        return "/dashboard/dashboard-about";
    }
    @PostMapping("/dashboard/main/about/{id}/save")
    public String aboutSave(@PathVariable(value = "id") Long id, @RequestParam String title, @RequestParam String descriptions, Model model) {
        AboutCompany aboutCompany = aboutCompanyReposiroty.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        aboutCompany.setTitle(title);
        aboutCompany.setDescriptions(descriptions);
        if (!Images.getImage().equals("")) {
            aboutCompany.setImage(Images.getImage());
        }
        aboutCompanyReposiroty.save(aboutCompany);
        Images.setImage("");
        return "redirect:/dashboard/main";
    }
    @PostMapping("/dashboard/main/about/{id}/remove")
    public String aboutRemove(@PathVariable(value = "id") Long id, Model model) {
        AboutCompany aboutCompany = aboutCompanyReposiroty.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        aboutCompanyReposiroty.delete(aboutCompany);
        return "redirect:/dashboard/main";
    }
    @PostMapping("/dashboard/main/about/load")
    public String aboutLoad(@RequestBody String url, Model model) {
        System.out.println(url);
        Images.setImage(url);
        return "redirect:/dashboard/main";
    }


    @GetMapping("/dashboard/main/advantage/{id}")
    public String advantage(@PathVariable(value = "id") Long id, Model model) {
        Advantage advantage = advantageRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        model.addAttribute("advantage", advantage);
        model.addAttribute("title", advantage.getTitle());
        model.addAttribute("descriptions", "На данной странице вы можете изменить заголовок преимущества и изображение над ним");
        return "/dashboard/dashboard-advantage";
    }
    @PostMapping("/dashboard/main/advantage/{id}/save")
    public String advantageSave(@PathVariable(value = "id") Long id, @RequestParam String title, Model model) {
        Advantage advantage = advantageRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        advantage.setTitle(title);
        if (!Images.getImage().equals("")) {
            advantage.setImage(Images.getImage());
        }
        advantageRepository.save(advantage);
        Images.setImage("");
        return "redirect:/dashboard/main";
    }
    @PostMapping("/dashboard/main/advantage/{id}/remove")
    public String districtRemove(@PathVariable(value = "id") Long id, Model model) {
        Advantage advantage = advantageRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        advantageRepository.delete(advantage);
        return "redirect:/dashboard/main";
    }
    @PostMapping("/dashboard/main/advantage/load")
    public String apartamentsLoad(@RequestBody String url, Model model) {
        System.out.println(url);
        Images.setImage(url);
        return "redirect:/dashboard/main";
    }


    @GetMapping("/dashboard/main/attractions/add")
    public String getAttractionsAdd(Model model) {
        model.addAttribute("title", "Добавить блок");
        model.addAttribute("descriptions", "На данной странице вы можете добавить блок с заголовком, описанием и изображением");
        return "/dashboard/dashboard-attractions-add";
    }
    @PostMapping("/dashboard/main/attractions/add")
    public String setAttractionsAdd(@RequestParam String title, @RequestParam String description, Model model) {
        Attractions attractions = new Attractions();
        attractions.setTitle(title);
        attractions.setDescriptions(description);
        attractions.setImage(Images.getImage());
        attractionsRepository.save(attractions);
        Images.setImage("");
        return "redirect:/dashboard/main";
    }
    @GetMapping("/dashboard/main/attractions/{id}")
    public String attractions(@PathVariable(value = "id") Long id, Model model) {
        Attractions attractions = attractionsRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        model.addAttribute("attractions", attractions);
        model.addAttribute("title", attractions.getTitle());
        model.addAttribute("descriptions", "На данной странице вы можете изменить заголовок, описание достопримечательности и изображение над ним");
        return "/dashboard/dashboard-attractions";
    }
    @PostMapping("/dashboard/main/attractions/{id}/save")
    public String attractionssSave(@PathVariable(value = "id") Long id, @RequestParam String title, @RequestParam String descriptions, Model model) {
        Attractions attractions = attractionsRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        attractions.setTitle(title);
        attractions.setDescriptions(descriptions);
        if (!Images.getImage().equals("")) {
            attractions.setImage(Images.getImage());
        }
        attractionsRepository.save(attractions);
        Images.setImage("");
        return "redirect:/dashboard/main";
    }
    @PostMapping("/dashboard/main/attractions/{id}/remove")
    public String attractionsRemove(@PathVariable(value = "id") Long id, Model model) {
        Attractions attractions = attractionsRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        attractionsRepository.delete(attractions);
        return "redirect:/dashboard/main";
    }
    @PostMapping("/dashboard/main/attractions/load")
    public String attractionsLoad(@RequestBody String url, Model model) {
        System.out.println(url);
        Images.setImage(url);
        return "redirect:/dashboard/main";
    }


    @PostMapping("/dashboard/main/contact/{id}/save")
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
        return "redirect:/dashboard/main";
    }

}
