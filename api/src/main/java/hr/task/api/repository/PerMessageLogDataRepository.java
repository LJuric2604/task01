package hr.task.api.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import hr.task.api.entity.PerMessageLogData;

public interface PerMessageLogDataRepository extends ElasticsearchRepository<PerMessageLogData, String> {

}
