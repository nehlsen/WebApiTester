package me.nehlsen.webapitester.run.recorder;

import lombok.extern.slf4j.Slf4j;
import me.nehlsen.webapitester.persistence.DataAccess;
import me.nehlsen.webapitester.persistence.record.TaskExecutionRecordEntity;
import me.nehlsen.webapitester.persistence.record.TaskExecutionRecordEntityFactory;
import me.nehlsen.webapitester.persistence.record.TaskExecutionRecordMapper;
import me.nehlsen.webapitester.run.context.TaskExecutionContext;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class TaskExecutionRecorder {

    private final TaskExecutionRecordEntityFactory taskExecutionRecordEntityFactory;
    private final DataAccess dataAccess;
    private final TaskExecutionRecordMapper mapper;
    private final Map<UUID, UUID> executionContextToRecord = new HashMap<>();

    public TaskExecutionRecorder(
            TaskExecutionRecordEntityFactory taskExecutionRecordEntityFactory,
            DataAccess dataAccess,
            TaskExecutionRecordMapper mapper
    ) {
        this.taskExecutionRecordEntityFactory = taskExecutionRecordEntityFactory;
        this.dataAccess = dataAccess;
        this.mapper = mapper;
    }

    public void executionStart(TaskExecutionContext taskExecutionContext) {
        if (executionContextHasRecord(taskExecutionContext)) {
            log.error(
                    "Execution context ({}) already has a Record ({}), bailing",
                    taskExecutionContext.getUuid(),
                    executionContextToRecord.get(taskExecutionContext.getUuid())
            );
            return;
        }

        final TaskExecutionRecordEntity executionRecord = taskExecutionRecordEntityFactory.create(
                taskExecutionContext.getPlanExecutionContext().getRecordUuid(),
                taskExecutionContext.getTask().getUuid()
        );
        executionRecord.setStartTimeEpochMillis(Instant.now().toEpochMilli());

        dataAccess.save(executionRecord);
        executionContextToRecord.put(taskExecutionContext.getUuid(), executionRecord.getUuid());
    }

    public void executionEnd(TaskExecutionContext taskExecutionContext) {
        if (!executionContextHasRecord(taskExecutionContext)) {
            log.error(
                    "Execution context ({}) has NO Record ({}), bailing",
                    taskExecutionContext.getUuid(),
                    executionContextToRecord.get(taskExecutionContext.getUuid())
            );
            return;
        }

        dataAccess.findTaskExecutionRecordByUuid(executionContextToRecord.get(taskExecutionContext.getUuid()))
                .ifPresent(record -> {
                    record.setEndTimeEpochMillis(Instant.now().toEpochMilli());
                    record.setResultPositive(taskExecutionContext.isResultPositive());
                    record.setRequest(mapper.mapRequest(taskExecutionContext.getRequest()));
                    record.setResponse(mapper.mapResponse(taskExecutionContext.getResponse()));
                    dataAccess.save(record);
                });

        executionContextToRecord.remove(taskExecutionContext.getUuid());
    }

    private boolean executionContextHasRecord(TaskExecutionContext taskExecutionContext) {
        return executionContextToRecord.containsKey(taskExecutionContext.getUuid());
    }
}
