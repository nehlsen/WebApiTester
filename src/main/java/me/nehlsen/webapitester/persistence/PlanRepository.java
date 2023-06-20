package me.nehlsen.webapitester.persistence;

import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface PlanRepository extends ListCrudRepository<PlanEntity, UUID> {
}
