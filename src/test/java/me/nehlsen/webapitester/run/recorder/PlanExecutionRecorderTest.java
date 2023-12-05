package me.nehlsen.webapitester.run.recorder;

import me.nehlsen.webapitester.persistence.DataAccess;
import me.nehlsen.webapitester.persistence.record.PlanExecutionRecordEntity;
import me.nehlsen.webapitester.persistence.record.PlanExecutionRecordEntityFactory;
import me.nehlsen.webapitester.run.context.PlanExecutionContext;
import me.nehlsen.webapitester.run.dto.PlanDto;
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

class PlanExecutionRecorderTest {

    private static final UUID PLAN_UUID = UUID.fromString("dc6647fe-f0ef-4d0e-90a9-1843288089e3");
    private static final UUID EXECUTION_CONTEXT_UUID = UUID.fromString("66b7b061-4a5a-4b51-89b9-c7e08d4632c9");
    private static final UUID EXECUTION_RECORD_UUID = UUID.fromString("656ead0f-35c5-4eb2-896f-12203327a5da");
    private DataAccess dataAccess;
    private PlanExecutionRecordEntityFactory recordEntityFactory;
    private PlanExecutionRecorder planExecutionRecorder;

    @BeforeEach
    void setUp() {
        dataAccess = mock(DataAccess.class);
        recordEntityFactory = mock(PlanExecutionRecordEntityFactory.class);
        planExecutionRecorder = new PlanExecutionRecorder(recordEntityFactory, dataAccess);
    }

    @Test
    public void it_creates_and_persists_a_new_execution_record_entity_on_executionStart() {
        final PlanExecutionRecordEntity executionRecordEntity = createPlanExecutionRecordEntityMock();
        when(recordEntityFactory.create(PLAN_UUID)).thenReturn(executionRecordEntity);

        planExecutionRecorder.executionStart(createPlanExecutionContextMock());

        verify(recordEntityFactory, times(1)).create(PLAN_UUID);
        verify(dataAccess, times(1)).save(executionRecordEntity);
    }

    @Test
    public void it_does_not_create_and_persist_a_new_execution_record_entity_on_repeated_executionStart() {
        final PlanExecutionRecordEntity executionRecordEntity = createPlanExecutionRecordEntityMock();
        when(recordEntityFactory.create(PLAN_UUID)).thenReturn(executionRecordEntity);

        planExecutionRecorder.executionStart(createPlanExecutionContextMock());

        // start again
        planExecutionRecorder.executionStart(createPlanExecutionContextMock());

        verify(recordEntityFactory, times(1)).create(PLAN_UUID);
        verify(dataAccess, times(1)).save(executionRecordEntity);

    }

    @Test
    public void it_updates_properties_and_saves_on_executionEnd() {
        final PlanExecutionRecordEntity executionRecordEntity = createPlanExecutionRecordEntityMock();
        when(recordEntityFactory.create(PLAN_UUID)).thenReturn(executionRecordEntity);

        when(dataAccess.findPlanExecutionRecordByUuid(EXECUTION_RECORD_UUID)).thenReturn(Optional.of(executionRecordEntity));

        final PlanExecutionContext planExecutionContextMock = createPlanExecutionContextMock();
        planExecutionRecorder.executionStart(planExecutionContextMock);
        planExecutionRecorder.executionEnd(planExecutionContextMock);

        verify(recordEntityFactory, times(1)).create(PLAN_UUID);
        verify(dataAccess, times(2)).save(executionRecordEntity);

        verify(dataAccess, times(1)).findPlanExecutionRecordByUuid(EXECUTION_RECORD_UUID);
        verify(executionRecordEntity, times(1)).setEndTimeEpochMillis(anyLong());
        verify(executionRecordEntity, times(1)).setResultPositive(anyBoolean());
    }

    @Test
    public void it_does_not_create_and_persist_a_new_execution_record_entity_when_only_calling_executionEnd() {
        final PlanExecutionRecordEntity executionRecordEntity = createPlanExecutionRecordEntityMock();
        when(recordEntityFactory.create(PLAN_UUID)).thenReturn(executionRecordEntity);

        planExecutionRecorder.executionEnd(createPlanExecutionContextMock());

        verify(recordEntityFactory, never()).create(PLAN_UUID);
        verify(dataAccess, never()).save(executionRecordEntity);
    }

    private PlanExecutionContext createPlanExecutionContextMock() {
        final PlanDto planDto = mock(PlanDto.class);
        when(planDto.getUuid()).thenReturn(PLAN_UUID);

        final PlanExecutionContext planExecutionContext = mock(PlanExecutionContext.class);
        when(planExecutionContext.getUuid()).thenReturn(EXECUTION_CONTEXT_UUID);
        when(planExecutionContext.getPlan()).thenReturn(planDto);

        return planExecutionContext;
    }

    private static PlanExecutionRecordEntity createPlanExecutionRecordEntityMock() {
        final PlanExecutionRecordEntity executionRecordEntity = mock(PlanExecutionRecordEntity.class);
        when(executionRecordEntity.getUuid()).thenReturn(EXECUTION_RECORD_UUID);
        return executionRecordEntity;
    }
}
