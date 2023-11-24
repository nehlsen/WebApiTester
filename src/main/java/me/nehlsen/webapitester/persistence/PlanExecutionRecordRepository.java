package me.nehlsen.webapitester.persistence;

import me.nehlsen.webapitester.persistence.plan.PlanExecutionRecordEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;
import java.util.UUID;

interface PlanExecutionRecordRepository extends ListCrudRepository<PlanExecutionRecordEntity, UUID> {
    Optional<PlanExecutionRecordEntity> findFirstByPlan_UuidOrderByCreatedDesc(UUID uuid);
}
