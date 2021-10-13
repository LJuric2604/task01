package hr.task.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.task.api.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
