package hr.task.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.task.api.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

	Optional<Person> findByName(String name);

}
