package me.nehlsen.webapitester.persistence.plan;

import me.nehlsen.webapitester.persistence.PlanRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PlanExecutionRecordEntityFactory {

    private final PlanRepository planRepository;

    public PlanExecutionRecordEntityFactory(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public PlanExecutionRecordEntity create(final UUID plan) {
        final PlanExecutionRecordEntity executionRecord = new PlanExecutionRecordEntity();
        executionRecord.setPlan(planRepository.findById(plan).orElseThrow());
        return executionRecord;
    }
}
