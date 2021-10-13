package hr.task.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.task.api.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
