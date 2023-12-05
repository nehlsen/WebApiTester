package me.nehlsen.webapitester.persistence.record;

import me.nehlsen.webapitester.persistence.DataAccess;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TaskExecutionRecordEntityFactory {

    private final DataAccess dataAccess;

    public TaskExecutionRecordEntityFactory(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public TaskExecutionRecordEntity create(final UUID planExecutionRecord, final UUID task) {
        final TaskExecutionRecordEntity executionRecord = new TaskExecutionRecordEntity();
        executionRecord.setPlanExecutionRecord(dataAccess.findPlanExecutionRecordByUuid(planExecutionRecord).orElseThrow());
        executionRecord.setTask(dataAccess.findTaskByUuid(task).orElseThrow());

        return executionRecord;
    }
}
