package ru.aGreen.rentalofrealestate.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.aGreen.rentalofrealestate.models.HousingClass;

public interface HousingClassRepository extends CrudRepository<HousingClass, Long> {
}
