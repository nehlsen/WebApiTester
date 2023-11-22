package me.nehlsen.webapitester.persistence;

import me.nehlsen.webapitester.persistence.plan.PlanEntity;
import me.nehlsen.webapitester.persistence.plan.PlanListView;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.UUID;

public interface PlanRepository extends ListCrudRepository<PlanEntity, UUID> {
    List<PlanListView> findAllListViewBy();
}
