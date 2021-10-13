package hr.task.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.task.api.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
