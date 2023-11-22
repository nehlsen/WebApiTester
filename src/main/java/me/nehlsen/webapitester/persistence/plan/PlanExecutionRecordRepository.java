package me.nehlsen.webapitester.persistence.plan;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface PlanExecutionRecordRepository extends ListCrudRepository<PlanExecutionRecordEntity, UUID> {
    Optional<PlanExecutionRecordEntity> findFirstByPlan_UuidOrderByCreatedDesc(UUID uuid);
}
