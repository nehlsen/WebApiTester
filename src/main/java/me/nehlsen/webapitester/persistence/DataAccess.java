package me.nehlsen.webapitester.persistence;

import me.nehlsen.webapitester.api.CreatePlanDto;
import org.springframework.stereotype.Component;

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

    public PlanEntity findByUuid(String uuid) {
        final Optional<PlanEntity> optionalPlan = repository.findById(UUID.fromString(uuid));
        return optionalPlan.orElseThrow(() -> new PlanNotFoundException("asd"));
    }

    public PlanEntity saveNew(CreatePlanDto planDto) {
        final PlanEntity planEntity = planEntityFactory.newPlan(planDto);
        return repository.save(planEntity);
    }
}
