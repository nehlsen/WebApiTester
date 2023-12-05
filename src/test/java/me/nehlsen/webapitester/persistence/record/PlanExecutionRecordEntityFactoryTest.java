package me.nehlsen.webapitester.persistence.record;

import me.nehlsen.webapitester.persistence.DataAccess;
import me.nehlsen.webapitester.persistence.plan.PlanEntity;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlanExecutionRecordEntityFactoryTest {

    @Test
    public void it_creates_entity() {
        UUID planUuid = UUID.randomUUID();
        final PlanEntity planEntity = mock(PlanEntity.class);

        final DataAccess dataAccess = mock(DataAccess.class);
        when(dataAccess.findPlanByUuid(planUuid)).thenReturn(Optional.of(planEntity));

        final PlanExecutionRecordEntityFactory factory = new PlanExecutionRecordEntityFactory(dataAccess);

        final PlanExecutionRecordEntity entity = factory.create(planUuid);
        assertThat(entity.getPlan()).isEqualTo(planEntity);
    }
}
