package ru.aGreen.rentalofrealestate.controllers.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.aGreen.rentalofrealestate.models.Review;
import ru.aGreen.rentalofrealestate.repositories.ReviewRepository;

import java.util.NoSuchElementException;

@Controller
public class ReviewController {

    private final ReviewRepository reviewRepository;

    public ReviewController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @GetMapping("/dashboard/review")
    public String getReview(Model model) {
        Iterable<Review> reviews = reviewRepository.findAll();
        model.addAttribute("title", "Список отзывов");
        model.addAttribute("descriptions", "На данной странице вы можете просмотреть отзывы");
        model.addAttribute("reviews", reviews);
        return "dashboard/review";
    }

    @GetMapping("/dashboard/review/{id}")
    public String infoReview(@PathVariable(value = "id") Long id, Model model) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));

        model.addAttribute("title", "Редактировать отзыв");
        model.addAttribute("descriptions", "На данной странице вы можете отредактировать отзыв");
        model.addAttribute("review", review);

        return "dashboard/reviewEdit";
    }

    @GetMapping("/dashboard/review/add")
    public String addReview(Model model) {
        model.addAttribute("title", "Добавить отзыв");
        model.addAttribute("descriptions", "На данной странице вы можете добавить отзыв");
        return "dashboard/reviewAdd";
    }

    @PostMapping("/dashboard/review/add")
    public String setReview(@RequestParam String name, @RequestParam String description, Model model) {
        Review review = new Review();
        review.setName(name);
        review.setDiscription(description);
        reviewRepository.save(review);
        return "redirect:/dashboard/review";
    }

    @GetMapping("/dashboard/review/edit/{id}")
    public String editReview(@PathVariable(value = "id") Long id, Model model) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        model.addAttribute("review", review);
        model.addAttribute("title", review.getName());
        model.addAttribute("descriptions", "На данной странице вы можете изменить отзыв");
        return "dashboard/reviewEdit";
    }

    @PostMapping("/dashboard/review/save/{id}")
    public String aboutSave(@PathVariable(value = "id") Long id, @RequestParam String name, @RequestParam String descriptions, Model model) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        review.setName(name);
        review.setDiscription(descriptions);
        reviewRepository.save(review);
        return "redirect:/dashboard/review";
    }

    @PostMapping("/dashboard/review/remove/{id}")
    public String aboutRemove(@PathVariable(value = "id") Long id, Model model) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        reviewRepository.delete(review);
        return "redirect:/dashboard/review";
    }
}
