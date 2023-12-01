package me.nehlsen.webapitester.run.recorder;

import lombok.extern.slf4j.Slf4j;
import me.nehlsen.webapitester.persistence.DataAccess;
import me.nehlsen.webapitester.persistence.record.PlanExecutionRecordEntity;
import me.nehlsen.webapitester.persistence.record.PlanExecutionRecordEntityFactory;
import me.nehlsen.webapitester.run.context.PlanExecutionContext;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class PlanExecutionRecorder {

    private final PlanExecutionRecordEntityFactory planExecutionRecordEntityFactory;
    private final DataAccess dataAccess;
    private final Map<UUID, UUID> executionContextToRecord = new HashMap<>();

    public PlanExecutionRecorder(
            PlanExecutionRecordEntityFactory planExecutionRecordEntityFactory,
            DataAccess dataAccess) {
        this.planExecutionRecordEntityFactory = planExecutionRecordEntityFactory;
        this.dataAccess = dataAccess;
    }

    public void executionStart(PlanExecutionContext planExecutionContext) {
        if (executionContextHasRecord(planExecutionContext)) {
            log.error(
                    "Execution context ({}) already has a Record ({}), bailing",
                    planExecutionContext.getUuid(),
                    executionContextToRecord.get(planExecutionContext.getUuid())
            );
            return;
        }

        final PlanExecutionRecordEntity executionRecord = planExecutionRecordEntityFactory.create(planExecutionContext.getPlan().getUuid());
        executionRecord.setStartTimeEpochMillis(Instant.now().toEpochMilli());

        dataAccess.save(executionRecord);
        executionContextToRecord.put(planExecutionContext.getUuid(), executionRecord.getUuid());
    }

    public void executionEnd(PlanExecutionContext planExecutionContext) {
        if (!executionContextHasRecord(planExecutionContext)) {
            log.error(
                    "Execution context ({}) has NO Record ({}), bailing",
                    planExecutionContext.getUuid(),
                    executionContextToRecord.get(planExecutionContext.getUuid())
            );
            return;
        }

        dataAccess.findExecutionRecordByUuid(executionContextToRecord.get(planExecutionContext.getUuid()))
                .ifPresent(record -> {
                    record.setEndTimeEpochMillis(Instant.now().toEpochMilli());
                    record.setResultPositive(planExecutionContext.isResultPositive());
                    dataAccess.save(record);
                });

        executionContextToRecord.remove(planExecutionContext.getUuid());
    }

    private boolean executionContextHasRecord(PlanExecutionContext planExecutionContext) {
        return executionContextToRecord.containsKey(planExecutionContext.getUuid());
    }
}
