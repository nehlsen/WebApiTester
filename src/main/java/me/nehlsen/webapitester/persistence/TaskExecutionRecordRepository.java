package me.nehlsen.webapitester.persistence;

import me.nehlsen.webapitester.persistence.record.TaskExecutionRecordEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

interface TaskExecutionRecordRepository extends ListCrudRepository<TaskExecutionRecordEntity, UUID> {
}
