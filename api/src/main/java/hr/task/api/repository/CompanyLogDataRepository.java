package hr.task.api.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import hr.task.api.entity.CompanyLogData;

public interface CompanyLogDataRepository extends ElasticsearchRepository<CompanyLogData, String> {

}
