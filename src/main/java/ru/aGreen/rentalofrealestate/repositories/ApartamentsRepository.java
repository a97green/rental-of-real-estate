package ru.aGreen.rentalofrealestate.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.aGreen.rentalofrealestate.models.Apartaments;

public interface ApartamentsRepository extends CrudRepository<Apartaments, Long> {
}
