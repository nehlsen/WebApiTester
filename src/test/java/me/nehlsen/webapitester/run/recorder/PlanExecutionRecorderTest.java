package me.nehlsen.webapitester.run.recorder;

import me.nehlsen.webapitester.persistence.DataAccess;
import me.nehlsen.webapitester.persistence.record.PlanExecutionRecordEntity;
import me.nehlsen.webapitester.persistence.record.PlanExecutionRecordEntityFactory;
import me.nehlsen.webapitester.run.context.PlanExecutionContext;
import me.nehlsen.webapitester.run.dto.PlanDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PlanExecutionRecorderTest {

    private static final UUID PLAN_UUID = UUID.fromString("dc6647fe-f0ef-4d0e-90a9-1843288089e3");
    private static final UUID EXECUTION_CONTEXT_UUID = UUID.fromString("66b7b061-4a5a-4b51-89b9-c7e08d4632c9");
    private DataAccess dataAccess;
    private PlanExecutionRecordEntityFactory recordEntityFactory;

    @BeforeEach
    void setUp() {
        dataAccess = mock(DataAccess.class);
        recordEntityFactory = mock(PlanExecutionRecordEntityFactory.class);
    }

    @Test
    public void it_creates_and_persists_a_new_execution_record_entity_on_executionStart() {
        final PlanExecutionRecordEntity executionRecordEntity = mock(PlanExecutionRecordEntity.class);
        when(recordEntityFactory.create(PLAN_UUID)).thenReturn(executionRecordEntity);

        final PlanExecutionRecorder planExecutionRecorder = new PlanExecutionRecorder(recordEntityFactory, dataAccess);
        planExecutionRecorder.executionStart(createPlanExecutionContextMock());

        verify(recordEntityFactory, times(1)).create(PLAN_UUID);
        verify(dataAccess, times(1)).save(executionRecordEntity);
    }

    @Test
    public void it_does_not_create_and_persist_a_new_execution_record_entity_on_repeated_executionStart() {
        final PlanExecutionRecordEntity executionRecordEntity = mock(PlanExecutionRecordEntity.class);
        when(recordEntityFactory.create(PLAN_UUID)).thenReturn(executionRecordEntity);

        final PlanExecutionRecorder planExecutionRecorder = new PlanExecutionRecorder(recordEntityFactory, dataAccess);
        planExecutionRecorder.executionStart(createPlanExecutionContextMock());

        // start again
        planExecutionRecorder.executionStart(createPlanExecutionContextMock());

        verify(recordEntityFactory, times(1)).create(PLAN_UUID);
        verify(dataAccess, times(1)).save(executionRecordEntity);

    }

    private PlanExecutionContext createPlanExecutionContextMock() {
        final PlanDto planDto = mock(PlanDto.class);
        when(planDto.getUuid()).thenReturn(PLAN_UUID);

        final PlanExecutionContext planExecutionContext = mock(PlanExecutionContext.class);
        when(planExecutionContext.getUuid()).thenReturn(EXECUTION_CONTEXT_UUID);
        when(planExecutionContext.getPlan()).thenReturn(planDto);

        return planExecutionContext;
    }
}
