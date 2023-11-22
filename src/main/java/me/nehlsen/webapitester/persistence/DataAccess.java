package me.nehlsen.webapitester.persistence;

import me.nehlsen.webapitester.api.plan.CreatePlanDto;
import me.nehlsen.webapitester.persistence.event.AfterCreatePlanEvent;
import me.nehlsen.webapitester.persistence.event.BeforeCreatePlanEvent;
import me.nehlsen.webapitester.persistence.plan.PlanEntity;
import me.nehlsen.webapitester.persistence.plan.PlanEntityFactory;
import me.nehlsen.webapitester.persistence.plan.PlanListView;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DataAccess {

    private final PlanEntityFactory planEntityFactory;
    private final PlanRepository repository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public DataAccess(
            PlanEntityFactory planEntityFactory,
            PlanRepository repository,
            ApplicationEventPublisher applicationEventPublisher
    ) {
        this.planEntityFactory = planEntityFactory;
        this.repository = repository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public List<PlanEntity> findAll() {
        return repository.findAll();
    }
    public List<PlanListView> findAllListView() {
        return repository.findAllListViewBy();
    }

    public PlanEntity findByUuid(String uuid) {
        return repository
                .findById(UUID.fromString(uuid))
                .orElseThrow(() -> new PlanNotFoundException("asd"));
    }

    public PlanEntity saveNew(CreatePlanDto planDto) {
        applicationEventPublisher.publishEvent(new BeforeCreatePlanEvent(this, planDto));
        final PlanEntity planEntity = planEntityFactory.newPlan(planDto);
        final PlanEntity savedEntity = repository.save(planEntity);
        applicationEventPublisher.publishEvent(new AfterCreatePlanEvent(this, savedEntity));
        return savedEntity;
    }
}
