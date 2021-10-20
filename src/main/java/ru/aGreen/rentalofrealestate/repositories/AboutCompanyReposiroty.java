package ru.aGreen.rentalofrealestate.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.aGreen.rentalofrealestate.models.AboutCompany;

import java.util.List;

public interface AboutCompanyReposiroty extends CrudRepository<AboutCompany, Long> {
    List<AboutCompany> findAll();
}
