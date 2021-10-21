package ru.aGreen.rentalofrealestate.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.aGreen.rentalofrealestate.models.Advantage;

import java.util.List;

public interface AdvantageRepository extends CrudRepository<Advantage, Long> {
    List<Advantage> findAll();
}
