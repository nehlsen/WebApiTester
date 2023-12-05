package me.nehlsen.webapitester.run.recorder;

import me.nehlsen.webapitester.persistence.DataAccess;
import me.nehlsen.webapitester.persistence.record.Request;
import me.nehlsen.webapitester.persistence.record.Response;
import me.nehlsen.webapitester.persistence.record.TaskExecutionRecordEntity;
import me.nehlsen.webapitester.persistence.record.TaskExecutionRecordEntityFactory;
import me.nehlsen.webapitester.persistence.record.TaskExecutionRecordMapper;
import me.nehlsen.webapitester.run.context.PlanExecutionContext;
import me.nehlsen.webapitester.run.context.TaskExecutionContext;
import me.nehlsen.webapitester.run.dto.TaskDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TaskExecutionRecorderTest {

    private static final UUID TASK_UUID = UUID.fromString("30a2a6a7-77a1-4f20-9423-56c8c5d16f64");
    private static final UUID EXECUTION_CONTEXT_UUID = UUID.fromString("fb6f8d03-6f8f-4600-bb29-42e2c2cf3fd5");
    private static final UUID EXECUTION_RECORD_UUID = UUID.fromString("a54f3f48-b104-476a-95a3-df7c2d1c8f38");
    private static final UUID PLAN_EXECUTION_RECORD_UUID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
    private DataAccess dataAccess;
    private TaskExecutionRecordEntityFactory recordEntityFactory;
    private TaskExecutionRecorder taskExecutionRecorder;

    @BeforeEach
    void setUp() {
        dataAccess = mock(DataAccess.class);
        recordEntityFactory = mock(TaskExecutionRecordEntityFactory.class);
        TaskExecutionRecordMapper mapper = mock(TaskExecutionRecordMapper.class);
        when(mapper.mapRequest(any())).thenReturn(mock(Request.class));
        when(mapper.mapResponse(any())).thenReturn(mock(Response.class));
        taskExecutionRecorder = new TaskExecutionRecorder(recordEntityFactory, dataAccess, mapper);
    }

    @Test
    public void it_creates_and_persists_a_new_execution_record_entity_on_executionStart() {
        final TaskExecutionRecordEntity executionRecordEntity = createTaskExecutionRecordEntityMock();
        when(recordEntityFactory.create(PLAN_EXECUTION_RECORD_UUID, TASK_UUID)).thenReturn(executionRecordEntity);

        taskExecutionRecorder.executionStart(createTaskExecutionContextMock());

        verify(recordEntityFactory, times(1)).create(PLAN_EXECUTION_RECORD_UUID, TASK_UUID);
        verify(dataAccess, times(1)).save(executionRecordEntity);
    }

    @Test
    public void it_does_not_create_and_persist_a_new_execution_record_entity_on_repeated_executionStart() {
        final TaskExecutionRecordEntity executionRecordEntity = createTaskExecutionRecordEntityMock();
        when(recordEntityFactory.create(PLAN_EXECUTION_RECORD_UUID, TASK_UUID)).thenReturn(executionRecordEntity);

        taskExecutionRecorder.executionStart(createTaskExecutionContextMock());

        // start again
        taskExecutionRecorder.executionStart(createTaskExecutionContextMock());

        verify(recordEntityFactory, times(1)).create(PLAN_EXECUTION_RECORD_UUID, TASK_UUID);
        verify(dataAccess, times(1)).save(executionRecordEntity);

    }

    @Test
    public void it_updates_properties_and_saves_on_executionEnd() {
        final TaskExecutionRecordEntity executionRecordEntity = createTaskExecutionRecordEntityMock();
        when(recordEntityFactory.create(PLAN_EXECUTION_RECORD_UUID, TASK_UUID)).thenReturn(executionRecordEntity);

        when(dataAccess.findTaskExecutionRecordByUuid(EXECUTION_RECORD_UUID)).thenReturn(Optional.of(executionRecordEntity));

        final TaskExecutionContext taskExecutionContextMock = createTaskExecutionContextMock();
        taskExecutionRecorder.executionStart(taskExecutionContextMock);
        taskExecutionRecorder.executionEnd(taskExecutionContextMock);

        verify(recordEntityFactory, times(1)).create(PLAN_EXECUTION_RECORD_UUID, TASK_UUID);
        verify(dataAccess, times(2)).save(executionRecordEntity);

        verify(dataAccess, times(1)).findTaskExecutionRecordByUuid(EXECUTION_RECORD_UUID);
        verify(executionRecordEntity, times(1)).setEndTimeEpochMillis(anyLong());
        verify(executionRecordEntity, times(1)).setResultPositive(anyBoolean());
        verify(executionRecordEntity, times(1)).setRequest(any(Request.class));
        verify(executionRecordEntity, times(1)).setResponse(any(Response.class));
    }

    @Test
    public void it_does_not_create_and_persist_a_new_execution_record_entity_when_only_calling_executionEnd() {
        final TaskExecutionRecordEntity executionRecordEntity = createTaskExecutionRecordEntityMock();
        when(recordEntityFactory.create(PLAN_EXECUTION_RECORD_UUID, TASK_UUID)).thenReturn(executionRecordEntity);

        taskExecutionRecorder.executionEnd(createTaskExecutionContextMock());

        verify(recordEntityFactory, never()).create(PLAN_EXECUTION_RECORD_UUID, TASK_UUID);
        verify(dataAccess, never()).save(executionRecordEntity);
    }

    private TaskExecutionContext createTaskExecutionContextMock() {
        final TaskDto taskDto = mock(TaskDto.class);
        when(taskDto.getUuid()).thenReturn(TASK_UUID);

        final PlanExecutionContext planExecutionContext = mock(PlanExecutionContext.class);
        when(planExecutionContext.getRecordUuid()).thenReturn(PLAN_EXECUTION_RECORD_UUID);

        final TaskExecutionContext taskExecutionContext = mock(TaskExecutionContext.class);
        when(taskExecutionContext.getUuid()).thenReturn(EXECUTION_CONTEXT_UUID);
        when(taskExecutionContext.getTask()).thenReturn(taskDto);
        when(taskExecutionContext.getPlanExecutionContext()).thenReturn(planExecutionContext);

        return taskExecutionContext;
    }

    private static TaskExecutionRecordEntity createTaskExecutionRecordEntityMock() {
        final TaskExecutionRecordEntity executionRecordEntity = mock(TaskExecutionRecordEntity.class);
        when(executionRecordEntity.getUuid()).thenReturn(EXECUTION_RECORD_UUID);
        return executionRecordEntity;
    }
}
