package me.nehlsen.webapitester.run.recorder;

import lombok.extern.slf4j.Slf4j;
import me.nehlsen.webapitester.persistence.plan.PlanExecutionRecordEntity;
import me.nehlsen.webapitester.persistence.plan.PlanExecutionRecordEntityFactory;
import me.nehlsen.webapitester.persistence.plan.PlanExecutionRecordRepository;
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
    private final PlanExecutionRecordRepository recordRepository;
    private final Map<UUID, UUID> executionContextToRecord = new HashMap<>();

    public PlanExecutionRecorder(
            PlanExecutionRecordEntityFactory planExecutionRecordEntityFactory,
            PlanExecutionRecordRepository recordRepository
    ) {
        this.planExecutionRecordEntityFactory = planExecutionRecordEntityFactory;
        this.recordRepository = recordRepository;
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

        recordRepository.save(executionRecord);
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

        recordRepository.findById(executionContextToRecord.get(planExecutionContext.getUuid()))
                .ifPresent(record -> {
                    record.setEndTimeEpochMillis(Instant.now().toEpochMilli());
                    record.setResultPositive(planExecutionContext.isResultPositive());
                    recordRepository.save(record);
                });

        executionContextToRecord.remove(planExecutionContext.getUuid());
    }

    private boolean executionContextHasRecord(PlanExecutionContext planExecutionContext) {
        return executionContextToRecord.containsKey(planExecutionContext.getUuid());
    }
}
