package hr.task.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.task.api.entity.PersonChannel;

public interface PersonChannelRepository extends JpaRepository<PersonChannel, Long> {

}
