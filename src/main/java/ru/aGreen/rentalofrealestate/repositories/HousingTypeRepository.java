package ru.aGreen.rentalofrealestate.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.aGreen.rentalofrealestate.models.HousingType;

public interface HousingTypeRepository extends CrudRepository<HousingType, Long> {
}
