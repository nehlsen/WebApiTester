package me.nehlsen.webapitester.persistence;

import me.nehlsen.webapitester.api.plan.CreatePlanDto;
import me.nehlsen.webapitester.persistence.plan.PlanEntity;
import me.nehlsen.webapitester.persistence.plan.PlanEntityFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class DataAccess {

    private final PlanEntityFactory planEntityFactory;
    private final PlanRepository repository;

    public DataAccess(
            PlanEntityFactory planEntityFactory,
            PlanRepository repository
    ) {
        this.planEntityFactory = planEntityFactory;
        this.repository = repository;
    }

    public List<PlanEntity> findAll() {
        return repository.findAll();
    }

    public PlanEntity findByUuid(String uuid) {
        final Optional<PlanEntity> optionalPlan = repository.findById(UUID.fromString(uuid));
        return optionalPlan.orElseThrow(() -> new PlanNotFoundException("asd"));
    }

    public PlanEntity saveNew(CreatePlanDto planDto) {
        final PlanEntity planEntity = planEntityFactory.newPlan(planDto);
        return repository.save(planEntity);
    }
}
