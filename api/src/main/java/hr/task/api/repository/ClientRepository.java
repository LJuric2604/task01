package hr.task.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.task.api.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

	Optional<Client> findByName(String name);

}
