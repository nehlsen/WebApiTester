package me.nehlsen.webapitester.persistence.record;

import me.nehlsen.webapitester.persistence.DataAccess;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PlanExecutionRecordEntityFactory {

    private final DataAccess dataAccess;

    public PlanExecutionRecordEntityFactory(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public PlanExecutionRecordEntity create(final UUID plan) {
        final PlanExecutionRecordEntity executionRecord = new PlanExecutionRecordEntity();
        executionRecord.setPlan(dataAccess.findPlanByUuid(plan).orElseThrow());
        return executionRecord;
    }
}
