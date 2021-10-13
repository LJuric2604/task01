package hr.task.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.task.api.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
