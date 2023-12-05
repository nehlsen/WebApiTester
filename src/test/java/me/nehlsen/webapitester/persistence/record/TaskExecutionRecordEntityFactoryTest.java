package me.nehlsen.webapitester.persistence.record;

import me.nehlsen.webapitester.persistence.DataAccess;
import me.nehlsen.webapitester.persistence.task.TaskEntity;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskExecutionRecordEntityFactoryTest {

    @Test
    public void it_creates_entity() {
        UUID executionRecordUuid = UUID.randomUUID();
        final PlanExecutionRecordEntity planExecutionRecordEntity = mock(PlanExecutionRecordEntity.class);

        UUID taskUuid = UUID.randomUUID();
        final TaskEntity taskEntity = mock(TaskEntity.class);

        final DataAccess dataAccess = mock(DataAccess.class);
        when(dataAccess.findPlanExecutionRecordByUuid(executionRecordUuid)).thenReturn(Optional.of(planExecutionRecordEntity));
        when(dataAccess.findTaskByUuid(taskUuid)).thenReturn(Optional.of(taskEntity));

        final TaskExecutionRecordEntityFactory factory = new TaskExecutionRecordEntityFactory(dataAccess);

        final TaskExecutionRecordEntity entity = factory.create(executionRecordUuid, taskUuid);
        assertThat(entity.getPlanExecutionRecord()).isEqualTo(planExecutionRecordEntity);
        assertThat(entity.getTask()).isEqualTo(taskEntity);
    }
}
