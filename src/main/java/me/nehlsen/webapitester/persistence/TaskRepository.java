package me.nehlsen.webapitester.persistence;

import me.nehlsen.webapitester.persistence.task.TaskEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface TaskRepository extends ListCrudRepository<TaskEntity, UUID> {
}
