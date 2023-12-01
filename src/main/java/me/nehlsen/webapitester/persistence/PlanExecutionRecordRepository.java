package me.nehlsen.webapitester.persistence;

import me.nehlsen.webapitester.persistence.record.PlanExecutionRecordEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface PlanExecutionRecordRepository extends ListCrudRepository<PlanExecutionRecordEntity, UUID> {
    Optional<PlanExecutionRecordEntity> findFirstByPlan_UuidOrderByCreatedDesc(UUID uuid);

    List<PlanExecutionRecordEntity> findByPlan_UuidOrderByCreatedDesc(UUID uuid, Pageable pageable);
}
