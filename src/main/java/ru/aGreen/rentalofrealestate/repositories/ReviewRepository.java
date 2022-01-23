package ru.aGreen.rentalofrealestate.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.aGreen.rentalofrealestate.models.Review;

public interface ReviewRepository extends CrudRepository<Review, Long> {
}
