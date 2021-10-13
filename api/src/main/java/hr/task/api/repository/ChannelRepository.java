package hr.task.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.task.api.entity.Channel;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

}
